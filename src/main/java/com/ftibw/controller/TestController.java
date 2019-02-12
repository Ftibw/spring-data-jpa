package com.ftibw.controller;

import com.ftibw.service.IamgateWebService;
import com.ftibw.service.IamgateWsResponse;
import org.apache.cxf.endpoint.Client;
import org.apache.cxf.frontend.ClientProxy;
import org.apache.cxf.jaxws.JaxWsProxyFactoryBean;
import org.apache.cxf.jaxws.endpoint.dynamic.JaxWsDynamicClientFactory;
import org.apache.cxf.transport.http.HTTPConduit;
import org.apache.cxf.transports.http.configuration.HTTPClientPolicy;
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

    public static final String IAM_WS_URL = "http://10.240.41.10:31201/iamgate/services/IamgateWebService?wsdl";

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
        Client client = dcflient.createClient(IAM_WS_URL);
        try {
            //status, message, code
            Object[] objects = client.invoke("verifySessionAgain", sessionToken, appId, iamToken);
            ret = "success".equals(objects[0]);
        } catch (Exception ignored) {
        }
        return ret;
    }

    private boolean verifyToken(String sessionToken, String appId, String iamToken) {
        boolean ret = false;
        JaxWsProxyFactoryBean jaxWsProxyFactoryBean = new JaxWsProxyFactoryBean();
        jaxWsProxyFactoryBean.setAddress(IAM_WS_URL);
        jaxWsProxyFactoryBean.setServiceClass(IamgateWebService.class);

        IamgateWebService iamService = (IamgateWebService) jaxWsProxyFactoryBean.create();
        Client proxy = ClientProxy.getClient(iamService);
        HTTPConduit conduit = (HTTPConduit) proxy.getConduit();
        HTTPClientPolicy policy = new HTTPClientPolicy();
        policy.setConnectionTimeout(1000);
        policy.setReceiveTimeout(1000);
        conduit.setClient(policy);

        try {
            IamgateWsResponse wsResp = iamService.verifySessionAgain(sessionToken, appId, iamToken);
            ret = "success".equals(wsResp.getStatus());
        } catch (Exception ignored) {
        }
        return ret;
    }
}
