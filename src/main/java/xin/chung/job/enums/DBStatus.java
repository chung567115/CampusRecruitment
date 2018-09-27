package xin.chung.job.enums;

/**
 * @Author Chung
 * @Date 2018/09/24 23:15
 */
public enum DBStatus {
    NOR(0, "正常"),
    DEL(1, "删除");
    public final int value;
    public final String desc;

    DBStatus(int value, String desc) {
        this.value = value;
        this.desc = desc;
    }
}
