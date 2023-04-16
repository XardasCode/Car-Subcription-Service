package com.csub.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
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
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private long id;

    @Column(name = "name")
    @Size(min = 2, message = "Name must be at least 2 characters long")
    private String name;

    @Column(name = "surname")
    @Size(min = 2, message = "Surname must be at least 2 characters long")
    private String surname;

    @Column(name = "email")
    @NotBlank(message = "Email is mandatory")
    private String email;

    @Column(name = "password")
    @Size(min = 5, message = "Password must be at least 5 characters long")
    private String password;

    @Column(name = "phone")
    private String phone;

    @Column(name = "is_verified")
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private boolean isVerified;

    @Column(name = "is_blocked")
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private boolean isBlocked;

    @Column(name = "verification_code")
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private String verificationCode;

    @OneToOne(mappedBy = "user", cascade = {CascadeType.MERGE, CascadeType.PERSIST}, fetch = FetchType.EAGER)
    private Subscription subscription;
}
