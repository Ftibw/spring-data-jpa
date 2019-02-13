package com.ftibw.service;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;

/**
 * 接口和实现在不同包时,一定要保证targetNamespace相同
 *
 * @author : Ftibw
 * @date : 2019/2/12 18:36
 */
@WebService(targetNamespace = "http://service.ftibw.com")
public interface IamgateWebService {

    @WebMethod
    IamgateWsResponse verifySessionAgain(@WebParam(name = "sessionToken") String sessionToken,
                                         @WebParam(name = "appId") String appId,
                                         @WebParam(name = "iamToken") String iamToken);
}
