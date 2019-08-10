package com.catchu.enums;

/**
 * DB类型枚举
 */
public enum XDBEnvEnums {
    WRITE(1, "WRITE", "写库"), READ(2, "READ", "读库");

    private int code;
    private String enviromentType;
    private String msg;

    XDBEnvEnums(int code, String enviromentType, String msg) {
        this.code = code;
        this.enviromentType = enviromentType;
        this.msg = msg;
    }

    public int getCode() {
        return this.code;
    }

    public String getEnviromentType() {
        return this.enviromentType;
    }

    public String getMsg() {
        return this.msg;
    }

    public static XDBEnvEnums getDBEnviromentEnumsByCode(int code) {
        XDBEnvEnums targetEnv = null;
        for (XDBEnvEnums enviromentEnums : XDBEnvEnums.values()) {
            if (enviromentEnums.getCode() == code) {
                targetEnv = enviromentEnums;
                break;
            }
        }
        return targetEnv;
    }

    public static XDBEnvEnums getDBEnviromentEnumsByEnviromentType(String enviromentType) {
        XDBEnvEnums targetEnv = null;
        for (XDBEnvEnums enviromentEnums : XDBEnvEnums.values()) {
            if (enviromentType.equalsIgnoreCase(enviromentEnums.getEnviromentType())) {
                targetEnv = enviromentEnums;
                break;
            }
        }
        return targetEnv;
    }
}
