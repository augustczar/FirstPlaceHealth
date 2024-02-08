package com.firstplacehealth.health.services;

import java.util.Collection;
import java.util.List;

import com.firstplacehealth.health.models.DocumentModel;

public interface DocumentService {

	List<DocumentModel> saveAll(Collection<DocumentModel> documentList);

	DocumentModel save(DocumentModel documentModels);

}
