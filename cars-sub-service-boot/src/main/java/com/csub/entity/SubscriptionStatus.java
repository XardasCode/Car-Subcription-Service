package com.csub.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "subscription_statuses")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SubscriptionStatus {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    
    @Column(name = "name")
    private String name;

    @OneToMany(mappedBy = "status")
    @EqualsAndHashCode.Exclude
    private Set<Subscription> subscriptions = new HashSet<>();
}
