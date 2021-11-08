package com.mrfti.helpdesk.resources.exceptions;

import java.util.ArrayList;
import java.util.List;

//classe para trabalhar na manipulação da exceção
public class ValidationError extends StandardError {
	private static final long serialVersionUID = 1L;

	private List<FieldMessage> errors = new ArrayList<>();

	public ValidationError() {
		super();
	}

	public ValidationError(Long timeStamp, Integer status, String error, String message, String path) {
		super(timeStamp, status, error, message, path);
	}

	public List<FieldMessage> getErrors() {
		return errors;
	}

	public void addError(String fieldName, String message) { // customizado ao criar os acessores
		this.errors.add(new FieldMessage(fieldName, message));
	}

}
