package com.kuainiu.qt.data.service.code;

/**
 * Created by IntelliJ IDEA.
 * User: Jixuan
 * Date: 2019-08-23
 * Time: 15:12
 * @author user
 */
public enum SnapshotPortfolioCode {
    /**
     * snapshot portfolio code: success
     */
    SUCCESS("SUCCESS", "数据正常"),
    /**
     * snapshot portfolio code: error
     */
    ERROR("ERROR", "数据计算异常"),
    /**
     * snapshot portfolio code: repair
     */
    REPAIR("REPAIR", "修数标记"),

    ;


    private String code;

    private String desc;

    SnapshotPortfolioCode(String code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }


}
