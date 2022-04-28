package com.xs.weather.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.xs.weather.commons.Result;
import com.xs.weather.dto.LoginDTO;
import com.xs.weather.service.UserService;
import com.xs.weather.utils.ValidEntityFieldUtils;
import io.swagger.annotations.Api;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * Copyright (C)
 * FileName: UserController
 * Author:   maokai
 * Date:     2022/4/28 12:23
 * Description:
 */
@Api(tags = "用户")
@Log4j2
@RestController
@AllArgsConstructor
@RequestMapping("user")
public class UserController {

    private final UserService userService;
    private final ObjectMapper objectMapper;

    @PostMapping(value = "login")
    public Result login(@RequestBody @Validated LoginDTO dto, BindingResult bindingResult) throws JsonProcessingException {
        //参数校验
        ValidEntityFieldUtils.valid(bindingResult);
        String json = objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(dto);
        log.info("登录----->{}", json);
        boolean result = userService.login(dto);
        return new Result(result);
    }

    @GetMapping(value = "signOut")
    public Result signOut() {
        log.info("登出----->{}", 1);
        return Result.success();
    }
}
