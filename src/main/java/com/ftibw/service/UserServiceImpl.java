package com.ftibw.service;

import com.ftibw.bean.User;
import com.ftibw.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author : Ftibw
 * @date : 2019/1/18 9:42
 */
@Service
public class UserServiceImpl {

    @Autowired
    private UserRepository userRepository;

    public List<User> listUsers() {
        return userRepository.findAll();
    }

    public User saveUser(User user) {
        userRepository.save(user);
        return user;
    }
}
