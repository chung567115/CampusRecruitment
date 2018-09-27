package xin.chung.job.enums;

import org.springframework.util.StringUtils;

/**
 * @Author Chung
 * @Date 2018/09/22 18:41
 */
public enum Address {
    HANG_ZHOU(0, "杭州"),
    BEI_JING(1, "北京"),
    SHANG_HAI(2, "上海"),
    GUANG_ZHOU(3, "广州"),
    SHEN_ZHEN(4, "深圳"),
    WU_HAN(5, "武汉"),
    OTHER(6, "其他");

    public final int value;
    public final String desc;

    Address(int value, String desc) {
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
        for (Address addr : Address.values()) {
            if (addr.value == value) {
                return addr.desc;
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
        for (Address item : Address.values()) {
            if (item.desc.equals(desc)) {
                return item.value;
            }
        }
        return -1;
    }


}