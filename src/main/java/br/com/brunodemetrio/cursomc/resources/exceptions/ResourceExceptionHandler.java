package br.com.brunodemetrio.cursomc.resources.exceptions;

import static java.lang.System.currentTimeMillis;
import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.NOT_FOUND;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import br.com.brunodemetrio.cursomc.services.exceptions.DataIntegrityException;
import br.com.brunodemetrio.cursomc.services.exceptions.ObjectNotFoundException;

@ControllerAdvice
public class ResourceExceptionHandler {
	
	@ExceptionHandler(ObjectNotFoundException.class)
	public ResponseEntity<StandardError> objectNotFoundError(ObjectNotFoundException e) {
		StandardError error = new StandardError(NOT_FOUND.value(), e.getMessage(), currentTimeMillis());
		
		return ResponseEntity.status(NOT_FOUND).body(error);
	}
	
	@ExceptionHandler(DataIntegrityException.class)
	public ResponseEntity<StandardError> dataIntegrityError(DataIntegrityException e) {
		StandardError error = new StandardError(BAD_REQUEST.value(), e.getMessage(), currentTimeMillis());
		
		return ResponseEntity.status(BAD_REQUEST).body(error);
	}
	
	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<StandardError> fieldValidationError(MethodArgumentNotValidException e) {
		ValidationError error = new ValidationError(BAD_REQUEST.value(), "Erro de validação", currentTimeMillis());
		
		e.getBindingResult().getFieldErrors()
			.forEach(fieldError -> error.addError(fieldError.getField(), fieldError.getDefaultMessage()));
		
		return ResponseEntity.status(BAD_REQUEST).body(error);
	}

}
