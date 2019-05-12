package xin.chung.job.config;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;
import xin.chung.job.entity.User;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @Author Chung
 * @Date 2018/09/22 21:02
 */
@Component
public class LoginInterceptor implements HandlerInterceptor {
    /**
     * 前处理
     */
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
            throws Exception {
        if (request.getRequestURI().contains("login") || request.getMethod().equalsIgnoreCase("OPTIONS")) {
            return true;
        }
        if(request.getRequestURI().contains("/swagger-ui") || request.getRequestURI().contains("/api-docs")
                || request.getRequestURI().contains("/springfox-swagger-ui") || request.getRequestURI().contains("/swagger-resources")){
            return true;
        }
        User loginInfo = (User) request.getSession().getAttribute("loginInfo");
        if (null == loginInfo) {
            return false;
        }
        return true;
    }

    /**
     * handler处理
     */
    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
                           ModelAndView modelAndView) throws Exception {

    }

    /**
     * 后处理
     */
    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
            throws Exception {
    }
}
