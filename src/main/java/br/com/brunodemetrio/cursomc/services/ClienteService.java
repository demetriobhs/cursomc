package br.com.brunodemetrio.cursomc.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;

import br.com.brunodemetrio.cursomc.domain.Cliente;
import br.com.brunodemetrio.cursomc.repositories.ClienteRepository;
import br.com.brunodemetrio.cursomc.services.exceptions.DataIntegrityException;
import br.com.brunodemetrio.cursomc.services.exceptions.ObjectNotFoundException;

@Service
public class ClienteService {
	
	@Autowired
	private ClienteRepository repository;
	
	// TODO [REFACTOR] verificar a melhor maneira de disponibilizar
	// classes de service para que as classes nao fiquem super 
	// populadas de atributos services
	@Autowired
	private EnderecoService enderecoService;
	
	public List<Cliente> getAll() {
		return repository.findAll();
	}
	
	public Cliente getById(Integer id) {
		ensureClienteExists(id);
		
		Optional<Cliente> cliente = repository.findById(id);
		
		return cliente.get();
	}
	
	public Page<Cliente> getByPage(Integer page, Integer itemsPerPage, String orderBy, String direction) {
		PageRequest pageRequest = PageRequest.of(page, itemsPerPage, Direction.fromString(direction), orderBy);
		
		return repository.findAll(pageRequest);
	}
	
	public Cliente create(Cliente cliente) {
		cliente = repository.save(cliente);
		
		enderecoService.create(cliente.getEnderecos());
		
		return cliente;
	}
	
	// TODO [REFACTOR] Garantir que esse metodo seja chamado apenas quando for para fazer update
	// parcial do dado. Caso seja para fazer update total, criar um novo metodo que atualize
	// o dado por completo.
	// TODO [REFACTOR] garantir que a categoria tenha um ID. Fazer isso em uma validacao.
	public Cliente update(Cliente cliente) {
		Cliente persistedCliente = getById(cliente.getId());
		
		persistedCliente.setNome(cliente.getNome());
		persistedCliente.setEmail(cliente.getEmail());
		
		cliente = repository.save(persistedCliente);
		
		return cliente;
	}
	
	public void deleteById(Integer id) {
		ensureClienteExists(id);
		
		try {
			repository.deleteById(id);
		} catch (DataIntegrityViolationException e) {
			throw new DataIntegrityException("Não é possível excluir cliente de ID [" + id + "]"
					+ " pois existem entidades relacionadas.");
		}
	}
	
	private void ensureClienteExists(Integer id) {
		if (id == null || !repository.existsById(id)) {
			throw new ObjectNotFoundException("Objeto não encontrado! ID: " + id + " Tipo: " + Cliente.class.getName());
		}
	}

}
