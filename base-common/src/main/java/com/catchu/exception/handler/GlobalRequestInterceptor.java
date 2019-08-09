package com.catchu.exception.handler;

import com.catchu.logging.GlobalRequestContext;
import com.google.common.collect.Maps;
import org.slf4j.MDC;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Enumeration;
import java.util.Map;
import java.util.UUID;

import static java.util.Objects.isNull;

/**
 * 统一访问日志
 *
 * @author Administrator
 */
public class GlobalRequestInterceptor extends HandlerInterceptorAdapter {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
            throws Exception {
        String requestId = request.getHeader("request-id");
        if (isNull(requestId) || requestId.isEmpty()) {
            requestId = generateRequestId();
        }
        response.addHeader("request-id", requestId);
        MDC.put("requestId", requestId);

        GlobalRequestContext.setRequestId(requestId);
        GlobalRequestContext.setURL(request.getRequestURI());
        GlobalRequestContext.setApiBegin(System.currentTimeMillis());

        Map<String, String> headers = Maps.newHashMap();
        Enumeration<String> headerNames = request.getHeaderNames();
        while (headerNames.hasMoreElements()) {
            String s = headerNames.nextElement();
            headers.put(s, request.getHeader(s));

            putIntoMDC(s, request.getHeader(s));
        }
        GlobalRequestContext.putRequestHeaders(headers);
        return super.preHandle(request, response, handler);
    }

    private static final String DEVICE_ID = "DeviceId";

    private static final String USER_AGENT = "user-agent";

    private void putIntoMDC(String headerName, String headerValue) {
        if (DEVICE_ID.equalsIgnoreCase(headerName)) {
            MDC.put(DEVICE_ID, headerValue);
        }

        if (USER_AGENT.equalsIgnoreCase(headerName)) {
            MDC.put(USER_AGENT, headerValue);
        }

    }

    private String generateRequestId() {
        return UUID.randomUUID().toString().replace("-", "");
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
            throws Exception {
        super.afterCompletion(request, response, handler, ex);
    }
}
