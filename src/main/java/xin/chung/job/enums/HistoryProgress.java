package xin.chung.job.enums;

import org.springframework.util.StringUtils;

/**
 * @Author Chung
 * @Date 2018/09/22 18:41
 */
public enum HistoryProgress {
    RESUME_SUBMIT(0, "初筛完成"),
    WAIT_TO_EXAM(1, "笔试完成"),
    WAIT_TECH_INTERVIEW1(2, "初面完成"),
    WAIT_TECH_INTERVIEW2(3, "复面完成"),
    WAIT_TECH_INTERVIEW3(4, "三面完成"),
    WAIT_TECH_INTERVIEW4(5, "四面完成"),
    WAIT_HR_INTERVIEW(6, "待HR面完成"),
    WAIT_THE_OFFER(7, "OFFER"),
    ALL_FINISH(8, "结束");

    public final int value;
    public final String desc;

    HistoryProgress(int value, String desc) {
        this.value = value;
        this.desc = desc;
    }


    /**
     * 值转描述
     *
     * @param value 值
     * @return 描述
     */
    public static String value2Desc(Integer value) {
        if (null == value) {
            return "";
        }
        for (HistoryProgress pro : HistoryProgress.values()) {
            if (pro.value == value) {
                return pro.desc;
            }
        }
        return "";
    }

    /**
     * 描述转值
     *
     * @param desc 状态描述
     * @return 枚举
     */
    public static int descToValue(String desc) {
        if (StringUtils.isEmpty(desc.trim())) {
            return -1;
        }
        for (HistoryProgress item : HistoryProgress.values()) {
            if (item.desc.equals(desc)) {
                return item.value;
            }
        }
        return -1;
    }

}