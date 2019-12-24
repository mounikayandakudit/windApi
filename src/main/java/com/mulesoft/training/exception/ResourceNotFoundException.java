package com.mulesoft.training.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.BAD_REQUEST)
public class ResourceNotFoundException extends RuntimeException {
	
	public ResourceNotFoundException() {
		// TODO Auto-generated constructor stub
	}
	
	public ResourceNotFoundException(String message){
		super(message);
	}

}
