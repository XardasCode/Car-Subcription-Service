package com.csub.util;

public enum SubscriptionStatusList {
    UNDER_CONSIDERATION(1),
    CONFIRM_STATUS(2),
    REJECT_STATUS(3);

    private final int statusId;

    SubscriptionStatusList(int statusId) {
        this.statusId = statusId;
    }

    public int getStatusId() {
        return statusId;
    }
}
