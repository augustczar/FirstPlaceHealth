package com.firstplacehealth.health.services.impl;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

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

	@Override
	public Optional<BeneficiaryModel> findById(UUID beneficiaryId) {
		return beneficiaryRepository.findById(beneficiaryId);
	}

	@Override
	public List<BeneficiaryModel> findAll() {
		return beneficiaryRepository.findAll();
	}

	@Override
	public Optional<BeneficiaryModel> findByName(String name) {
		return beneficiaryRepository.findByName(name);
	}

	@Override
	public BeneficiaryModel save(BeneficiaryModel beneficiaryModel) {
		return beneficiaryRepository.save(beneficiaryModel);
	}
}
