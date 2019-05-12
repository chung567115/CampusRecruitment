package xin.chung.job.config;

import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

/**
 * @Author Chung
 * @Date 2018/09/22 21:27
 */
@Component
@Order(Ordered.HIGHEST_PRECEDENCE)
@WebFilter(urlPatterns = "/*")
public class ServiceFilter implements Filter {
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        if (request.getRequestURI().contains("/job") || request.getRequestURI().contains("/login")) {
            filterChain.doFilter(servletRequest, servletResponse);
        }
        if(request.getRequestURI().contains("/swagger-ui") || request.getRequestURI().contains("/api-docs")
                || request.getRequestURI().contains("/springfox-swagger-ui") || request.getRequestURI().contains("/swagger-resources")){
            filterChain.doFilter(servletRequest, servletResponse);
        }
    }

    @Override
    public void destroy() {

    }
}
