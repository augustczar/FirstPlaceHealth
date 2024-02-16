package com.firstplacehealth.health.clients;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import com.firstplacehealth.health.dtos.BeneficiaryDto;
import com.firstplacehealth.health.dtos.DocumentDto;
import com.firstplacehealth.health.models.BeneficiaryModel;
import com.firstplacehealth.health.models.DocumentModel;
import com.firstplacehealth.health.services.BeneficiaryService;
import com.firstplacehealth.health.services.DocumentService;

import jakarta.transaction.Transactional;
import jakarta.validation.Valid;

@Component
public class DocumentClient {

	@Autowired
	RestTemplate restTemplate;
	
	@Autowired
	DocumentService documentService;
	
	@Autowired
	BeneficiaryService beneficiaryService;
	String REQUEST_URL_DOCUMENT = "http://localhost:8082";
	@Transactional
	public void updateBeneficiaryDocument(@Valid BeneficiaryDto beneficiaryDto,	BeneficiaryModel benficiaryModelUpdate) {
		String url = REQUEST_URL_DOCUMENT + "/firstPlaceHealth/document/beneficiary/update";

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
		
/*
		Optional<BeneficiaryModel> beneficiaryModelOptional = beneficiaryService.findById(beneficiaryId);
		if(!beneficiaryModelOptional.isPresent()) {
	//		return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Beneficiary not found!");
		}
		DocumentModel documentModels = null;
		for (DocumentModel benefDoc : beneficiaryModelOptional.get().getDocuments()) {
			documentModels = new DocumentModel();
			documentModels.setDocumentId(benefDoc.getDocumentId());
			documentModels.setDocumentTypes(benefDoc.getDocumentTypes().CPF);
			documentModels.setDescription(benefDoc.getDescription());
			//documentModels.setBeneficiary(benefDoc.getBeneficiary());
			//documentModels.setInclusionDate( beneficiarySaveList.get().getInclusionDate());
			documentModels.setUpdateDate(LocalDateTime.now(ZoneId.of("UTC")));
//			documentService.update(documentModels.getDocumentId(), documentModels.getDocumentTypes(), documentModels.getDescription(), documentModels.getUpdateDate());
		}
*/
//		restTemplate.put(url, documentModelsLit);//, String.class);
	}
}
