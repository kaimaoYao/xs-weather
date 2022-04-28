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
@Entity("weather")
@ApiModel(value = "天气")
public class Weather implements Serializable {

    private Long id;
    /**
     * 地区编码
     */
    private String regionCode;
    /**
     * 地区名称
     */
    private String regionName;
    /**
     * 天气数据
     */
    private String weatherData;

}
