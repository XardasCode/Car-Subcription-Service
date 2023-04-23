package com.csub.entity;

import com.csub.controller.request.UserRequestDTO;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "users")
public class User {
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

    @Column(name = "phone")
    private String phone;

    @Column(name = "is_verified")
    private boolean isVerified;

    @Column(name = "is_blocked")
    private boolean isBlocked;

    @Column(name = "verification_code")
    private String verificationCode;

    @OneToOne(mappedBy = "user", cascade = {CascadeType.MERGE, CascadeType.PERSIST}, fetch = FetchType.EAGER)
    private Subscription subscription;

    public static User mapUserRequestDTOToUser(UserRequestDTO user) {
        return User.builder()
                .name(user.getName())
                .surname(user.getSurname())
                .email(user.getEmail())
                .phone(user.getPhone())
                .password(user.getPassword())
                .isVerified(false)
                .isBlocked(false)
                .build();
    }
}
