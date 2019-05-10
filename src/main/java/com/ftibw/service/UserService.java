package com.ftibw.service;

import com.ftibw.bean.User;

import java.util.List;

/**
 * @author : Ftibw
 * @date : 2019/5/10 14:12
 */
public interface UserService {

    List<User> listUsers();

    User saveUser(User user);
}
