package com.bitnei.es.bean;

import java.util.Map;

/**
 * ES车辆表更新基础信息实体类
 * <p>
 * Created by IntelliJ IDEA.
 * User: zhaogd
 * Date: 2017/9/5
 */
public class VehicleUpdateBasics {

    private String condition;  // 条件字段名
    private String conditionValue;  // 条件值
    private Map<String, Object> value;  // 需要更新的键值对


    public String getCondition() {
        return condition;
    }

    public void setCondition(String condition) {
        this.condition = condition;
    }

    public String getConditionValue() {
        return conditionValue;
    }

    public void setConditionValue(String conditionValue) {
        this.conditionValue = conditionValue;
    }

    public Map<String, Object> getValue() {
        return value;
    }

    public void setValue(Map<String, Object> value) {
        this.value = value;
    }
}
