package com.firstplacehealth.health.controllers;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.BeanUtils;
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

import com.firstplacehealth.health.dtos.BeneficiaryDto;
import com.firstplacehealth.health.dtos.DocumentDto;
import com.firstplacehealth.health.models.BeneficiaryModel;
import com.firstplacehealth.health.services.BeneficiaryService;

import jakarta.validation.Valid;


@RestController
@RequestMapping("/Beneficiary")
@CrossOrigin(origins = "*", maxAge = 3600)
public class BeneficiaryController {

	@Autowired
	BeneficiaryService beneficiaryService;
	
	@PostMapping
	public ResponseEntity<Object> saveBeneficiary(@RequestBody @Valid BeneficiaryDto beneficiaryDto,
													@RequestBody @Valid DocumentDto documentDto){
		var beneficiaryName = beneficiaryService.findByName(beneficiaryDto.getName());
		
		if (beneficiaryName.isPresent()) {
			return ResponseEntity.status(HttpStatus.CONFLICT).body("Error: beneficiaryName, this beneficiary is already registered!");
		}
		
		var beneficiaryModel = new BeneficiaryModel();
		BeanUtils.copyProperties(beneficiaryDto, beneficiaryModel);
		beneficiaryModel.setInclusionDate(LocalDateTime.now(ZoneId.of("UTC")));
		beneficiaryModel.setUpdateDate(LocalDateTime.now(ZoneId.of("UTC")));
		var beneficiarySave =  beneficiaryService.save(beneficiaryModel);
		
		
		
		return null;
		
	}
	
	@DeleteMapping("{beneficiaryId}")
	public ResponseEntity<Object> deleteBeneficiary(@PathVariable(value = "beneficiaryId") UUID beneficiaryId){
		Optional<BeneficiaryModel> beneficiaryModelOptional = beneficiaryService.findById(beneficiaryId);
		if(!beneficiaryModelOptional.isPresent()) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Beneficiary not found!");
		}
		beneficiaryService.delete(beneficiaryModelOptional.get());
		return ResponseEntity.status(HttpStatus.OK).body("Beneficiary deleted successfully!");
	}
	
	@PutMapping("{beneficiaryId}")
	public ResponseEntity<Object> updateBeneficiary(@PathVariable(value = "beneficiaryId") UUID beneficiaryId,
													@RequestBody @Valid BeneficiaryDto beneficiaryDto){
		Optional<BeneficiaryModel> beneficiaryModelOptional = beneficiaryService.findById(beneficiaryId);
		if(!beneficiaryModelOptional.isPresent()) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Beneficiary not found!");
		}
	
		var	beneficiaryModel = beneficiaryModelOptional.get();
		beneficiaryModel.setName(beneficiaryDto.getName());
		beneficiaryModel.setBirthDate(beneficiaryDto.getBirthDate());
		beneficiaryModel.setTelephone(beneficiaryDto.getTelephone());
		//beneficiaryModel.setInclusionDate(null);
		beneficiaryModel.setUpdateDate(LocalDateTime.now(ZoneId.of("UTC")));
		
		return ResponseEntity.status(HttpStatus.OK).body(beneficiaryService.save(beneficiaryModel));
		
	}
	
	@GetMapping
	public ResponseEntity<List<BeneficiaryModel>> getAllBeneficiary() {
		return ResponseEntity.status(HttpStatus.OK).body(beneficiaryService.findAll());
	}
	
	
}
