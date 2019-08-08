package com.catchu.exception.handler;

import com.catchu.exception.beans.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import javax.servlet.http.HttpServletRequest;
import static org.springframework.http.HttpStatus.*;

/**
 * @author junzhongliu
 * @date 2019/8/8 18:55
 */
@Slf4j
@RestControllerAdvice
public class ServiceExceptionHandler extends ResponseEntityExceptionHandler {

    private HttpStatus getStatus(HttpServletRequest request) {
        Integer statusCode = (Integer) request.getAttribute("javax.servlet.error.status_code");
        if (statusCode == null) {
            return HttpStatus.INTERNAL_SERVER_ERROR;
        }
        return HttpStatus.valueOf(statusCode);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<?> handleAnyException(HttpServletRequest req, Exception e) {
        HttpStatus statusCode = getStatus(req);
        return ResponseEntity
                .status(statusCode)
                .body(new ApiExceptionResponse(statusCode, e.getMessage()));
    }

    @ResponseBody
    //@ResponseStatus(BAD_REQUEST)
    @ResponseStatus(OK)
    @ExceptionHandler(BadRequestException.class)
    public ApiExceptionResponse handle400(BadRequestException e) {
        return new ApiExceptionResponse()
                .setCode(BAD_REQUEST.value())
                .setMessage(e.getMessage());
    }

    @ResponseBody
    @ResponseStatus(OK)
    @ExceptionHandler(ArgumentException.class)
    public ApiExceptionResponse handle400(ArgumentException e) {
        return new ApiExceptionResponse()
                .setCode(408)
                .setMessage(e.getMessage());
    }

    @ResponseBody
    @ResponseStatus(OK)
    @ExceptionHandler(UnauthorizedException.class)
    public ApiExceptionResponse handle401(UnauthorizedException e) {
        return new ApiExceptionResponse(UNAUTHORIZED, e.getMessage());
    }

    @ResponseBody
    @ResponseStatus(OK)
    @ExceptionHandler(ForbiddenException.class)
    public ApiExceptionResponse handle403(ForbiddenException e) {
        return new ApiExceptionResponse(FORBIDDEN, e.getMessage());
    }

    @ResponseBody
    @ResponseStatus(OK)
    @ExceptionHandler(CustomException.class)
    public ApiExceptionResponse customException(CustomException e) {
        return new ApiExceptionResponse()
                .setCode(e.getCode())
                .setMessage(e.getMessage());
    }

    public interface LogHook {
        void log(HttpServletRequest request, Exception e);
    }

    @Data
    @Accessors(chain = true)
    @NoArgsConstructor
    @AllArgsConstructor
    public static class ApiExceptionResponse {

        public ApiExceptionResponse(HttpStatus status, String message) {
            this.code = status.value();
            this.message = message;
        }

        private int code;

        private String message;

        /**********兼容之前两个参数的构造器**************/
        public ApiExceptionResponse(int code, String message) {
            this.code = code;
            this.message = message;
        }

        /**********3.6.0刘俊重新增**************/
        /**
         * 异常中给出Object类型的内容
         */
        private Object content;
    }
}
