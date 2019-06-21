package dev.aequitas.boot.api;

import java.util.ArrayList;
import java.util.List;

public class ApiError {

    private List<ApiError> innerError = new ArrayList<>();

    private String code;
    private String desc;
    private String target;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getDesc() {
        return desc;
    }

    public String getTarget() {
        return target;
    }

    public void setTarget(String target) {
        this.target = target;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public List<ApiError> getInnerError() {
        return innerError;
    }
}
