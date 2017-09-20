package com.bitnei.core.constant;

/**
 * Created by IntelliJ IDEA.
 * User: zhaogd
 * Date: 2017/8/7
 */
public enum DateFormatEnum {

    YEAR("yyyy"),

    MONTH("yyyy-MM"),
    MONTH_NO_SEPARATOR("yyyyMM"),

    DATE("yyyy-MM-dd"),
    DATE_NO_SEPARATOR("yyyyMMdd"),

    DATE_TIME("yyyy-MM-dd HH:mm:ss"),
    DATE_TIME_NO_SEPARATOR("yyyyMMddHHmmss");

    private final String format;

    private DateFormatEnum(String format) {
        this.format = format;
    }

    public String getFormat() {
        return format;
    }
}
