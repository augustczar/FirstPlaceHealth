package com.firstplacehealth.health.dtos;

import com.firstplacehealth.health.enums.DocumentTypes;

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

	private DocumentTypes documentTypes;

 	private String description;
}
