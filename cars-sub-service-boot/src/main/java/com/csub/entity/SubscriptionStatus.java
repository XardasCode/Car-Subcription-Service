package com.csub.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.Set;

@Entity
@Table(name = "subscription_statuses")
@Data
@NoArgsConstructor
public class SubscriptionStatus {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    
    @Column(name = "name")
    private String name;

    @OneToMany(mappedBy = "status", cascade = {CascadeType.MERGE, CascadeType.PERSIST}, fetch = FetchType.EAGER)
    @EqualsAndHashCode.Exclude
    private Set<Subscription> subscriptions;
}
