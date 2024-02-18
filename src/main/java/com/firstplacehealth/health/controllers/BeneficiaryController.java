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

import io.swagger.v3.oas.annotations.Hidden;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;

@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
@RequestMapping("/beneficiary")
@Tag(name = "Saúde em primeiro lugar")
public class BeneficiaryController {

	@Autowired
	BeneficiaryService beneficiaryService;

	@Autowired
	DocumentService documentService;

	@Autowired
	DocumentClient documentClient;

	
	@Operation(summary = "Cadastrar um benefíciario junto com seus documentos", method = "POST")
	@ApiResponses(value = {
			@ApiResponse( responseCode = "200", description = "Benefíciario registrado com sucesso! "),
			@ApiResponse( responseCode = "422", description = "Dados da requisição inválidos!"),
			@ApiResponse( responseCode = "400", description = "Parametros inválidos"),
			@ApiResponse( responseCode = "500", description = "Error ao registrar Benefíciario!"),
	})
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

	@Operation(summary = "Remover um beneficiário", method = "DELETE")
	@ApiResponses(value = {
			@ApiResponse( responseCode = "200", description = "Benefíciario removido com sucesso! "),
			@ApiResponse( responseCode = "422", description = "Dados da requisição inválidos!"),
			@ApiResponse( responseCode = "400", description = "Parametros inválidos"),
			@ApiResponse( responseCode = "500", description = "Error ao remover Benefíciario!"),
	})
	@DeleteMapping("/{beneficiaryId}/delete")
	public ResponseEntity<Object> deleteBeneficiary(@PathVariable(value = "beneficiaryId") UUID beneficiaryId) {
		Optional<BeneficiaryModel> beneficiaryModelOptional = beneficiaryService.findById(beneficiaryId);
		if (!beneficiaryModelOptional.isPresent()) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Beneficiary not found!");
		}
		beneficiaryService.delete(beneficiaryModelOptional.get());
		return ResponseEntity.status(HttpStatus.OK).body("Beneficiary deleted successfully!");
	}

	@Operation(summary = "Atualizar os dados cadastrais de um beneficiário junto com os documentos", method = "PUT")
	@ApiResponses(value = {
			@ApiResponse( responseCode = "200", description = "Benefíciario atualizado com sucesso! "),
			@ApiResponse( responseCode = "422", description = "Dados da requisição inválidos!"),
			@ApiResponse( responseCode = "400", description = "Parametros inválidos"),
			@ApiResponse( responseCode = "500", description = "Error ao atualizar Benefíciario!"),
	})
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
		var updateDate = (!beneficiaryModel.getName().equals(beneficiaryDto.getName()) ||
		!beneficiaryModel.getBirthDate().equals(beneficiaryDto.getBirthDate()) ||
		!beneficiaryModel.getTelephone().equals(beneficiaryDto.getTelephone())) 
				? LocalDateTime.now(ZoneId.of("UTC")) :beneficiaryModel.getUpdateDate(); 
		beneficiaryModel.setUpdateDate(updateDate);

		BeneficiaryModel benficiaryModelUpdate = beneficiaryService.save(beneficiaryModel);
		updateBeneficiaryDocument(beneficiaryDto, benficiaryModelUpdate);
		return ResponseEntity.status(HttpStatus.OK).body(beneficiaryModel);

	}

	@Hidden
	@Operation(summary = "Atualizar os dados cadastrais de um beneficiário mais documentos", method = "PUT")
	@ApiResponses(value = {
			@ApiResponse( responseCode = "200", description = "Documento Benefíciario atualizado com sucesso! "),
			@ApiResponse( responseCode = "422", description = "Dados da requisição inválidos!"),
			@ApiResponse( responseCode = "400", description = "Parametros inválidos"),
			@ApiResponse( responseCode = "500", description = "Error ao atualizar documento Benefíciario!"),
	})
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
						var updateDate = (!element.getDocumentTypes().name().equals(benefDoc.getDocumentTypes().name())
								|| (!element.getDescription().equals(benefDoc.getDescription()))? LocalDateTime.now(ZoneId.of("UTC")) : element.getUpdateDate()); 
						documentModels.setUpdateDate(updateDate);
						documentService.save(documentModels);
					});
		}
		return ResponseEntity.noContent().build();
	}

	@Operation(summary = "Listar todos os beneficiários cadastrados", method = "GET")
	@ApiResponses(value = {
			@ApiResponse( responseCode = "200", description = "Busca realizada com sucesso! "),
			@ApiResponse( responseCode = "422", description = "Dados da requisição inválidos!"),
			@ApiResponse( responseCode = "400", description = "Parametros inválidos"),
			@ApiResponse( responseCode = "500", description = "Error ao realizar busca dos Benefíciarios!"),
	})
	@GetMapping
	public ResponseEntity<List<BeneficiaryModel>> getAllBeneficiary() {
		return ResponseEntity.status(HttpStatus.OK).body(beneficiaryService.findAll());
	}

}
