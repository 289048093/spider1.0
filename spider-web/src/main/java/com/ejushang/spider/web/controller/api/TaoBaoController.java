package com.ejushang.spider.web.controller.api;

import com.ejushang.spider.constant.Constant;
import com.ejushang.spider.constant.OutPlatformType;
import com.ejushang.spider.domain.Shop;
import com.ejushang.spider.domain.ShopAuth;
import com.ejushang.spider.query.ShopQuery;
import com.ejushang.spider.service.shop.IShopService;
import com.ejushang.spider.taobao.api.ShopApi;
import com.ejushang.spider.taobao.helper.ConstantTaoBao;
import com.ejushang.spider.taobao.helper.ConvertUtils;
import com.ejushang.spider.taobao.helper.TaoBaoUtilsEjs;
import com.ejushang.spider.util.*;
import com.ejushang.spider.vo.ShopVo;
import com.ejushang.spider.web.util.DefaultTrustManager;
import com.ejushang.spider.web.util.JsonResult;
import net.sf.json.JSONObject;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.Consts;
import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.net.ssl.KeyManager;
import javax.net.ssl.SSLContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URLDecoder;
import java.nio.charset.Charset;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * 淘宝平台授权入口
 * User: Baron.Zhang
 * Date: 14-2-14
 * Time: 下午2:14
 */
@RequestMapping("/taobao")
@Controller
public class TaoBaoController {

    private static final Logger log = LoggerFactory.getLogger(TaoBaoController.class);

    private static final String REDIRECT_URI = AppConfigUtil.getInstance("config.properties").getProperty("REDIRECT_URI");
    private static final String INDEX_HTML = AppConfigUtil.getInstance("config.properties").getProperty("INDEX_HTML");

    @Autowired
    private IShopService shopService;


    /**
     * 进入淘宝授权页面
     * @param request
     * @return
     * @throws IOException
     * @throws URISyntaxException
     */
    @RequestMapping("/authorize")
    public String authorize(HttpServletRequest request) throws IOException, URISyntaxException {
        if(log.isInfoEnabled()){
            log.info("淘宝授权：进入淘宝授权页面：：：：：：");
        }

        URI uri = new URIBuilder()
                .setPath(ConstantTaoBao.TAOBAO_AUTHORIZE_URL)
                .setParameter(ConstantTaoBao.CLIENT_ID,ConstantTaoBao.APP_KEY)
                .setParameter(ConstantTaoBao.RESPONSE_TYPE,ConstantTaoBao.CODE)
                .setParameter(ConstantTaoBao.REDIRECT_URI, REDIRECT_URI)
                .build();

        return "redirect:"+uri.toURL();
    }

    /**
     * 供top回调，获取token
     * @param request
     */
    @RequestMapping("/callback")
    public String callback(HttpServletRequest request) throws Exception {

        if(log.isInfoEnabled()){
            log.info("淘宝授权：获取淘宝用户Access Token：：：：：：");
        }
        // TOP平台返回授权码
        String code = WebUtil.getString(request, ConstantTaoBao.CODE);

        if(log.isInfoEnabled()){
            log.info("淘宝授权：TOP返回CODE：" + code);
        }

        // 使用HttpClient4.3访问https时，进行证书环境设置
        DefaultTrustManager[] defaultTrustManagers = new DefaultTrustManager[1];
        defaultTrustManagers[0] = new DefaultTrustManager();

        SSLContext sslContext = SSLContext.getInstance("TLS");
        sslContext.init(new KeyManager[0],defaultTrustManagers,new SecureRandom());
        SSLContext.setDefault(sslContext);

        sslContext.init(null,defaultTrustManagers,null);
        SSLConnectionSocketFactory socketFactory = new SSLConnectionSocketFactory(sslContext,
                SSLConnectionSocketFactory.ALLOW_ALL_HOSTNAME_VERIFIER);

        // 构造提交参数
        List<NameValuePair> nameValuePairList = new ArrayList<NameValuePair>();
        nameValuePairList.add(new BasicNameValuePair(ConstantTaoBao.CLIENT_ID,ConstantTaoBao.APP_KEY));
        nameValuePairList.add(new BasicNameValuePair(ConstantTaoBao.CLIENT_SECRET,ConstantTaoBao.APP_SECRET));
        nameValuePairList.add(new BasicNameValuePair(ConstantTaoBao.GRANT_TYPE,ConstantTaoBao.AUTHORIZATION_CODE));
        nameValuePairList.add(new BasicNameValuePair(ConstantTaoBao.CODE,code));
        nameValuePairList.add(new BasicNameValuePair(ConstantTaoBao.REDIRECT_URI,REDIRECT_URI));

        UrlEncodedFormEntity encodedFormEntity = new UrlEncodedFormEntity(nameValuePairList,Consts.UTF_8);
        HttpPost httpPost = new HttpPost(ConstantTaoBao.TAOBAO_TOKEN_URL);
        httpPost.setEntity(encodedFormEntity);

        // post提交获取access token
        CloseableHttpClient httpClient = HttpClients.custom()
                .setSSLSocketFactory(socketFactory)
                .build();
        CloseableHttpResponse response = httpClient.execute(httpPost);

        // 创建保存返回内容的buffer对象
        StringBuffer content = new StringBuffer();
        try{
            HttpEntity httpEntity = response.getEntity();
            BufferedReader br = new BufferedReader(new InputStreamReader(httpEntity.getContent()));
            String line = null;
            while((line=br.readLine()) != null){
                content.append(line);
            }
        }catch (Exception e){
            throw e;
        }finally {
            response.close();
        }

        JSONObject jsonObject = JSONObject.fromObject(URLDecoder.decode(content.toString(), Constant.Encoding.UTF8));
        String accessToken = (String) jsonObject.get(ConstantTaoBao.ACCESS_TOKEN);
        String tokenType = (String) jsonObject.get(ConstantTaoBao.TOKEN_TYPE);
        String refreshToken = (String) jsonObject.get(ConstantTaoBao.REFRESH_TOKEN);
        Integer expiresIn = (Integer) jsonObject.get(ConstantTaoBao.EXPIRES_IN);
        Integer reExpiresIn = (Integer) jsonObject.get(ConstantTaoBao.RE_EXPIRES_IN);
        Integer r1ExpiresIn = (Integer) jsonObject.get(ConstantTaoBao.R1_EXPIRES_IN);
        Integer r2ExpiresIn = (Integer) jsonObject.get(ConstantTaoBao.R2_EXPIRES_IN);
        Integer w1ExpiresIn = (Integer) jsonObject.get(ConstantTaoBao.W1_EXPIRES_IN);
        Integer w2ExpiresIn = (Integer) jsonObject.get(ConstantTaoBao.W2_EXPIRES_IN);
        String taobaoUserNick = (String) jsonObject.get(ConstantTaoBao.TAOBAO_USER_NICK);
        String taobaoUserId = (String) jsonObject.get(ConstantTaoBao.TAOBAO_USER_ID);
        String subTaobaoUserNick = (String) jsonObject.get(ConstantTaoBao.SUB_TAOBAO_USER_NICK);
        String subTaobaoUserId = (String) jsonObject.get(ConstantTaoBao.SUB_TAOBAO_USER_ID);



        if(!StringUtils.isBlank(accessToken)){
            if(log.isInfoEnabled()){
                log.info("淘宝授权：获取用户Access Token成功。sessionKey="+accessToken);
            }

            // 来自淘宝平台
            Map<String,Object> shopArgsMap = new HashMap<String, Object>();
            // 设置返回字段（必须）
            shopArgsMap.put(ConstantTaoBao.FIELDS, "sid,cid,nick,title,desc,bulletin,pic_path,created,modified,shop_score");
            // 设置卖家昵称（必须）
            shopArgsMap.put(ConstantTaoBao.NICK,taobaoUserNick);
            if(log.isInfoEnabled()){
                log.info("淘宝授权：查询淘宝店铺信息，参数argsMap = "+ shopArgsMap);
            }
            // 创建淘宝shopApi
            ShopApi shopApi = new ShopApi(accessToken);
            log.info("淘宝授权：获取店铺信息信息");
            com.taobao.api.domain.Shop shop = shopApi.getShop(shopArgsMap);

            // 为供应商
            if(shop == null){
                Shop shopEx = shopService.findShopByNick(taobaoUserNick);

                if(shopEx != null){
                    // 供应商已授权
                    return "redirect:"+INDEX_HTML;
                }
                else{
                    Shop newShop = new Shop();
                    newShop.setShopId(taobaoUserNick);
                    newShop.setNick(taobaoUserNick);
                    newShop.setTitle(taobaoUserNick);
                    newShop.setOutPlatformType(OutPlatformType.TAO_BAO.toDesc());
                    newShop.setCreateTime(DateUtils.getCurrentDate());
                    newShop.setUpdateTime(DateUtils.getCurrentDate());

                    ShopAuth shopAuth = new ShopAuth();
                    shopAuth.setShopId(taobaoUserNick);
                    shopAuth.setSessionKey(accessToken);
                    shopAuth.setRefreshToken(refreshToken);
                    shopAuth.setTokenType(tokenType);
                    shopAuth.setExpiresIn(Long.valueOf(expiresIn));
                    shopAuth.setReExpiresIn(Long.valueOf(reExpiresIn));
                    shopAuth.setR1ExpiresIn(Long.valueOf(r1ExpiresIn));
                    shopAuth.setR2ExpiresIn(Long.valueOf(r2ExpiresIn));
                    shopAuth.setW1ExpiresIn(Long.valueOf(w1ExpiresIn));
                    shopAuth.setW2ExpiresIn(Long.valueOf(w2ExpiresIn));
                    shopAuth.setUserId(taobaoUserId);
                    shopAuth.setUserNick(taobaoUserNick);
                    shopAuth.setOutPlatformType(OutPlatformType.TAO_BAO.toDesc());

                    // 添加店铺和店铺授权信息
                    shopService.saveShopAndShopAuth(newShop,shopAuth);
                }
            }
            else{
                // 为店铺
                // 判断店铺是否已经授权
                Shop shopEx = shopService.findShopByShopId(ConvertUtils.convertLong2String(shop.getSid()));

                if(shopEx != null){
                    // 店铺已经授权
                    return "redirect:"+INDEX_HTML;
                }
                else{
                    Shop newShop = new Shop();
                    newShop.setShopId(ConvertUtils.convertLong2String(shop.getSid()));
                    newShop.setCatId(ConvertUtils.convertLong2String(shop.getCid()));
                    newShop.setNick(shop.getNick());
                    newShop.setTitle(shop.getTitle());
                    newShop.setDescription(shop.getDesc());
                    newShop.setBulletin(shop.getBulletin());
                    newShop.setPicPath(shop.getPicPath());
                    newShop.setItemScore(shop.getShopScore().getItemScore());
                    newShop.setServiceScore(shop.getShopScore().getServiceScore());
                    newShop.setDeliveryScore(shop.getShopScore().getDeliveryScore());
                    newShop.setOutPlatformType(OutPlatformType.TAO_BAO.toDesc());
                    newShop.setCreateTime(shop.getCreated());
                    newShop.setUpdateTime(shop.getModified());

                    ShopAuth shopAuth = new ShopAuth();
                    shopAuth.setShopId(ConvertUtils.convertLong2String(shop.getSid()));
                    shopAuth.setSessionKey(accessToken);
                    shopAuth.setRefreshToken(refreshToken);
                    shopAuth.setTokenType(tokenType);
                    shopAuth.setExpiresIn(Long.valueOf(expiresIn));
                    shopAuth.setReExpiresIn(Long.valueOf(reExpiresIn));
                    shopAuth.setR1ExpiresIn(Long.valueOf(r1ExpiresIn));
                    shopAuth.setR2ExpiresIn(Long.valueOf(r2ExpiresIn));
                    shopAuth.setW1ExpiresIn(Long.valueOf(w1ExpiresIn));
                    shopAuth.setW2ExpiresIn(Long.valueOf(w2ExpiresIn));
                    shopAuth.setUserId(taobaoUserId);
                    shopAuth.setUserNick(taobaoUserNick);
                    shopAuth.setOutPlatformType(OutPlatformType.TAO_BAO.toDesc());

                    // 添加店铺和店铺授权信息
                    shopService.saveShopAndShopAuth(newShop,shopAuth);
                }
            }
        }

        return "redirect:" + INDEX_HTML;

    }

    /**
     * 刷新店铺授权信息，延长有效期
     * @param id
     * @return
     * @throws Exception
     */
    @RequestMapping("/refreshToken")
    public void refreshToken(Integer id,HttpServletResponse httpServletResponse) throws Exception {

        // 获取店铺及其授权信息
        ShopVo shopVo = shopService.findShopById(id);

        if(log.isInfoEnabled()){
            log.info("淘宝授权：刷新店铺授权信息：：：：：：");
        }

        // 使用HttpClient4.3访问https时，进行证书环境设置
        DefaultTrustManager[] defaultTrustManagers = new DefaultTrustManager[1];
        defaultTrustManagers[0] = new DefaultTrustManager();

        SSLContext sslContext = SSLContext.getInstance("TLS");
        sslContext.init(new KeyManager[0],defaultTrustManagers,new SecureRandom());
        SSLContext.setDefault(sslContext);

        sslContext.init(null,defaultTrustManagers,null);
        SSLConnectionSocketFactory socketFactory = new SSLConnectionSocketFactory(sslContext,
                SSLConnectionSocketFactory.ALLOW_ALL_HOSTNAME_VERIFIER);

        // 构造提交参数
        List<NameValuePair> nameValuePairList = new ArrayList<NameValuePair>();
        nameValuePairList.add(new BasicNameValuePair(ConstantTaoBao.CLIENT_ID,ConstantTaoBao.APP_KEY));
        nameValuePairList.add(new BasicNameValuePair(ConstantTaoBao.CLIENT_SECRET,ConstantTaoBao.APP_SECRET));
        nameValuePairList.add(new BasicNameValuePair(ConstantTaoBao.GRANT_TYPE,ConstantTaoBao.REFRESH_TOKEN));
        nameValuePairList.add(new BasicNameValuePair(ConstantTaoBao.REFRESH_TOKEN,shopVo.getRefreshToken()));

        UrlEncodedFormEntity encodedFormEntity = new UrlEncodedFormEntity(nameValuePairList,Consts.UTF_8);
        HttpPost httpPost = new HttpPost(ConstantTaoBao.TAOBAO_TOKEN_URL);
        httpPost.setEntity(encodedFormEntity);

        // post提交获取access token
        CloseableHttpClient httpClient = HttpClients.custom()
                .setSSLSocketFactory(socketFactory)
                .build();
        CloseableHttpResponse response = httpClient.execute(httpPost);

        // 创建保存返回内容的buffer对象
        StringBuffer content = new StringBuffer();
        try{
            HttpEntity httpEntity = response.getEntity();
            BufferedReader br = new BufferedReader(new InputStreamReader(httpEntity.getContent()));
            String line = null;
            while((line=br.readLine()) != null){
                content.append(line);
            }
        }catch (Exception e){
            throw e;
        }finally {
            response.close();
        }

        JSONObject jsonObject = JSONObject.fromObject(URLDecoder.decode(content.toString(), Constant.Encoding.UTF8));
        String accessToken = (String) jsonObject.get(ConstantTaoBao.ACCESS_TOKEN);
        String tokenType = (String) jsonObject.get(ConstantTaoBao.TOKEN_TYPE);
        String refreshToken = (String) jsonObject.get(ConstantTaoBao.REFRESH_TOKEN);
        Integer expiresIn = (Integer) jsonObject.get(ConstantTaoBao.EXPIRES_IN);
        Integer reExpiresIn = (Integer) jsonObject.get(ConstantTaoBao.RE_EXPIRES_IN);
        Integer r1ExpiresIn = (Integer) jsonObject.get(ConstantTaoBao.R1_EXPIRES_IN);
        Integer r2ExpiresIn = (Integer) jsonObject.get(ConstantTaoBao.R2_EXPIRES_IN);
        Integer w1ExpiresIn = (Integer) jsonObject.get(ConstantTaoBao.W1_EXPIRES_IN);
        Integer w2ExpiresIn = (Integer) jsonObject.get(ConstantTaoBao.W2_EXPIRES_IN);
        String taobaoUserNick = (String) jsonObject.get(ConstantTaoBao.TAOBAO_USER_NICK);
        String taobaoUserId = (String) jsonObject.get(ConstantTaoBao.TAOBAO_USER_ID);

        // 更新授权信息
        ShopAuth shopAuth = new ShopAuth();
        shopAuth.setId(String.valueOf(id));
        shopAuth.setSessionKey(accessToken);
        shopAuth.setTokenType(tokenType);
        shopAuth.setRefreshToken(refreshToken);
        shopAuth.setExpiresIn(Long.valueOf(expiresIn));
        shopAuth.setReExpiresIn(Long.valueOf(reExpiresIn));
        shopAuth.setR1ExpiresIn(Long.valueOf(r1ExpiresIn));
        shopAuth.setR2ExpiresIn(Long.valueOf(r2ExpiresIn));
        shopAuth.setW1ExpiresIn(Long.valueOf(w1ExpiresIn));
        shopAuth.setW2ExpiresIn(Long.valueOf(w2ExpiresIn));

        shopService.updateShopAuth(shopAuth);

        // 刷新页面上的sessionkey
        shopVo.setSessionKey(accessToken);

        new JsonResult(JsonResult.SUCCESS,"刷新授权信息成功！")
                .addData(JsonResult.RESULT_TYPE_SINGLE_OBJECT,shopVo)
                .toJson(httpServletResponse);

        return;
    }

}
