package com.firstplacehealth.health.controllers;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.firstplacehealth.health.models.BeneficiaryModel;
import com.firstplacehealth.health.models.DocumentModel;
import com.firstplacehealth.health.services.BeneficiaryService;
import com.firstplacehealth.health.services.DocumentService;


@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
@RequestMapping("/document")
public class DocumentController {

	@Autowired
	BeneficiaryService beneficiaryService;
	
	@Autowired
	DocumentService documentService;
	
	/*
	 * Listar todos os documentos de um beneficiário a partir de seu id
	 */
	@GetMapping("/beneficiary/{beneficiaryId}/list")
	public ResponseEntity<Object> getAllDocumentByBeneficiay(@PathVariable(value = "beneficiaryId") UUID beneficiaryId){
		Optional<BeneficiaryModel> beneficiaryModelOptional = beneficiaryService.findById(beneficiaryId);
 		if (!beneficiaryModelOptional.isPresent()) {
 			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Beneficiary Not Found!");
		}
 		
 		List<DocumentModel> documents = documentService.findByBeneficiary(beneficiaryModelOptional.get());
 		
		return ResponseEntity.status(HttpStatus.OK).body(documents);	
	}
	
	@GetMapping
	public ResponseEntity<List<DocumentModel>> getAllDocument() {
		return ResponseEntity.status(HttpStatus.OK).body(documentService.findAll());
	}
}
