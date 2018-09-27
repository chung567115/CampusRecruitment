package xin.chung.job.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import xin.chung.job.entity.User;
import xin.chung.job.entity.UserDTO;
import xin.chung.job.repository.LoginRepository;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;

/**
 * @Author Chung
 * @Date 2018/09/22 20:10
 */
@Service
public class LoginService {
    @Autowired
    private LoginRepository loginRepository;

    public User login(String username, String password, HttpServletRequest request) {
        User user = loginRepository.findByUsernameAndPassword(username, password);
        if (null == user) {
            return null;
        }

        user.setLastLogin(new Date());
        loginRepository.save(user);
        //把数据存入session 中
        request.getSession().setAttribute("loginInfo", user);
        request.getSession().setMaxInactiveInterval(12 * 60 * 60);
        user.setPassword("******");
        return user;
    }

    public User resetPassword(int userId, UserDTO userDTO) {
        User old = loginRepository.findById(userId);
        if (userDTO.getUsername().equals(old.getUsername())) {
            old.setPassword(userDTO.getPassword());
            loginRepository.save(old);
        }
        old.setPassword("******");
        return old;
    }
}
