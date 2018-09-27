package xin.chung.job.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import xin.chung.job.entity.Recruitment;

import java.util.List;

/**
 * @Author Chung
 * @Date 2018/09/22 18:54
 */
//@Repository
public interface JobRepository extends JpaRepository<Recruitment, Integer> {
    Page<Recruitment> findAll(Specification<Recruitment> spec, Pageable pageable);

    Recruitment findByIdAndStatus(int id, int status);

    Page<Recruitment> findByUserIdAndStatus(int userId, int status, Pageable pageable);

    List<Recruitment> findAllByUserIdAndStatus(int userId, int status);
}
