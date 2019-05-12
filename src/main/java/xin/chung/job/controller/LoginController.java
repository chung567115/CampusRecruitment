package xin.chung.job.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
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
@CrossOrigin(origins = "http://192.168.0.7:8080", maxAge = 3600, allowCredentials = "true")
@RestController
@Api(value = "login", description = "用户相关API")
public class LoginController {
    @Autowired
    public LoginService loginService;

    /**
     * 登录接口
     *
     * @param request
     * @param userDTO
     * @return
     */
    @PostMapping("/login")
    @ApiOperation(value = "登录", response = User.class)
    public User login(HttpServletRequest request,
                      @ApiParam(value = "用户名密码", required = true) @RequestBody UserDTO userDTO) {
        return loginService.login(userDTO.getUsername(), userDTO.getPassword(), request);
    }

    /**
     * 注册接口
     * @param userDTO
     * @return
     */
    @PostMapping("/register")
    @ApiOperation(value = "注册", response = User.class)
    public User register(@ApiParam(value = "用户名密码", required = true) @RequestBody UserDTO userDTO) {
        return loginService.register(userDTO.getUsername(), userDTO.getPassword());
    }

    /**
     * 重置密码
     *
     * @param request
     * @param userDTO
     * @return
     */
    @PutMapping("/job/resetPass")
    @ApiOperation(value = "重置密码", response = User.class)
    public User reset(HttpServletRequest request,
                      @ApiParam(value = "用户名密码", required = true) @RequestBody UserDTO userDTO) {
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
