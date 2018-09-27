package xin.chung.job.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import xin.chung.job.entity.User;
import xin.chung.job.entity.UserDTO;
import xin.chung.job.service.LoginService;

import javax.servlet.http.HttpServletRequest;

/**
 * @Author Chung
 * @Date 2018/09/22 20:05
 */
//@CrossOrigin(origins = "http://127.0.0.1:8081", maxAge = 3600, allowCredentials = "true")
@CrossOrigin(origins = "http://www.chung.xin:80", maxAge = 3600, allowCredentials = "true")
@RestController
public class LoginController {
    @Autowired
    public LoginService loginService;

    @PostMapping("/login")
    public User login(HttpServletRequest request, @RequestBody UserDTO userDTO) {
        return loginService.login(userDTO.getUsername(), userDTO.getPassword(), request);
    }

    /**
     * 重置密码
     * @param request
     * @param userDTO
     * @return
     */
    @PutMapping("/job/resetPass")
    public User reset(HttpServletRequest request, @RequestBody UserDTO userDTO) {
        return loginService.resetPassword(getLoginUserId(request), userDTO);
    }

    /**
     * 获取当前登录用户ID
     *
     * @param request
     * @return
     */
    private int getLoginUserId(HttpServletRequest request) {
        User loginUser = (User) request.getSession().getAttribute("loginInfo");
        return loginUser.getId();
    }

}
