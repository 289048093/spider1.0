package com.ejushang.spider.erp.service.repository;

import com.ejushang.spider.constant.StorageType;
import com.ejushang.spider.domain.Product;
import com.ejushang.spider.domain.Storage;
import com.ejushang.spider.domain.StorageLog;
import com.ejushang.spider.domain.User;
import com.ejushang.spider.erp.common.mapper.*;
import com.ejushang.spider.query.StorageQuery;
import com.ejushang.spider.service.repository.IStorageService;
import com.ejushang.spider.util.Money;
import com.ejushang.spider.util.Page;
import com.ejushang.spider.util.SessionUtils;
import com.ejushang.spider.vo.StorageVo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * User: tin
 * Date: 13-12-23
 * Time: 下午2:04
 */
@Service("storageService")
@Transactional
public class StorageService implements IStorageService {
    private static final Logger log = LoggerFactory.getLogger(RepositoryService.class);
    @Autowired
    private StorageMapper storageMapper;
    @Autowired
    private RepositoryMapper repositoryMapper;

    @Autowired
    private ProductMapper productMapper;

    @Autowired
    private ProdCategoryMapper prodCategoryMapper;

    @Autowired
    private BrandMapper brandMapper;
    @Autowired
    private StorageLogMapper storageLogMapper;


//查询区

    /**
     * 查询出所有Storage数据
     *
     * @return 返回一个Storage类型的list
     */
    @Transactional(readOnly = true)
    @Override
    public List<Storage> findStorage() {
        if (log.isInfoEnabled()) {
            log.info("Storage findStorage 查询所有");
        }
        List<Storage> result = new ArrayList<Storage>();
        List<Storage> list = storageMapper.findStorage();

        Product product = null;
        for (Storage stor : list) {
            Storage storage = new Storage();
            storage = stor;
            storage.setRepository(repositoryMapper.findRepositoryById(stor.getRepoId()));

            product = productMapper.findProductById(stor.getProdId());

            if (null == product) {
                product = new Product();
            }
            storage.setBrand(brandMapper.findBrandById(product.getBrandId()));
            storage.setProdCategory(prodCategoryMapper.findProdCategoryById(product.getCid()));
            storage.setProduct(product);
            result.add(storage);

        }
        return result;
    }


    /**
     * 通过商品id查询库存条数
     *
     * @param prodId 商品ID
     * @return 数据条数
     */
    @Transactional(readOnly = true)
    @Override
    public Integer findStorageCountByProdId(Integer prodId) {
        if (log.isInfoEnabled()) {
            log.info("StorageService findStorageCountByProdId 通过prodId{" + prodId + "}查找");
        }
        return storageMapper.findStorageCountByProdId(prodId);
    }

    public Storage findStorageByProdId(Integer pordId) {
        return storageMapper.findStorageByProdId(pordId);

    }

    /**
     * 通过id查询库存信息
     *
     * @param id 库存id
     * @return 返回Storage类型
     */
    @Transactional(readOnly = true)
    @Override
    public Storage findStorageById(Integer id) {
        if (log.isInfoEnabled()) {
            log.info("StorageService中的方法findStorageById的参数id：{" + id.toString() + "} 通过ID查找库存");
        }
        Storage storage = storageMapper.findStorageById(id);
        storage.setRepository(repositoryMapper.findRepositoryById(storage.getRepoId()));
        Product product = productMapper.findProductById(storage.getProdId());
        if (null == product) {
            product = new Product();
        }
        storage.setBrand(brandMapper.findBrandById(product.getBrandId()));
        storage.setProdCategory(prodCategoryMapper.findProdCategoryById(product.getCid()));
        storage.setProduct(product);


        return storage;
    }

    /**
     * 通过StorageQuery查询库存
     *
     * @param storageQuery Storage库存查询条件
     * @return返回一个Page对象
     */
    @Transactional(readOnly = true)
    @Override
    public Page findJoinStorage(StorageQuery storageQuery) {
        if (log.isInfoEnabled()) {
            log.info("StorageService findJoinStorage 通过storageQuery{" + storageQuery.toString() + "}");
        }
        User user = SessionUtils.getUser();
        Integer checknum = 0;
        if (null != user) {
            if (null != user.getRepoId() && !checknum.equals(user.getRepoId())) {

                storageQuery.setRepoId(user.getRepoId());
            }
        }

        // 构造分页信息
        Page page = new Page();
        // 设置当前页
        page.setPageNo(storageQuery.getPage());
        // 设置分页大小
        page.setPageSize(storageQuery.getLimit());
        // 设置总条数
        List<StorageVo> storageVoList = new ArrayList<StorageVo>();
        StorageVo storageVo = null;
        List<Storage> joinStorageByPage = storageMapper.findJoinStorageByPage(storageQuery, page);
        for (Storage storage : joinStorageByPage) {
            storageVo = new StorageVo();
            storageVo.setId(storage.getId().toString());
            storageVo.setProdId(storage.getProdId().toString());
            storageVo.setProdName(storage.getProduct().getProdName());
            storageVo.setRepoId(storage.getRepoId().toString());
            storageVo.setRepoName(storage.getRepository().getName());
            storageVo.setActuallyNumber(storage.getActuallyNumber());
            storageVo.setModifyTime(storage.getModifyTime());
            storageVo.setBrandId(storage.getBrand().getId().toString());
            storageVo.setBrandName(storage.getBrand().getName());
            storageVo.setProdNo(storage.getProduct().getProdNo());
            storageVo.setProdCode(storage.getProduct().getProdCode());
            storageVo.setProdCaName(storage.getProdCategory().getName());
            storageVo.setProdCaId(storage.getId().toString());

            if (null != storage.getProduct().getShopPrice()) {
                storageVo.setShopPrice(Money.CentToYuan(storage.getProduct().getShopPrice()).toString());
            }
            if (null != storage.getProduct().getStandardPrice()) {
                storageVo.setStandardPrice(Money.CentToYuan(storage.getProduct().getStandardPrice()).toString());
            }
            if (null != storage.getProduct().getBuyPrice()) {
                storageVo.setBuyPrice(Money.CentToYuan(storage.getProduct().getBuyPrice()).toString());
            }
            storageVo.setDescription(storage.getProduct().getDescription());
            storageVoList.add(storageVo);
        }
        page.setResult(storageVoList);
        return page;
    }


    @Override
    public boolean manipulateStorage(Integer productId, Integer quantity) {
        if (quantity == null || quantity == 0) {
            return false;
        }
        Storage storage = findStorageByProdId(productId);
        if (storage == null) {
            log.error("操作库存的时候根据产品ID没有找到库存记录,productId[{}]", productId);
            return false;
        }
        storage.setActuallyNumber((storage.getActuallyNumber() == null ? 0 : storage.getActuallyNumber()) + quantity);
        storageMapper.updateStorage(storage);
       Storage storage1=findStorageByProdId(productId);
        User user=SessionUtils.getUser();
        StorageLog storageLog=storage.addLog(StorageType.DELIVERY.toString(),storage1,user);
         storageLogMapper.saveStorageLog(storageLog);
        return true;
    }


//=================================================
//    更新区

    /**
     * 更新库存
     *
     * @param storage 实体库存信息
     */
    @Transactional
    @Override
    public Integer updateStorage(Storage storage) {
        if (log.isInfoEnabled()) {
            log.info("StorageService updateStorage{" + storage.toString() + "} 通过Storage对象进行更新 ");
        }
        int result=0;
        Storage storage1=storageMapper.findStorageById(storage.getId());
        if (null == storage1) {
            return 0;
        } else {
           result= storageMapper.updateStorage(storage);
            if(result==0){
                return 0;
            }
            Storage storage2=storageMapper.findStorageById(storage.getId());

            User user= SessionUtils.getUser();
         StorageLog storageLog= storage1.addLog(StorageType.STORAGE.toString(),storage2,user);
         storageLogMapper.saveStorageLog(storageLog);
            return 1;
        }
    }
//=====================================================
//    删除区

    /**
     * 删除库存
     *
     * @param id 库存id
     */
    @Transactional
    @Override
    public Integer deleteStorageById(Integer id) {
        if (log.isInfoEnabled()) {
            log.info("StorageService deleteStorageById 通过Id{" + id.toString() + "}删除");
        }
        return storageMapper.deleteStorageById(id);
    }
//=======================================================
//    插入区

    /**
     * 插入库存数据
     *
     * @param storage 实体库存信息
     */
    @Transactional
    @Override
    public Integer saveStorage(Storage storage) {
        if (log.isInfoEnabled()) {
            log.info("StorageService saveStorage 通过对象storage{" + storage.toString() + "}进行save");
        }
        return storageMapper.saveStorage(storage);
    }
//================================================================


}
