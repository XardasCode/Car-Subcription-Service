package com.csub.entity.audit;

import com.csub.entity.Subscription;
import jakarta.annotation.PreDestroy;
import jakarta.persistence.*;
import lombok.extern.slf4j.Slf4j;

import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;

@Slf4j
public class SubscriptionEntityListener {

    @PrePersist
    public void prePersist(Subscription subscription) {
        log.debug("SubscriptionEntityListener.prePersist() called");
        subscription.setLastUpdateDate(OffsetDateTime.now(ZoneOffset.UTC).format(DateTimeFormatter.ISO_OFFSET_DATE_TIME));
        subscription.setCreateDate(OffsetDateTime.now(ZoneOffset.UTC).format(DateTimeFormatter.ISO_OFFSET_DATE_TIME));
    }

    @PreUpdate
    public void preUpdate(Subscription subscription) {
        log.debug("SubscriptionEntityListener.preUpdate() called");
        subscription.setLastUpdateDate(OffsetDateTime.now(ZoneOffset.UTC).format(DateTimeFormatter.ISO_OFFSET_DATE_TIME));
    }

    @PreDestroy
    public void preDestroy() {
        log.debug("SubscriptionEntityListener.preDestroy() called");
    }

    @PreRemove
    public void preRemove(Subscription subscription) {
        log.debug("SubscriptionEntityListener.preRemove() called");
    }

    @PostUpdate
    public void postUpdate(Subscription subscription) {
        log.debug("SubscriptionEntityListener.postUpdate() called");
    }

    @PostRemove
    public void postRemove(Subscription subscription) {
        log.debug("SubscriptionEntityListener.postRemove() called");
    }

    @PostPersist
    public void postPersist(Subscription subscription) {
        log.debug("SubscriptionEntityListener.postPersist() called");
    }

}
