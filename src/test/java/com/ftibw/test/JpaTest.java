package com.ftibw.test;

import com.ftibw.bean.User;
import com.ftibw.service.UserServiceImpl;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Date;
import java.util.concurrent.TimeUnit;

/**
 * @author : Ftibw
 * @date : 2019/1/18 9:47
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class JpaTest {
    @Autowired
    private UserServiceImpl userService;

    @Test
    public void jpaTest() {
        Date date = new Date();
        User user = new User(null, "ftibw", "root", "123456", "wuhan", date, date);
        System.out.println(userService.saveUser(user));//打印出id值---32位字符串
        try {
            Thread.sleep(TimeUnit.HOURS.toMillis(24));
        } catch (InterruptedException ignored) {
        }
    }
}
