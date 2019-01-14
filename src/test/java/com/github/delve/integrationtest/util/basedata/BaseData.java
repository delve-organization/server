package com.github.delve.integrationtest.util.basedata;

public interface BaseData {

    void load();

    void unload();

    default Class<? extends BaseData> dependsOn() {
        return null;
    }
}
