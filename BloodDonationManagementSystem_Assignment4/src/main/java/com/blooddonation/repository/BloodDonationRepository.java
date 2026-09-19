package com.blooddonation.repository;

import com.blooddonation.entity.BloodDonation;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BloodDonationRepository extends JpaRepository<BloodDonation, Long> {
}
