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

import io.swagger.v3.oas.annotations.Hidden;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;


@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
@RequestMapping("/document")
@Tag(name = "Saúde em primeiro lugar")
public class DocumentController {

	@Autowired
	BeneficiaryService beneficiaryService;
	
	@Autowired
	DocumentService documentService;

	@Operation(summary = "Listar todos os documentos de um beneficiário a partir de seu id", method = "GET")
	@ApiResponses(value = {
			@ApiResponse( responseCode = "200", description = "Busca realizada com sucesso! "),
			@ApiResponse( responseCode = "422", description = "Dados da requisição inválidos!"),
			@ApiResponse( responseCode = "400", description = "Parametros inválidos"),
			@ApiResponse( responseCode = "500", description = "Error ao realizar busca Benefíciario!"),
	})
	@GetMapping("/beneficiary/{beneficiaryId}/list")
	public ResponseEntity<Object> getAllDocumentByBeneficiay(@PathVariable(value = "beneficiaryId") UUID beneficiaryId){
		Optional<BeneficiaryModel> beneficiaryModelOptional = beneficiaryService.findById(beneficiaryId);
 		if (!beneficiaryModelOptional.isPresent()) {
 			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Beneficiary Not Found!");
		}
 		
 		List<DocumentModel> documents = documentService.findByBeneficiary(beneficiaryModelOptional.get());
 		
		return ResponseEntity.status(HttpStatus.OK).body(documents);	
	}
	
	@Hidden
	@GetMapping
	public ResponseEntity<List<DocumentModel>> getAllDocument() {
		return ResponseEntity.status(HttpStatus.OK).body(documentService.findAll());
	}
}
