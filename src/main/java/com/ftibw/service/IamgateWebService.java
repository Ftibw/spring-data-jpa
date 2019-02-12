package com.ftibw.service;

import javax.jws.WebParam;
import javax.jws.WebService;

/**
 * @author : Ftibw
 * @date : 2019/2/12 18:36
 */
@WebService
public interface IamgateWebService {

    IamgateWsResponse verifySessionAgain(@WebParam(name = "sessionToken") String sessionToken,
                                         @WebParam(name = "appId")String appId,
                                         @WebParam(name = "iamToken")String iamToken);
}
