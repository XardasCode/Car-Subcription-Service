package com.csub.util;

public enum SubscriptionStatusList {
    UNDER_CONSIDERATION(3),
    CONFIRM_STATUS(4),
    REJECT_STATUS(5);

    private final int statusId;

    SubscriptionStatusList(int statusId) {
        this.statusId = statusId;
    }

    public int getStatusId() {
        return statusId;
    }
}
