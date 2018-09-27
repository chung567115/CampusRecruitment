package xin.chung.job.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Author Chung
 * @Date 2018/09/24 21:23
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ResponseDTO {
    public int code;
    public String msg;
}
