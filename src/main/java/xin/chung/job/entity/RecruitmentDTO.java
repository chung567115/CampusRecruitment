package xin.chung.job.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Author Chung
 * @Date 2018/09/22 18:52
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RecruitmentDTO {
    private int id;
    private String companyName;
    private String companyAddress;
    private String submitTime;
    private String submitType;
    private String progress;
    private String comment;
    private String updateTime;
}
