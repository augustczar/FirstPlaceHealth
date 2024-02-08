package com.firstplacehealth.health.services;

import java.util.Collection;
import java.util.List;

import com.firstplacehealth.health.models.BeneficiaryModel;
import com.firstplacehealth.health.models.DocumentModel;

public interface DocumentService {

	List<DocumentModel> saveAll(Collection<DocumentModel> documentList);

	DocumentModel save(DocumentModel documentModels);

	List<DocumentModel> findAll();

	DocumentModel findByBeneficiaryId(BeneficiaryModel beneficiaryModel);

}
