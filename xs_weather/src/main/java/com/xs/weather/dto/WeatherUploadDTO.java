package com.xs.weather.dto;

import cn.afterturn.easypoi.excel.annotation.Excel;
import lombok.Data;

import java.io.Serializable;

/**
 * Copyright (C)
 * FileName: WeatherUploadDTO
 * Author:   maokai
 * Date:     2022/4/28 14:55
 * Description:
 */
@Data
public class WeatherUploadDTO implements Serializable {
    @Excel(name = "地区编码")
    /**
     * 地区编码
     */
    private String regionCode;
    /**
     * 地区名称
     */
    @Excel(name = "地区名称")
    private String regionName;
    /**
     * 天气数据
     */
    @Excel(name = "天气")
    private String weatherData;
}
