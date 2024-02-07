package com.firstplacehealth.health.repositories;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.firstplacehealth.health.models.BeneficiaryModel;

public interface BeneficiaryRepository extends JpaRepository<BeneficiaryModel, UUID> {

	Optional<BeneficiaryModel> findByName(String name);

}
