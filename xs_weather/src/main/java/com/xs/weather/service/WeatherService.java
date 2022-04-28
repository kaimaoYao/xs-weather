package com.xs.weather.service;

import cn.afterturn.easypoi.excel.ExcelExportUtil;
import cn.afterturn.easypoi.excel.entity.ExportParams;
import cn.hutool.core.convert.Convert;
import cn.hutool.core.lang.TypeReference;
import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.Lists;
import com.google.common.util.concurrent.ThreadFactoryBuilder;
import com.xs.weather.commons.BusinessException;
import com.xs.weather.dto.DownloadExcelDTO;
import com.xs.weather.dto.WeatherUploadDTO;
import com.xs.weather.entity.Weather;
import com.xs.weather.utils.OkHttpClientUtils;
import com.xs.weather.vo.WeatherVO;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import okhttp3.Headers;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.stereotype.Service;
import org.teasoft.bee.osql.SuidRich;

import java.io.IOException;
import java.io.OutputStream;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.Callable;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * Copyright (C)
 * FileName: WeatherService
 * Author:   maokai
 * Date:     2022/4/28 13:02
 * Description:
 */
@Log4j2
@Service
@AllArgsConstructor
public class WeatherService {

    private final SuidRich suidRich;

    /**
     * 下载
     *
     * @param query
     * @param outputStream
     */

    public void downloadExcel(DownloadExcelDTO query, OutputStream outputStream) {
        Weather weather = new Weather();
        List<Weather> list = suidRich.select(weather, 0, 100);
        List<WeatherVO> listVO = Lists.newArrayList();
        if (CollectionUtils.isNotEmpty(list)) {
            requestWeatherData(list);
            listVO = Convert.convert(new TypeReference<List<WeatherVO>>() {
            }, list);
        }
        Workbook workbook = ExcelExportUtil.exportExcel(new ExportParams(), WeatherVO.class, listVO);
        try {
            workbook.write(outputStream);
        } catch (IOException e) {
            throw new BusinessException(e.getMessage());
        } finally {
            try {
                if (workbook == null) {
                    workbook.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 开启多线程，请求天气数据
     *
     * @param list
     */
    public void requestWeatherData(List<Weather> list) {
        //创建线程池
        final ThreadPoolExecutor ex = new ThreadPoolExecutor(
                4,
                10,
                5,
                TimeUnit.SECONDS,
                new ArrayBlockingQueue(5),
                new ThreadFactoryBuilder().build(),
                new ThreadPoolExecutor.CallerRunsPolicy());

        list.forEach(weather -> {
            String dataUrl = concatWeatherDataUrl(weather);
            ex.submit(new Task(dataUrl, weather));
        });
    }

    /**
     * 拼接处理请求url
     *
     * @param weather
     * @return
     */
    private String concatWeatherDataUrl(Weather weather) {
        String weatherDataHost = "http://www.weather.com.cn/data/sk/";
        String weatherDataHostSuffix = ".html";
        String regionCode = weather.getRegionCode();
        String dataUrl = weatherDataHost.concat(regionCode).concat(weatherDataHostSuffix);
        return dataUrl;
    }

    /**
     * 保存导入的数据
     *
     * @param list
     */
    public void saveBath(List<WeatherUploadDTO> list) {
        List<Weather> data = Convert.convert(new TypeReference<List<Weather>>() {
        }, list);
        suidRich.insert(data);
    }

    /**
     * 开启任务
     */
    private class Task implements Callable<String> {
        private final String url;
        private final Weather weather;

        public Task(String url, Weather weather) {
            this.url = url;
            this.weather = weather;
        }

        @Override
        public String call() throws Exception {
            try {
                OkHttpClientUtils okHttpClient = new OkHttpClientUtils();
                String data = okHttpClient.get(null, url, Headers.of());
                log.info("请求返回数据：{}: {}", url, data);
                if (StringUtils.isBlank(data)) {
                    return "";
                }
                JSONObject jsonObject = JSONObject.parseObject(data);
                JSONObject weatherinfo = jsonObject.getJSONObject("weatherinfo");
                if (Objects.isNull(weatherinfo)) {
                    return "";
                }
                String temp = weatherinfo.getString("temp");
                log.info("天气数据 ---->{},{}", weather.getRegionCode(), temp);
                weather.setWeatherData(temp);
                return temp;
            } catch (IOException e) {
                e.printStackTrace();
                return null;
            }
        }
    }
}
