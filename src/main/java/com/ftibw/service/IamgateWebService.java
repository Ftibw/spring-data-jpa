package com.ftibw.service;

/**
 * @author : Ftibw
 * @date : 2019/2/12 18:36
 */
public interface IamgateWebService {
    IamgateWsResponse verifySessionAgain(String sessionToken, String appId, String iamToken);
}
