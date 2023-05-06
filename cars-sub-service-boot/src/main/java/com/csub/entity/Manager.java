package com.csub.entity;

import com.csub.entity.audit.ManagerEntityListener;
import jakarta.persistence.*;
import lombok.*;

import java.util.HashSet;
import java.util.Set;

@EntityListeners(ManagerEntityListener.class)
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "managers")
public class Manager {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private long id;

    @Column(name = "name")
    private String name;

    @Column(name = "surname")
    private String surname;

    @Column(name = "email")
    private String email;

    @Column(name = "password")
    private String password;

    @Column(name = "create_date")
    private String createDate;

    @Column(name = "last_update")
    private String lastUpdateDate;

    @OneToMany(mappedBy = "manager")
    @EqualsAndHashCode.Exclude
    private Set<Subscription> subscriptions = new HashSet<>();
}
