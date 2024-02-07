package com.firstplacehealth.health.services.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.firstplacehealth.health.models.BeneficiaryModel;
import com.firstplacehealth.health.models.DocumentModel;
import com.firstplacehealth.health.repositories.BeneficiaryRepository;
import com.firstplacehealth.health.repositories.DocumentRepository;
import com.firstplacehealth.health.services.BeneficiaryService;

import jakarta.transaction.Transactional;

@Service
public class BeneficiaryServiceImpl implements BeneficiaryService {

	@Autowired
	BeneficiaryRepository beneficiaryRepository;
	
	@Autowired
	DocumentRepository documentRepository;
	
	@Transactional
	@Override
	public void delete(BeneficiaryModel beneficiaryModel) {
		List<DocumentModel> documentModels = documentRepository
				.findAllDocumentIntoBeneficiary(beneficiaryModel.getBeneficiaryId());
		if (!documentModels.isEmpty()) {
			documentRepository.deleteAll(documentModels);
		}
		beneficiaryRepository.delete(beneficiaryModel);
	}
}
