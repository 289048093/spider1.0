package com.ejushang.spider.web.controller;

import com.ejushang.spider.service.permission.IRoleService;
import com.ejushang.spider.service.user.IUserService;
import com.ejushang.spider.util.JsonResult;
import com.ejushang.spider.util.WebUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.json.MappingJacksonJsonView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;


@Controller
public class LoginController {

//    RandomNumberGenerator rng = new SecureRandomNumberGenerator();

    @Autowired
    private IUserService userService;

    @Autowired
    private IRoleService roleService;

//    @RequestMapping(value = "/login")
//    public String loginPage(){
//
////		return "/user/login";
//        return "login.html";
//    }

    @RequestMapping("/")
    public String index(){
        return "redirect:/index.html";
    }

    @RequestMapping("/404")
    public String pageNotFound(HttpServletRequest request ,HttpServletResponse response) throws IOException {
        if(WebUtils.isAjaxRequest(request)) {
            new JsonResult(false).setMsg("您访问的页面不存在").toJson(response);
        } else {
            response.getOutputStream().write("Page Not Found".getBytes("UTF-8"));
        }
        return null;
    }

//    @RequestMapping("/")
//    public String index(){
//        return "/index";
//    }
//
//    @RequestMapping(value = "/register", method = RequestMethod.GET)
//    public String registerPage(Model model){
//        model.addAttribute("roles", roleService.findAllRole());
//        return "/user/register";
//    }
//
//    @RequestMapping(value = "/register", method = RequestMethod.POST)
//    public String register(String username, String password, Integer roleId){
//        User user = new User();
//        user.setUsername(username);
//        String salt = rng.nextBytes().toBase64();
//        user.setPassword(new Sha256Hash(password, salt).toBase64());
//        user.setSalt(salt);
//        user.setRoleId(roleId);
//        user.setRootUser(false);
//        userService.saveUser(user);
//        return "redirect:/login.action";
//    }
//
//    @RequestMapping(value = "/order/*")
//    public String orderPage(){
//        return "/order/order";
//    }
//
//    @RequestMapping(value = "/unauthorized")
//    public String unauthorized(){
//        return "/unauthorized";
//    }
}
