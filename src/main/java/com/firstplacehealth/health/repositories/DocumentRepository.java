package com.firstplacehealth.health.repositories;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.firstplacehealth.health.enums.DocumentTypes;
import com.firstplacehealth.health.models.BeneficiaryModel;
import com.firstplacehealth.health.models.DocumentModel;

import jakarta.transaction.Transactional;

public interface DocumentRepository extends JpaRepository<DocumentModel, UUID> {

	List<DocumentModel> findByBeneficiary(BeneficiaryModel beneficiaryModel);

	@Modifying(clearAutomatically = true)
	@Transactional
	@Query(value = "UPDATE tb_document set document_types = :documentTypes, description = :description, update_date = :updateDate WHERE document_id = :documentId", nativeQuery = true)
	void update(@Param("documentId") UUID documentId, @Param("documentTypes") DocumentTypes documentTypes, @Param("description") String description, @Param("updateDate") LocalDateTime updateDate);

	@Transactional
	@Query(value = "SELECT FROM tb_document WHERE beneficiary_beneficiary_id = :beneficiaryId", nativeQuery = true)
	Optional<DocumentModel> findByBeneficiary(@Param("beneficiaryId") UUID beneficiaryId);
}
