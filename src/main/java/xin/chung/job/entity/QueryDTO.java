package xin.chung.job.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Author Chung
 * @Date 2018/09/25 18:04
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class QueryDTO {
    String name;
    String progress;
    String addr;
    int page;
    int pageSize;
}
