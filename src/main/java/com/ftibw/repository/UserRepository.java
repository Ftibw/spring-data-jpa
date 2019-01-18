package com.ftibw.repository;

import com.ftibw.bean.User;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author : Ftibw
 * @date : 2019/1/18 9:40
 */
public interface UserRepository extends JpaRepository<User, String> {
}
