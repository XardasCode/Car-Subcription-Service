package com.csub.entity.audit;

import com.csub.entity.Car;
import jakarta.annotation.PreDestroy;
import jakarta.persistence.*;
import lombok.extern.slf4j.Slf4j;

import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;

@Slf4j
public class CarEntityListener {

    @PrePersist
    public void prePersist(Car car) {
        log.debug("CarAuditListener.prePersist() called");
        car.setLastUpdateDate(OffsetDateTime.now(ZoneOffset.UTC).format(DateTimeFormatter.ISO_OFFSET_DATE_TIME));
        car.setCreateDate(OffsetDateTime.now(ZoneOffset.UTC).format(DateTimeFormatter.ISO_OFFSET_DATE_TIME));
    }

    @PreUpdate
    public void preUpdate(Car car) {
        log.debug("CarAuditListener.preUpdate() called");
        car.setLastUpdateDate(OffsetDateTime.now(ZoneOffset.UTC).format(DateTimeFormatter.ISO_OFFSET_DATE_TIME));
    }

    @PreDestroy
    public void preDestroy() {
        log.debug("CarAuditListener.preDestroy() called");
    }

    @PreRemove
    public void preRemove(Car car) {
        log.debug("CarAuditListener.preRemove() called");
    }

    @PostPersist
    public void postPersist(Car car) {
        log.debug("CarAuditListener.postPersist() called");
    }

    @PostUpdate
    public void postUpdate(Car car) {
        log.debug("CarAuditListener.postUpdate() called");
    }

    @PostRemove
    public void postRemove(Car car) {
        log.debug("CarAuditListener.postRemove() called");
    }
}
