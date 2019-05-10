package com.ftibw.service.impl;

import com.ftibw.bean.User;
import com.ftibw.repository.UserRepository;
import com.ftibw.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author : Ftibw
 * @date : 2019/1/18 9:42
 */
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public List<User> listUsers() {
        return userRepository.findAll();
    }

    @Override
    public User saveUser(User user) {
        userRepository.save(user);
        return user;
    }
}
