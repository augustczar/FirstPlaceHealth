package com.firstplacehealth.health.dtos;

import java.time.LocalDate;

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
public class BeneficiaryDto {

	@NotNull
	private String name;
	
	@NotBlank
	private String telephone;

	@NotBlank
	private LocalDate birthDate;
}
