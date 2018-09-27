package xin.chung.job.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Date;

/**
 * @Author Chung
 * @Date 2018/09/22 18:37
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "campus_recruitment")
public class Recruitment implements Serializable {
    @Id
    @GeneratedValue
    private Integer id;

    @NotNull(message = "userId不能为空")
    private Integer userId;

    @NotNull(message = "公司名称不能为空")
    private String companyName;

    @NotNull(message = "工作地址不能为空")
    private Integer companyAddress;

    @NotNull(message = "提交时间不能为空")
    private Date submitTime;

    @NotNull(message = "提交类别不能为空")
    private Integer submitType;

    @NotNull(message = "进度不能为空")
    private Integer progress;

    private String comment;
    private Date updateTime;
    private String history;
    private Integer status;
}
