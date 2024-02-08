package com.firstplacehealth.health.dtos;

import com.firstplacehealth.health.enums.DocumentTypes;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class DocumentDto {

	@NotNull
	private DocumentTypes documentTypes;

	@NotBlank
 	private String description;
}
