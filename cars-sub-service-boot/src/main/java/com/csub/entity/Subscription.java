package com.csub.entity;

import com.csub.entity.audit.SubscriptionEntityListener;
import jakarta.persistence.*;
import lombok.*;

@EntityListeners(SubscriptionEntityListener.class)
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "subscriptions")
public class Subscription {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "is_active")
    private boolean isActive;

    @Column(name = "start_date")
    private String startDate;

    @Column(name = "month_price")
    private int monthPrice;

    @Column(name = "total_price")
    private int totalPrice;

    @Column(name = "total_months")
    private int totalMonths;

    @Column(name = "create_date")
    private String createDate;

    @Column(name = "last_update_date")
    private String lastUpdateDate;

    @OneToOne(fetch = FetchType.EAGER, cascade = {CascadeType.MERGE, CascadeType.PERSIST})
    @JoinColumn(name = "user_id", unique = true)
    @EqualsAndHashCode.Exclude
    private User user;

    @OneToOne(fetch = FetchType.EAGER, cascade = {CascadeType.MERGE, CascadeType.PERSIST})
    @JoinColumn(name = "car_id", unique = true)
    @EqualsAndHashCode.Exclude
    private Car car;

    @ManyToOne(fetch = FetchType.EAGER, cascade = {CascadeType.MERGE, CascadeType.PERSIST})
    @JoinColumn(name = "manager_id", nullable = false)
    @EqualsAndHashCode.Exclude
    private Manager manager;

    @ManyToOne(fetch = FetchType.EAGER, cascade = {CascadeType.MERGE, CascadeType.PERSIST})
    @JoinColumn(name = "status_id", nullable = false)
    @EqualsAndHashCode.Exclude
    private SubscriptionStatus status;

}
