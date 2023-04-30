package com.csub.entity;

import com.csub.controller.request.SubscriptionRequestDTO;
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
    @ToString.Exclude
    private User user;

    @OneToOne(fetch = FetchType.EAGER, cascade = {CascadeType.MERGE, CascadeType.PERSIST})
    @JoinColumn(name = "car_id", unique = true)
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    private Car car;

    @ManyToOne(fetch = FetchType.EAGER, cascade = {CascadeType.MERGE, CascadeType.PERSIST})
    @JoinColumn(name = "manager_id")
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    private Manager manager;

    @ManyToOne(fetch = FetchType.EAGER, cascade = {CascadeType.MERGE, CascadeType.PERSIST})
    @JoinColumn(name = "status_id")
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    private SubscriptionStatus status;

    public static Subscription createSubscriptionFromRequest(SubscriptionRequestDTO subscription) {
        return Subscription.builder()
                .isActive(subscription.getIsActive() == null ? null : Boolean.parseBoolean(subscription.getIsActive()))
                .startDate(subscription.getStartDate() == null ? null : subscription.getStartDate())
                .monthPrice(subscription.getMonthPrice() == null ? 0 : Integer.parseInt(subscription.getMonthPrice()))
                .totalMonths(subscription.getTotalMonths() == null ? 0 : Integer.parseInt(subscription.getTotalMonths()))
                .totalPrice(subscription.getTotalPrice() == null ? 0 : Integer.parseInt(subscription.getTotalPrice()))
                .build();
    }

    public static void mergeSubscription(Subscription dbSubscription, Subscription subscriptionEntity){
        subscriptionEntity.setId(dbSubscription.getId());
        subscriptionEntity.setActive(dbSubscription.isActive());
        subscriptionEntity.setCreateDate(dbSubscription.getCreateDate());
        subscriptionEntity.setStartDate((subscriptionEntity.getStartDate() != null && !subscriptionEntity.getStartDate().isBlank())
                ? subscriptionEntity.getStartDate() : dbSubscription.getStartDate());
        subscriptionEntity.setMonthPrice((subscriptionEntity.getMonthPrice() != 0) ? subscriptionEntity.getMonthPrice() : dbSubscription.getMonthPrice());
        subscriptionEntity.setTotalMonths((subscriptionEntity.getTotalMonths() != 0) ? subscriptionEntity.getTotalMonths() : dbSubscription.getTotalMonths());
        subscriptionEntity.setTotalPrice((subscriptionEntity.getTotalPrice() != 0) ? subscriptionEntity.getTotalPrice() : dbSubscription.getTotalPrice());
        subscriptionEntity.setUser(subscriptionEntity.getUser() != null ? subscriptionEntity.getUser() : dbSubscription.getUser());
        subscriptionEntity.setManager(subscriptionEntity.getManager() != null ? subscriptionEntity.getManager() : dbSubscription.getManager());
        subscriptionEntity.setCar(subscriptionEntity.getCar() != null ? subscriptionEntity.getCar() : dbSubscription.getCar());

    }

}
