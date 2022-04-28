package com.xs.weather.controller;

import cn.afterturn.easypoi.excel.ExcelImportUtil;
import cn.afterturn.easypoi.excel.entity.ImportParams;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.xs.weather.commons.Result;
import com.xs.weather.dto.DownloadExcelDTO;
import com.xs.weather.dto.WeatherUploadDTO;
import com.xs.weather.service.WeatherService;
import io.swagger.annotations.Api;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;
import java.util.List;

/**
 * Copyright (C)
 * FileName: WeatherController
 * Author:   maokai
 * Date:     2022/4/28 12:23
 * Description:
 */
@Api(tags = "天气控制器")
@Log4j2
@RestController
@AllArgsConstructor
@RequestMapping("weather")
public class WeatherController {

    private final WeatherService weatherService;
    private final ObjectMapper objectMapper;

    @PostMapping("uploadExcel")
    public Result uploadExcel(@RequestParam("file") MultipartFile file) throws Exception {
        ImportParams importParas = new ImportParams();
        List<WeatherUploadDTO> list = ExcelImportUtil.importExcel(file.getInputStream(), WeatherUploadDTO.class, importParas);
        log.info("导入数据:{}",objectMapper.writeValueAsString(list));
        weatherService.saveBath(list);
        return Result.success();
    }

    @GetMapping("download")
    public void downloadExcel(DownloadExcelDTO query, HttpServletResponse response, HttpServletRequest request) {
        try (OutputStream outputStream = response.getOutputStream()) {
            // 重置输出流
            response.reset();
            // 设定输出文件头
            response.setHeader("Content-disposition", "attachment; filename=" + System.currentTimeMillis() + ".xls");
            // 定义输出类型
            response.setContentType("application/msexcel");
            weatherService.downloadExcel(query, outputStream);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
