package com.firstplacehealth.health.controllers;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.firstplacehealth.health.clients.DocumentClient;
import com.firstplacehealth.health.dtos.BeneficiaryDto;
import com.firstplacehealth.health.dtos.DocumentDto;
import com.firstplacehealth.health.models.BeneficiaryModel;
import com.firstplacehealth.health.models.DocumentModel;
import com.firstplacehealth.health.services.BeneficiaryService;
import com.firstplacehealth.health.services.DocumentService;

import jakarta.transaction.Transactional;
import jakarta.validation.Valid;

@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
@RequestMapping("/beneficiary")
public class BeneficiaryController {

	@Autowired
	BeneficiaryService beneficiaryService;

	@Autowired
	DocumentService documentService;

	@Autowired
	DocumentClient documentClient;

	/*
	 * Cadastrar um beneficiário junto com seus documentos
	 */
	@Transactional
	@PostMapping
	public ResponseEntity<Object> saveBeneficiary(@RequestBody @Valid BeneficiaryDto beneficiaryDto) {
		var beneficiaryName = beneficiaryService.findByName(beneficiaryDto.getName());

		if (beneficiaryName.isPresent()) {
			return ResponseEntity.status(HttpStatus.CONFLICT)
					.body("Error: beneficiaryName, this beneficiary is already registered!");
		}

		var beneficiaryModel = new BeneficiaryModel();
		beneficiaryModel.setName(beneficiaryDto.getName());
		beneficiaryModel.setBirthDate(beneficiaryDto.getBirthDate());
		beneficiaryModel.setTelephone(beneficiaryDto.getTelephone());
		beneficiaryModel.setInclusionDate(LocalDateTime.now(ZoneId.of("UTC")));
		beneficiaryModel.setUpdateDate(LocalDateTime.now(ZoneId.of("UTC")));
		Optional<BeneficiaryModel> beneficiarySave = Optional.of(beneficiaryService.save(beneficiaryModel));

		for (DocumentDto benefDoc : beneficiaryDto.getDocuments()) {
			DocumentModel documentModels = new DocumentModel();
			documentModels.setDocumentTypes(benefDoc.getDocumentTypes());
			documentModels.setDescription(benefDoc.getDescription());

			documentModels.setBeneficiary(beneficiarySave.get());
			documentModels.setInclusionDate(LocalDateTime.now(ZoneId.of("UTC")));
			documentModels.setUpdateDate(LocalDateTime.now(ZoneId.of("UTC")));
			documentService.save(documentModels);
		}

		return ResponseEntity.status(HttpStatus.CREATED).body("Registration completed successfully!");

	}

	/*
	 * Remover um beneficiário
	 */
	@DeleteMapping("/{beneficiaryId}/delete")
	public ResponseEntity<Object> deleteBeneficiary(@PathVariable(value = "beneficiaryId") UUID beneficiaryId) {
		Optional<BeneficiaryModel> beneficiaryModelOptional = beneficiaryService.findById(beneficiaryId);
		if (!beneficiaryModelOptional.isPresent()) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Beneficiary not found!");
		}
		beneficiaryService.delete(beneficiaryModelOptional.get());
		return ResponseEntity.status(HttpStatus.OK).body("Beneficiary deleted successfully!");
	}

	/*
	 * Atualizar os dados cadastrais de um beneficiário
	 */
	@Transactional
	@PutMapping("/{beneficiaryId}/update")
	public ResponseEntity<Object> updateBeneficiary(@PathVariable(value = "beneficiaryId") UUID beneficiaryId,
			@RequestBody @Valid BeneficiaryDto beneficiaryDto) {
		Optional<BeneficiaryModel> beneficiaryModelOptional = beneficiaryService.findById(beneficiaryId);
		if (!beneficiaryModelOptional.isPresent()) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Beneficiary not found!");
		}

		var beneficiaryModel = beneficiaryModelOptional.get();
		beneficiaryModel.setName(beneficiaryDto.getName());
		beneficiaryModel.setBirthDate(beneficiaryDto.getBirthDate());
		beneficiaryModel.setTelephone(beneficiaryDto.getTelephone());
		beneficiaryModel.setUpdateDate(LocalDateTime.now(ZoneId.of("UTC")));

		BeneficiaryModel benficiaryModelUpdate = beneficiaryService.save(beneficiaryModel);
		updateBeneficiaryDocument(beneficiaryDto, benficiaryModelUpdate);
		return ResponseEntity.status(HttpStatus.OK).body(beneficiaryModel);

	}

	/*
	 * Atualizar os dados cadastrais de um beneficiário mais documentos
	 */
	@Transactional
	@PutMapping("/beneficiary/update")
	public ResponseEntity<Void> updateBeneficiaryDocument(@RequestBody @Valid BeneficiaryDto beneficiaryDto,
			BeneficiaryModel benficiaryModelUpdate) {

		DocumentModel documentModels = new DocumentModel();
		List<DocumentModel> listLink = new ArrayList<DocumentModel>();
		for (DocumentModel documentList : benficiaryModelUpdate.getDocuments()) {
			listLink.add(documentList);
		}

		for (DocumentDto benefDoc : beneficiaryDto.getDocuments()) {
			listLink.stream()
					.filter((element) -> element.getDocumentTypes().getCode() == benefDoc.getDocumentTypes().getCode())
					.forEach((element) -> {

						documentModels.setDocumentId(element.getDocumentId());
						documentModels.setDocumentTypes(benefDoc.getDocumentTypes());
						documentModels.setDescription(benefDoc.getDescription());
						documentModels.setBeneficiary(element.getBeneficiary());
						documentModels.setInclusionDate(element.getInclusionDate());
						documentModels.setUpdateDate(LocalDateTime.now(ZoneId.of("UTC")));
						documentService.save(documentModels);
					});
		}
		return ResponseEntity.noContent().build();
	}

	@GetMapping
	public ResponseEntity<List<BeneficiaryModel>> getAllBeneficiary() {
		return ResponseEntity.status(HttpStatus.OK).body(beneficiaryService.findAll());
	}

}
