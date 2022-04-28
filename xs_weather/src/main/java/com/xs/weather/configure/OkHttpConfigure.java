package com.xs.weather.configure;

import com.xs.weather.utils.OkHttpClientUtils;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Copyright (C)
 * FileName: OkHttpConfingure
 * Author:   maokai
 * Date:     2021/3/12 10:22
 * Description:
 */
@Configuration
public class OkHttpConfigure {

    @Bean(name = "okHttpClient")
    public OkHttpClientUtils clientUtils() {
         return new OkHttpClientUtils();
    }
}
