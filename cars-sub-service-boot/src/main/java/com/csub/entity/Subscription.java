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

    @Column(name = "passport_number")
    private String passportNumber;

    @Column(name = "ipn_number")
    private String ipnNumber;

    @Column(name = "soc_media_link")
    private String socMediaLink;

    @Column(name = "create_date")
    private String createDate;

    @Column(name = "last_update_date")
    private String lastUpdateDate;

    @OneToOne
    @JoinColumn(name = "user_id", unique = true)
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    private User user;

    @OneToOne
    @JoinColumn(name = "car_id", unique = true)
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    private Car car;

    @ManyToOne
    @JoinColumn(name = "manager_id")
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    private Manager manager;

    @ManyToOne
    @JoinColumn(name = "status_id")
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    private SubscriptionStatus status;

    public static Subscription createSubscriptionFromRequest(SubscriptionRequestDTO subscription) {
        return Subscription.builder()
                .monthPrice(subscription.getMonthPrice() == null ? 0 : Integer.parseInt(subscription.getMonthPrice()))
                .totalMonths(subscription.getTotalMonths() == null ? 0 : Integer.parseInt(subscription.getTotalMonths()))
                .ipnNumber(subscription.getIpnNumber() == null ? "" : subscription.getIpnNumber())
                .passportNumber(subscription.getPassportNumber() == null ? "" : subscription.getPassportNumber())
                .socMediaLink(subscription.getSocMediaLink() == null ? "" : subscription.getSocMediaLink())
                .isActive(false)
                .build();
    }
}
