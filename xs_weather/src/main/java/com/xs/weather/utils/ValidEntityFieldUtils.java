package com.xs.weather.utils;

import com.google.common.collect.Lists;
import com.xs.weather.commons.BusinessException;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Copyright (C)
 * FileName: ValidEntityFieldUtils
 * Author:   maokai
 * Date:     2022/4/28 11:57
 * Description: 参数校验工具
 */
public class ValidEntityFieldUtils {
    private static final Log logger = LogFactory.getLog(ValidEntityFieldUtils.class);

    public static void valid(BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            List<String> messageList = Lists.newArrayList();
            for (ObjectError objectError : bindingResult.getAllErrors()) {
                String defaultMessage = objectError.getDefaultMessage();
                defaultMessage = StringUtils.isNotBlank(defaultMessage) ? defaultMessage : "";
                String concat = ((FieldError) objectError).getField().concat(":").concat(defaultMessage);
                messageList.add(concat);
            }
            String message = messageList.stream().collect(Collectors.joining(","));
            logger.error("参数校验---->" + message);
            throw new BusinessException(message);
        }
    }
}
