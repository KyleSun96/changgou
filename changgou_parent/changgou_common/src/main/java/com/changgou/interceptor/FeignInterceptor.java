package com.changgou.interceptor;

import feign.RequestInterceptor;
import feign.RequestTemplate;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.Enumeration;

/**
 * @Program:
 * @ClassName: FeignInterceptor
 * @Description: feign拦截器：传递令牌，解决微服务之间的验证问题
 * @Author: KyleSun
 **/
@Component
public class FeignInterceptor implements RequestInterceptor {
    @Override
    public void apply(RequestTemplate requestTemplate) {
        // 传递令牌
        RequestAttributes requestAttributes = RequestContextHolder.getRequestAttributes();
        if (requestAttributes != null) {
            HttpServletRequest request = ((ServletRequestAttributes) requestAttributes).getRequest();
            // 获取所有请求头信息
            Enumeration<String> headerNames = request.getHeaderNames();
            while (headerNames.hasMoreElements()) {
                String headerName = headerNames.nextElement();
                if ("authorization".equals(headerName)) {
                    // headerValue即为header中的authorization，格式为：Bearer jwt
                    String headerValue = request.getHeader(headerName);
                    // 传递令牌
                    requestTemplate.header(headerName, headerValue);
                }
            }
        }
    }
}
