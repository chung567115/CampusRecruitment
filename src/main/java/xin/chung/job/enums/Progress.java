package xin.chung.job.enums;

import org.springframework.util.StringUtils;

/**
 * @Author Chung
 * @Date 2018/09/22 18:41
 */
public enum Progress {
    RESUME_SUBMIT(0, "初筛"),
    WAIT_TO_EXAM(1, "待笔试"),
    WAIT_TECH_INTERVIEW1(2, "待初面"),
    WAIT_TECH_INTERVIEW2(3, "待复面"),
    WAIT_TECH_INTERVIEW3(4, "待三面"),
//    WAIT_TECH_INTERVIEW4(5, "待四面"),
    WAIT_HR_INTERVIEW(6, "待HR面"),
    WAIT_THE_OFFER(7, "待通知"),
    ALL_FINISH(8, "被拒"),
    GOT_OFFER(9, "OFFER");


    public final int value;
    public final String desc;

    Progress(int value, String desc) {
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
        for (Progress pro : Progress.values()) {
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
        for (Progress item : Progress.values()) {
            if (item.desc.equals(desc)) {
                return item.value;
            }
        }
        return -1;
    }

}