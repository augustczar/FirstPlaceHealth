package com.firstplacehealth.health.services.impl;

import java.util.Collection;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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

}
