package com.catchu.exception.handler;

import com.alibaba.fastjson.JSONObject;
import com.catchu.exception.beans.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
 * @author junzhongliu
 * @date 2019/8/8 18:59
 */
@Configuration
@Slf4j
public class ExceptionHandlerConfiguration extends WebMvcConfigurerAdapter {


    @Override
    public void configureHandlerExceptionResolvers(List<HandlerExceptionResolver> exceptionResolvers) {
        exceptionResolvers.add((request, response, handler, e) -> {
            ServiceExceptionHandler.ApiExceptionResponse apiExceptionResponse = new ServiceExceptionHandler.ApiExceptionResponse();

            boolean isBusinessException = false;
            if (e instanceof BadRequestException) {
                apiExceptionResponse.setCode(HttpServletResponse.SC_BAD_REQUEST).setMessage(e.getMessage());
            } else if (e instanceof ArgumentException) {
                isBusinessException = true;
                apiExceptionResponse.setCode(HttpServletResponse.SC_BAD_REQUEST).setMessage(e.getMessage());
            } else if (e instanceof ForbiddenException) {
                apiExceptionResponse.setCode(HttpServletResponse.SC_FORBIDDEN).setMessage(e.getMessage());
            }  else if (e instanceof UnauthorizedException) {
                apiExceptionResponse.setCode(HttpServletResponse.SC_UNAUTHORIZED).setMessage(e.getMessage());
            }else if (e instanceof CustomException) {
                isBusinessException = true;
                apiExceptionResponse.setCode(((CustomException) e).getCode()).setMessage(e.getMessage()).setContent(((CustomException) e).getContent());
            } else {
                String message = e.getMessage();
                apiExceptionResponse.setCode(HttpServletResponse.SC_INTERNAL_SERVER_ERROR).setMessage(message);
            }

            if (!isBusinessException) {
                response.setStatus(apiExceptionResponse.getCode());
            } else {
                response.setStatus(HttpServletResponse.SC_OK);
            }

            responseResult(response, apiExceptionResponse);
            return new ModelAndView();
        });
    }

    private void responseResult(HttpServletResponse response, ServiceExceptionHandler.ApiExceptionResponse result) {
        response.setCharacterEncoding("UTF-8");
        response.setHeader("content-type", "application/json;charset=UTF-8");
        try {
            String jsonError = JSONObject.toJSONString(result);
            response.getOutputStream().write(jsonError.getBytes("utf-8"));
        } catch (IOException ex) {
            log.error("error when response:", ex);
        }
    }
}