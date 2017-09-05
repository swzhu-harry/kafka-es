package com.bitnei.es.bean;

import java.util.Map;

/**
 * Created by IntelliJ IDEA.
 * User: zhaogd
 * Date: 2017/9/5
 */
public class VehicleUpdateBasics {

    private String condition;
    private String conditionValue;
    private Map<String, Object> value;


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
