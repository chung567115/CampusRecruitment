package xin.chung.job.enums;

import org.springframework.util.StringUtils;

/**
 * @Author Chung
 * @Date 2018/09/22 18:41
 */
public enum HistoryProgress {
    RESUME_SUCCEED(1, "初筛通过"),
    RESUME_FAIL(10, "初筛未通过"),
    EXAM_SUCCEED(2, "笔试通过"),
    EXAM_FAIL(11, "笔试未通过"),
    TECH_INTERVIEW1_SUCCEED(3, "初面通过"),
    TECH_INTERVIEW1_FAIL(12, "初面未通过"),
    TECH_INTERVIEW2_SUCCEED(4, "复面通过"),
    TECH_INTERVIEW2_FAIL(13, "复面未通过"),
    TECH_INTERVIEW3_SUCCEED(5, "三面通过"),
    TECH_INTERVIEW3_FAIL(14, "三面未通过"),
//    TECH_INTERVIEW4_SUCCEED(6, "四面通过"),
//    TECH_INTERVIEW4_FAIL(15, "四面未通过"),
    HR_INTERVIEW_SUCCEED(7, "HR面通过"),
    HR_INTERVIEW_FAIL(16, "HR面未通过"),
    OFFER_FAIL(17, "未拿到OFFER"),
    GOT_OFFER(9, "拿到OFFER");

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