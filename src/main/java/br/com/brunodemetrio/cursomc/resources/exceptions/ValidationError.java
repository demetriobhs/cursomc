package br.com.brunodemetrio.cursomc.resources.exceptions;

import java.util.ArrayList;
import java.util.List;

public class ValidationError extends StandardError {
	
	private static final long serialVersionUID = 1L;
	
	private List<FieldValidationError> errors = new ArrayList<>();

	public ValidationError(Integer status, String msg, Long timeStamp) {
		super(status, msg, timeStamp);
	}
	
	public List<FieldValidationError> getErrors() {
		return errors;
	}
	
	public void addError(String fieldName, String msg) {
		errors.add(new FieldValidationError(fieldName, msg));
	}

}
