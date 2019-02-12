package com.ftibw.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.Map;

/**
 * @author : Ftibw
 * @date : 2019/2/12 12:42
 */
@Controller
public class TestController {

    @GetMapping(value = "/acs")
    public String addUser(HttpServletRequest req, HttpSession session, Model model) {
        // TODO soap 统一接口认证
        String iamToken = req.getParameter("iamToken");
        String sessionToken = req.getParameter("sessionToken");

        session.setAttribute("sessionToken", sessionToken);
        //读取响应参数
        String samlResponseParam = req.getParameter("samlResponseParam");
        //samlResponseParam-->samlResponse-->attrs
        Map<String, String> attrs = new HashMap() {
            private HashMap<String, String> attributes = new HashMap<String, String>() {
                {
                    put("domain", "kmsz");
                    put("account", "admin");
                    put("password", "666666");
                }
            };

            Map<String, String> getAttributes() {
                return attributes;
            }
        }.getAttributes();

        attrs.forEach(model::addAttribute);
        return "/login";
    }
}
