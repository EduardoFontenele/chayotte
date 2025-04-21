package br.com.chayotte.common.interceptor;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

@Component
@Slf4j
public class RequestTimingInterceptor implements HandlerInterceptor {

    private final ThreadLocal<Long> startTimeLocalThread = new ThreadLocal<>();

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        startTimeLocalThread.set(System.currentTimeMillis());
        return true;
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) {
        var startTime = startTimeLocalThread.get();
        var requestDuration = System.currentTimeMillis() - startTime;

        log.info("Request to {} completed in {} ms with status {}", request.getRequestURI(), requestDuration, response.getStatus());

        startTimeLocalThread.remove();
    }
}
