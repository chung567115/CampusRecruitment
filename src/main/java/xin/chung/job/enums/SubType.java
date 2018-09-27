package xin.chung.job.enums;

import org.springframework.util.StringUtils;

/**
 * @Author Chung
 * @Date 2018/09/24 14:48
 */
public enum SubType {
    WANG_SHEN(0, "网申"),
    NEI_TUI(1, "内推");

    public final int value;
    public final String desc;

    SubType(int value, String desc) {
        this.value = value;
        this.desc = desc;
    }

    /**
     * 值转描述
     * @param value
     * @return
     */
    public static String value2Desc(Integer value) {
        if (null == value) {
            return "";
        }
        for (SubType subType : SubType.values()) {
            if (subType.value == value) {
                return subType.desc;
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
        for (SubType item : SubType.values()) {
            if (item.desc.equals(desc)) {
                return item.value;
            }
        }
        return -1;
    }

}
