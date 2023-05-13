package com.csub.util;

public enum CarStatusList {
    AVAILABLE(1),
    UNAVAILABLE(2);

    private final int statusId;

    CarStatusList(int statusId) {
        this.statusId = statusId;
    }

    public int getStatusId() {
        return statusId;
    }
}
