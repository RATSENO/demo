package org.ratseno.demo.common.interceptor;

import java.lang.reflect.Method;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.ratseno.demo.common.domain.AccessLog;
import org.ratseno.demo.common.service.AccessLogService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

public class AccessLoggingInterceptor implements HandlerInterceptor {

    private static final Logger log = LoggerFactory.getLogger(AccessLoggingInterceptor.class);

    @Autowired
    private AccessLogService accessLogService;

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
            ModelAndView modelAndView) throws Exception {
        String requestUri = request.getRequestURI();
        String remoteAddr = request.getRemoteAddr();

        log.info("===AccessLoggingInterceptor.postHandle===");
        log.info("===requestUri:{}", requestUri);
        log.info("===remoteAddr:{}", remoteAddr);

        if (handler instanceof HandlerMethod) {
            HandlerMethod handlerMethod = (HandlerMethod) handler;
            Method method = handlerMethod.getMethod();

            Class<?> clazz = method.getDeclaringClass();
            String className = clazz.getName();
            String classSimpleName = clazz.getSimpleName();
            String methodName = method.getName();
            log.info("[ACCESS CONTROLLER]{}.{}", className, methodName);

            AccessLog accessLog = new AccessLog();
            accessLog.setRequestUri(requestUri);
            accessLog.setRemoteAddr(remoteAddr);
            accessLog.setClassName(className);
            accessLog.setClassSimpleName(classSimpleName);
            accessLog.setMethodName(methodName);

            this.accessLogService.register(accessLog);

        } else {
            log.info("===AccessLoggingInterceptor.postHandle.handler:{}", handler);
        }

    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
            throws Exception {
        return true;
    }

}
