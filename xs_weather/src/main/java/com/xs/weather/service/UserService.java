package com.xs.weather.service;

import com.xs.weather.commons.BusinessException;
import com.xs.weather.dto.LoginDTO;
import com.xs.weather.entity.User;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.teasoft.bee.osql.SuidRich;

import javax.naming.Name;
import java.util.Objects;

/**
 * Copyright (C)
 * FileName: UserService
 * Author:   maokai
 * Date:     2022/4/28 12:59
 * Description: user biz
 */
@Log4j2
@Service
@AllArgsConstructor
public class UserService {

    private final SuidRich suidRich;

    /**
     * 通过用户名和密码查询用户
     *
     * @param name 用户名
     * @return 返回用户信息
     */
    public User findUserByName(String name) {
        User build = new User();
        build.setName(name);
        User user = this.suidRich.selectOne(build);
        return user;
    }

    /**
     * 登录 密码校验
     *
     * @param dto 登录参数
     * @return 返回登录结果
     */
    public boolean login(LoginDTO dto) {
        User user = findUserByName(dto.getUserName());
        if (Objects.isNull(user)) {
            throw new BusinessException("用户不存在");
        }
        if (!StringUtils.equals(user.getPaswd(), dto.getPassword())) {
            throw new BusinessException("密码错误");
        }
        return true;
    }
}
