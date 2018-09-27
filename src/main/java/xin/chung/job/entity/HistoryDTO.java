package xin.chung.job.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Author Chung
 * @Date 2018/09/25 23:01
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class HistoryDTO {
    public String progress;
    public String updateTime;
}
