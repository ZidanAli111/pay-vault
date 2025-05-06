package com.payvault.backend.entity;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Entity
@Table(name = "user_preference")
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class UserPreference {

    @Id
    @Column(name = "preferenceId")
    private UUID preferenceId;

    @OneToOne
    @JoinColumn(name = "user_id", referencedColumnName = "user_id", nullable = false, updatable = false)
    private UserProfile userProfile;

    @Column
    private String currency;

    @Column
    private boolean darkMode;

    @Column(name = "notification_settings")
    private String notificationSettings;

}
