package com.ftibw.service.impl;

import com.ftibw.service.IamgateWebService;
import com.ftibw.service.IamgateWsResponse;
import org.springframework.stereotype.Component;

import javax.jws.WebService;

/**
 * 设置serviceName,使wsdl更规范
 *
 * @author : Ftibw
 * @date : 2019/2/13 9:06
 */
@WebService(serviceName = "IamgateWebService",
        targetNamespace = "http://service.ftibw.com",
        endpointInterface = "com.ftibw.service.IamgateWebService")
@Component
public class IamgateWebServiceImpl implements IamgateWebService {

    @Override
    public IamgateWsResponse verifySessionAgain(String sessionToken, String appId, String iamToken) {
        IamgateWsResponse iamgateWsResponse = new IamgateWsResponse();
        iamgateWsResponse.setCode(0);
        iamgateWsResponse.setMessage("认证成功");
        iamgateWsResponse.setStatus("success");
        return iamgateWsResponse;
    }
}
