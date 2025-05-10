package com.payvault.backend.repository;

import com.payvault.backend.entity.UserPreference;
import com.payvault.backend.entity.UserProfile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface PreferenceRepository extends JpaRepository<UserPreference, UUID> {

    boolean existsByUserProfile(UserProfile userProfile);
}
