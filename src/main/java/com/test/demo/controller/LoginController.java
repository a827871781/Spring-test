package com.test.demo.controller;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.test.demo.dao.InfoDao;
import com.test.demo.dao.UserDao;
import com.test.demo.entity.Info;
import com.test.demo.entity.User;
import com.test.demo.utils.TokenUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;
import java.util.List;

/**
 * @Author: syz
 * @Date: 2020/12/17
 */

@RestController
public class LoginController {

    @Autowired
    private UserDao userDao;

    @Autowired
    private InfoDao infoDao;


    @RequestMapping("login")
    public String login(String username, String password) {

        User user = userDao.selectOne(Wrappers.<User>lambdaQuery().eq(User::getUsername, username));
        if (user == null) {
            return "无用户";
        }
        return TokenUtil.generateToken(user.getId() + "", null, Collections.singletonMap("user", user));

    }

    @RequestMapping("list")
    public List<Info> list(@RequestHeader(value = "token") String token) {

       
        return infoDao.selectList(Wrappers.emptyWrapper());

    }

    @RequestMapping("insert")
    public Info insert(@RequestHeader(value = "token") String token) {

        User user = TokenUtil.getUserFromToken(token);


        Info info = new Info();
        info.setName("11");
        info.setAge(1);
        info.setSex("男");


        infoDao.insert(info);

        return info;

    }


}
