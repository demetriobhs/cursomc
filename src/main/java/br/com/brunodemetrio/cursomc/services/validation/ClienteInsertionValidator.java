package br.com.brunodemetrio.cursomc.services.validation;

import static br.com.brunodemetrio.cursomc.domain.enums.TipoCliente.PESSOA_FISICA;
import static br.com.brunodemetrio.cursomc.domain.enums.TipoCliente.PESSOA_JURIDICA;
import static br.com.brunodemetrio.cursomc.services.validation.util.PessoaValidationUtil.isValidCNPJ;
import static br.com.brunodemetrio.cursomc.services.validation.util.PessoaValidationUtil.isValidCPF;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.springframework.beans.factory.annotation.Autowired;

import br.com.brunodemetrio.cursomc.repositories.ClienteRepository;
import br.com.brunodemetrio.cursomc.resources.dtos.ClienteInsertionDTO;
import br.com.brunodemetrio.cursomc.resources.exceptions.FieldValidationError;
import br.com.brunodemetrio.cursomc.services.validation.annotation.ClienteInsertion;

public class ClienteInsertionValidator implements ConstraintValidator<ClienteInsertion, ClienteInsertionDTO> {
	
	@Autowired
	private ClienteRepository repository;
	
	@Override
	public void initialize(ClienteInsertion constraintAnnotation) {
	}

	@Override
	public boolean isValid(ClienteInsertionDTO clienteDTO, ConstraintValidatorContext context) {
		List<FieldValidationError> errors = new ArrayList<>();
		
		checkCPFOrCNPJforError(clienteDTO).ifPresent(error -> errors.add(error));
		checkEmailForError(clienteDTO).ifPresent(error -> errors.add(error));
		
		for (FieldValidationError error : errors) {
			context.disableDefaultConstraintViolation();
			context.buildConstraintViolationWithTemplate(error.getMsg())
				.addPropertyNode(error.getFieldName())
				.addConstraintViolation();
		}
		
		return errors.isEmpty();
	}
	
	private Optional<FieldValidationError> checkCPFOrCNPJforError(ClienteInsertionDTO clienteDTO) {
		Integer tipoCliente = clienteDTO.getTipoCliente();
		String cpfOuCnpj = clienteDTO.getCpfOuCnpj();
		
		if (PESSOA_FISICA.getCod().equals(tipoCliente) && !isValidCPF(cpfOuCnpj)) {
			return Optional.of(new FieldValidationError("cpfOuCnpj", "CPF inválido"));
		} else if (PESSOA_JURIDICA.getCod().equals(tipoCliente) && !isValidCNPJ(cpfOuCnpj)) {
			return Optional.of(new FieldValidationError("cpfOuCnpj", "CNPJ inválido"));
		}
		
		return Optional.empty();
	}
	
	private Optional<FieldValidationError> checkEmailForError(ClienteInsertionDTO clienteDTO) {
		if (repository.findByEmail(clienteDTO.getEmail()) != null) {
			return Optional.of(new FieldValidationError("email", "Email já cadastrado"));
		}
		
		return Optional.empty();
	}

}
