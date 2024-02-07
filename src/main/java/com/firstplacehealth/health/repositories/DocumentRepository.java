package com.firstplacehealth.health.repositories;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.firstplacehealth.health.models.DocumentModel;

public interface DocumentRepository extends JpaRepository<DocumentModel, UUID> {

	@Query(value = "SELECT FROM tb_document WHERE beneficiary_beneficiary_id = :beneficiaryId", nativeQuery = true)
	List<DocumentModel> findAllDocumentIntoBeneficiary(@Param("beneficiaryId") UUID beneficiaryId);
}
