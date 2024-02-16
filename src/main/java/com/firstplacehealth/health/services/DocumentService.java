package com.firstplacehealth.health.services;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import com.firstplacehealth.health.enums.DocumentTypes;
import com.firstplacehealth.health.models.BeneficiaryModel;
import com.firstplacehealth.health.models.DocumentModel;

public interface DocumentService {

	DocumentModel save(DocumentModel documentModelsLit);

	List<DocumentModel> findAll();

	List<DocumentModel> findByBeneficiary(BeneficiaryModel beneficiaryModel);

	void update(UUID documentId, DocumentTypes documentTypes, String description, LocalDateTime updateDate);

	Optional<DocumentModel> findByBeneficiaryId(UUID beneficiaryId);

	Optional<DocumentModel> findById(UUID documentId);

	List<DocumentModel> saveAll(Collection<DocumentModel> documentList);

}
