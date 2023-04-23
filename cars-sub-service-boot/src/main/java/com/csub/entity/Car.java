package com.csub.entity;

import com.csub.entity.audit.SubscriptionEntityListener;
import jakarta.persistence.*;
import lombok.*;

@EntityListeners(SubscriptionEntityListener.class)
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "cars")
public class Car {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private long id;

    @Column(name = "name")
    private String name;

    @Column(name = "model")
    private String model;

    @Column(name = "brand")
    private String brand;

    @Column(name = "year")
    private int year;

    @Column(name = "color")
    private String color;

    @Column(name = "price")
    private int price;

    @Column(name = "fuel_type")
    private String fuelType;

    @Column(name = "chassis_number")
    private String chassisNumber;

    @Column(name = "reg_number")
    private String regNumber;

    @Column(name = "reg_date")
    private String regDate;

    @Column(name = "mileage")
    private String mileage;

    @Column(name = "last_service_date")
    private String lastServiceDate;

    @Column(name = "status_id")
    private String statusId;

    @Column(name = "create_date")
    private String createDate;

    @Column(name = "last_update_date")
    private String lastUpdateDate;

    @OneToOne(mappedBy = "car", fetch = FetchType.EAGER, cascade = {CascadeType.MERGE, CascadeType.PERSIST})
    @EqualsAndHashCode.Exclude
    private Subscription subscriptions;

    @ManyToOne(fetch = FetchType.EAGER, cascade = {CascadeType.MERGE, CascadeType.PERSIST})
    @JoinColumn(name = "status_id", insertable = false, updatable = false)
    @EqualsAndHashCode.Exclude
    private CarStatus carStatus;
}
