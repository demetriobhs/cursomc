package br.com.brunodemetrio.cursomc.services.validation;

import static org.springframework.web.servlet.HandlerMapping.URI_TEMPLATE_VARIABLES_ATTRIBUTE;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.springframework.beans.factory.annotation.Autowired;

import br.com.brunodemetrio.cursomc.domain.Cliente;
import br.com.brunodemetrio.cursomc.repositories.ClienteRepository;
import br.com.brunodemetrio.cursomc.resources.dtos.ClienteDTO;
import br.com.brunodemetrio.cursomc.resources.exceptions.FieldValidationError;
import br.com.brunodemetrio.cursomc.services.validation.annotation.ClienteUpdate;

public class ClienteUpdateValidator implements ConstraintValidator<ClienteUpdate, ClienteDTO> {
	
	@Autowired
	private ClienteRepository repository;
	
	@Autowired
	private HttpServletRequest request;
	
	@Override
	public void initialize(ClienteUpdate constraintAnnotation) {
	}

	@Override
	public boolean isValid(ClienteDTO clienteDTO, ConstraintValidatorContext context) {
		List<FieldValidationError> errors = new ArrayList<>();
		
		checkEmailForError(clienteDTO).ifPresent(error -> errors.add(error));
		
		for (FieldValidationError error : errors) {
			context.disableDefaultConstraintViolation();
			context.buildConstraintViolationWithTemplate(error.getMsg())
				.addPropertyNode(error.getFieldName())
				.addConstraintViolation();
		}
		
		return errors.isEmpty();
	}
	
	private Optional<FieldValidationError> checkEmailForError(ClienteDTO clienteDTO) {
		@SuppressWarnings("unchecked")
		Map<String, String> map = (Map<String, String>) request.getAttribute(URI_TEMPLATE_VARIABLES_ATTRIBUTE);
		Integer uriId = Integer.parseInt(map.get("id"));
		
		Cliente persistedCliente = repository.findByEmail(clienteDTO.getEmail());
		if (persistedCliente != null && !persistedCliente.getId().equals(uriId)) {
			return Optional.of(new FieldValidationError("email", "Email já existente"));
		}
		
		return Optional.empty();
	}

}
