package com.xs.weather.dto;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

/**
 * Copyright (C)
 * FileName: LoginDTO
 * Author:   maokai
 * Date:     2022/4/28 12:53
 * Description: login params
 */
@Data
public class LoginDTO {
    @NotBlank(message = "用户名不能为空")
    @Size(min = 4,max = 10,message = "用户名长度至少4个字符，最多不超过10个字符")
    private String userName;
    @NotBlank(message = "密码不能为空")
    @Size(min = 8,max = 24,message = "密码长度至少8个字符，最多不超过24个字符")
    private String password;
}
