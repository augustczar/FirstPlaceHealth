package com.firstplacehealth.health.services;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import com.firstplacehealth.health.models.BeneficiaryModel;

public interface BeneficiaryService {

	void delete(BeneficiaryModel beneficiaryModel);

	Optional<BeneficiaryModel> findById(UUID beneficiaryId);

	List<BeneficiaryModel> findAll();

	Optional<BeneficiaryModel> findByName(String name);

	BeneficiaryModel save(BeneficiaryModel beneficiaryModel);
}
