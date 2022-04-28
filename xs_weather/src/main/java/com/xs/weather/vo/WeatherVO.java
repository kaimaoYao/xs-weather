package com.xs.weather.vo;

import cn.afterturn.easypoi.excel.annotation.Excel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * Copyright (C)
 * FileName: WeatherVO
 * Author:   maokai
 * Date:     2022/4/28 14:05
 * Description:
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class WeatherVO implements Serializable {

    @Excel(name = "地区编码")
    private String regionCode;

    @Excel(name = "地区名称", orderNum = "1")
    private String regionName;

    @Excel(name = "天气数据", orderNum = "2")
    private String weatherData;
}
