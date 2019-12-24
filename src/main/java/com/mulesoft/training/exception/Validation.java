package com.mulesoft.training.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
public class Validation extends RuntimeException {
	
	public Validation() {
		// TODO Auto-generated constructor stub
	}
	
	public Validation(String message) {
		super(message);
	}
	
	public Validation(String message, Throwable cause) {
		super(message,cause);
	}

}
