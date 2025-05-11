package com.payvault.backend.repository;

import com.payvault.backend.entity.Address;
import com.payvault.backend.entity.UserProfile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface AddressRepository extends JpaRepository<Address, UUID> {

    Address findByUserProfile(UserProfile userProfile);
}
