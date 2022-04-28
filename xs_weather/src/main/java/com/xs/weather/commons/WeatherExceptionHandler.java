package com.xs.weather.commons;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 全局异常处理
 */
@ControllerAdvice
public class WeatherExceptionHandler {
    private static final Log logger = LogFactory.getLog(WeatherExceptionHandler.class);
    public static final String X_REQUESTED_WITH = "X-Requested-With";
    public static final String XML_HTTP_REQUEST = "XMLHttpRequest";
    public static final String ERROR_VIEW = "resources/error";


    @ResponseBody
    @ExceptionHandler(value = {BusinessException.class, Exception.class})
    @ResponseStatus(HttpStatus.OK)
    public Object handler(HttpServletRequest request, HttpServletResponse response, Exception exception) {
        String method = request.getMethod();
        String message = exception.getMessage();
        StringBuffer requestURL = request.getRequestURL();
        logger.error(String.format("请求%s---- %s ---发生异常--- %s", requestURL, method, message));
        HttpStatus status = getStatus(request);

        exception.printStackTrace();
        boolean ajax = ajax(request);
        if (ajax) {
            return Result.fail(exception.getMessage());
        } else {
            ModelAndView mv = new ModelAndView();
            mv.addObject("exception", exception);
            mv.addObject("url", requestURL);
            mv.addObject("message", message);
            mv.setViewName(String.valueOf(status.value()));
            return mv;
        }
    }

    public static boolean ajax(HttpServletRequest httpRequest) {
        String requestedSessionId = httpRequest.getRequestedSessionId();
        if (StringUtils.isBlank(requestedSessionId)) {
            return true;
        }
        String requestHeader = httpRequest.getHeader(X_REQUESTED_WITH);
        boolean result = XML_HTTP_REQUEST.equals(httpRequest.getHeader(X_REQUESTED_WITH));
        return ((StringUtils.isNotBlank(requestHeader) && result));
    }

    protected HttpStatus getStatus(HttpServletRequest request) {
        Integer statusCode = (Integer) request
                .getAttribute("javax.servlet.error.status_code");
        if (statusCode == null) {
            return HttpStatus.INTERNAL_SERVER_ERROR;
        }
        try {
            return HttpStatus.valueOf(statusCode);
        } catch (Exception ex) {
            return HttpStatus.INTERNAL_SERVER_ERROR;
        }
    }
}
