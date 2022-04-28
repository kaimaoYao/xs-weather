package com.xs.weather.entity;

import io.swagger.annotations.ApiModel;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.teasoft.bee.osql.annotation.Entity;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * Copyright (C)
 * FileName: User
 * Author:   maokai
 * Date:     2022/4/28 13:15
 * Description:
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Entity("user")
@ApiModel(value = "用户")
public class User implements Serializable {

    private Long id;

    private String name;
    /**
     * 密码
     */
    private String paswd;

    private LocalDateTime createTime;

}
