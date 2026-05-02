package com.fixOnGo.backend.exception;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ResourceAlreadyExistException extends RuntimeException {

	String resourceName;
	String fieldName;
	String fieldValue;

	public ResourceAlreadyExistException(String resourceName, String fieldName, String fieldValue) {
		super(String.format("%s Account Is Already Present With  %s  :%s", resourceName, fieldName, fieldValue));
		this.resourceName = resourceName;
		this.fieldName = fieldName;
		this.fieldValue = fieldValue;
	}

}
