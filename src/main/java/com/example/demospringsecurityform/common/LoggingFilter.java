package com.example.demospringsecurityform.common;

import org.springframework.util.StopWatch;
import org.springframework.web.filter.GenericFilterBean;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

public class LoggingFilter extends GenericFilterBean {
//GenericFilterBean은 스프링 상위 클래스

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        StopWatch stopWatch = new StopWatch();
        stopWatch.start(((HttpServletRequest)request).getRequestURI() );//stopWatch start에 task이름 부여

        chain.doFilter(request,response); //chain처리 해야지 다음 필터로 체이닝 연결

        stopWatch.stop();
        logger.info(stopWatch.prettyPrint());  //stopwatch의 정보 뿌리기

    }
}
