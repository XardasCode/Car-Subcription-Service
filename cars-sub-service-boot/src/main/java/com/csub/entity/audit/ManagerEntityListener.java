package com.csub.entity.audit;

import com.csub.entity.Manager;
import jakarta.annotation.PreDestroy;
import jakarta.persistence.*;
import lombok.extern.slf4j.Slf4j;

import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;

@Slf4j
public class ManagerEntityListener {

    @PrePersist
    public void prePersist(Manager manager) {
        log.debug("ManagerEntityListener.prePersist() called");
        manager.setLastUpdateDate(OffsetDateTime.now(ZoneOffset.UTC).format(DateTimeFormatter.ISO_OFFSET_DATE_TIME));
        manager.setCreateDate(OffsetDateTime.now(ZoneOffset.UTC).format(DateTimeFormatter.ISO_OFFSET_DATE_TIME));
    }

    @PreUpdate
    public void preUpdate(Manager manager) {
        log.debug("ManagerEntityListener.preUpdate() called");
        manager.setLastUpdateDate(OffsetDateTime.now(ZoneOffset.UTC).format(DateTimeFormatter.ISO_OFFSET_DATE_TIME));
    }

    @PreRemove
    public void preRemove(Manager manager) {
        log.debug("ManagerEntityListener.preRemove() called");
    }

    @PreDestroy
    public void preDestroy() {
        log.debug("ManagerEntityListener.preDestroy() called");
    }

    @PostRemove
    public void postRemove(Manager manager) {
        log.debug("ManagerEntityListener.postRemove() called");
    }

    @PostPersist
    public void postPersist(Manager manager) {
        log.debug("ManagerEntityListener.postPersist() called");
    }

    @PostUpdate
    public void postUpdate(Manager manager) {
        log.debug("ManagerEntityListener.postUpdate() called");
    }

}
