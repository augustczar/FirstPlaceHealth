package com.firstplacehealth.health.models;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.UUID;

import org.hibernate.annotations.DynamicUpdate;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonProperty.Access;
import com.firstplacehealth.health.enums.DocumentTypes;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@JsonInclude(JsonInclude.Include.NON_NULL)
@Entity
@DynamicUpdate
@Table(name = "TB_DOCUMENT")
public class DocumentModel implements Serializable {
	
	private static final long serialVersionUID = -8991110778838714315L;

	@Id
	@EqualsAndHashCode.Include
	@GeneratedValue(strategy = GenerationType.AUTO)
	private UUID documentId;
	
	private Integer documentTypes;
	
	@Column(nullable = false, length = 250)
 	private String description;
 	
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy HH:mm:ss")
	private LocalDateTime inclusionDate;
	
	@Column(nullable = false)
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy HH:mm:ss")
	private LocalDateTime updateDate;
	
	//@ToString.Exclude
	@JsonProperty(access = Access.WRITE_ONLY)
	@ManyToOne(cascade = CascadeType.REMOVE, fetch = FetchType.LAZY, optional = false)
	@JoinColumn
	private BeneficiaryModel beneficiary;
	
	public DocumentTypes getDocumentTypes() {
		return DocumentTypes.valueOf(documentTypes);
	}
	
	public void setDocumentTypes(DocumentTypes documentTypes) {
		if(documentTypes != null) {
			this.documentTypes = documentTypes.getCode();
		}
	}
}
