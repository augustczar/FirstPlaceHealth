package com.firstplacehealth.health.services.impl;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.firstplacehealth.health.enums.DocumentTypes;
import com.firstplacehealth.health.models.BeneficiaryModel;
import com.firstplacehealth.health.models.DocumentModel;
import com.firstplacehealth.health.repositories.DocumentRepository;
import com.firstplacehealth.health.services.DocumentService;

@Service
public class DocumentServiceImpl implements DocumentService {

	@Autowired
	DocumentRepository documentRepository;

	@Override
	public List<DocumentModel> saveAll(Collection<DocumentModel> documentList) {
		return documentRepository.saveAll(documentList);
	}

	@Override
	public DocumentModel save(DocumentModel documentModels) {
		return documentRepository.save(documentModels);
	}

	@Override
	public List<DocumentModel> findAll() {
		return documentRepository.findAll();
	}

	@Override
	public List<DocumentModel> findByBeneficiary(BeneficiaryModel beneficiaryModel) {
		
		return documentRepository.findByBeneficiary(beneficiaryModel);
	}

	@Override
	public void update(UUID documentId, DocumentTypes documentTypes, String description, LocalDateTime updateDate) {
		documentRepository.update(documentId, documentTypes, description, updateDate);
	}

	@Override
	public Optional<DocumentModel> findByBeneficiaryId(UUID beneficiaryId) {
		return documentRepository.findByBeneficiary(beneficiaryId);
	}

	@Override
	public Optional<DocumentModel> findById(UUID documentId) {
		return documentRepository.findById(documentId);
	}
}
