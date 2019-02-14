package com.ftibw.controller;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.cxf.endpoint.Client;
import org.apache.cxf.jaxws.endpoint.dynamic.JaxWsDynamicClientFactory;
import org.apache.cxf.transport.http.HTTPConduit;
import org.apache.cxf.transports.http.configuration.HTTPClientPolicy;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

    private static Logger logger = LoggerFactory.getLogger("acs_log");

    public static final ObjectMapper MAPPER = new ObjectMapper();

    //10.240.41.10
    public static final String IAM_WS_URL = "http://127.0.0.1:31201/iamgate/services/IamgateWebService?wsdl";

    @GetMapping(value = "/acs")
    public String addUser(HttpServletRequest req, HttpSession session, Model model) {
        //统一接口认证
        String sessionToken = req.getParameter("sessionToken");
        String appId = req.getParameter("appId");
        String iamToken = req.getParameter("iamToken");
        if (!verifyToken0(sessionToken, appId, iamToken)) {
            return "/toIndex";
        }
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
        return "/toHome";//redirect:/#!/dashboard/home
    }


    private boolean verifyToken0(String sessionToken, String appId, String iamToken) {
        boolean ret = false;
        JaxWsDynamicClientFactory dcflient = JaxWsDynamicClientFactory.newInstance();
        Client client = null;
        try {
            client = dcflient.createClient(IAM_WS_URL);
        } catch (Exception e) {
            logger.info("iam connect failed",e);
            return false;
        }
        HTTPConduit conduit = (HTTPConduit) client.getConduit();
        HTTPClientPolicy policy = new HTTPClientPolicy();
        policy.setConnectionTimeout(1000);
        policy.setReceiveTimeout(1000);
        conduit.setClient(policy);

        try {
            //status, message, code
            Object[] objects = client.invoke("verifySessionAgain", sessionToken, appId, iamToken);
            Object respObj = objects[0];
            String respJson;
            if (respObj instanceof String) {
                respJson = (String) respObj;
            } else if (null != respObj) {
                respJson = MAPPER.writeValueAsString(respObj);
            } else {
                return false;
            }
            Map<String, Object> map = MAPPER.readValue(respJson, new TypeReference<Map<String, Object>>() {
            });
            ret = "success".equals(map.get("status"));
        } catch (Exception e) {
            logger.info("iam socket timeout",e);
        }
        return ret;
    }
}
