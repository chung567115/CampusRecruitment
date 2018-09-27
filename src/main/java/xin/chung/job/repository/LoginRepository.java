package xin.chung.job.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import xin.chung.job.entity.User;

/**
 * @Author Chung
 * @Date 2018/09/22 20:07
 */
public interface LoginRepository extends JpaRepository<User, Integer> {
    User findByUsernameAndPassword(String username, String password);
    User findById(int userId);
}
