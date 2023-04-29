package com.csub.entity.audit;

import com.csub.entity.User;
import jakarta.annotation.PreDestroy;
import jakarta.persistence.*;
import lombok.extern.slf4j.Slf4j;

import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;

@Slf4j
public class UserEntityListener {

    @PrePersist
    public void prePersist(User user) {
        log.debug("UserEntityListener.prePersist() called");
        user.setLastUpdateDate(OffsetDateTime.now(ZoneOffset.UTC).format(DateTimeFormatter.ISO_OFFSET_DATE_TIME));
        user.setCreateDate(OffsetDateTime.now(ZoneOffset.UTC).format(DateTimeFormatter.ISO_OFFSET_DATE_TIME));
    }

    @PreUpdate
    public void preUpdate(User user) {
        log.debug("UserEntityListener.preUpdate() called");
        user.setLastUpdateDate(OffsetDateTime.now(ZoneOffset.UTC).format(DateTimeFormatter.ISO_OFFSET_DATE_TIME));
    }

    @PreDestroy
    public void preDestroy() {
        log.debug("UserEntityListener.preDestroy() called");
    }

    @PreRemove
    public void preRemove(User user) {
        log.debug("UserEntityListener.preRemove() called");
    }

    @PostUpdate
    public void postUpdate(User user) {
        log.debug("UserEntityListener.postUpdate() called");
    }

    @PostRemove
    public void postRemove(User user) {
        log.debug("UserEntityListener.postRemove() called");
    }

    @PostPersist
    public void postPersist(User user) {
        log.debug("UserEntityListener.postPersist() called");
    }
}
