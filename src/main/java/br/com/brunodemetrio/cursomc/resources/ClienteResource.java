package br.com.brunodemetrio.cursomc.resources;

import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import br.com.brunodemetrio.cursomc.domain.Cliente;
import br.com.brunodemetrio.cursomc.resources.dtos.ClienteDTO;
import br.com.brunodemetrio.cursomc.resources.dtos.ClienteInsertionDTO;
import br.com.brunodemetrio.cursomc.services.ClienteService;
import br.com.brunodemetrio.cursomc.services.dtos.ClienteDTOService;

@RestController
@RequestMapping("/clientes")
public class ClienteResource {
	
	// TODO [REFACTOR] verificar a melhor maneira de disponibilizar
	// classes de service para o uso em resources para que as classes
	// de resource nao fiquem super populadas de atributos services
	@Autowired
	private ClienteService service;
	
	@Autowired
	private ClienteDTOService dtoService;
	
	@GetMapping
	public ResponseEntity<List<?>> findAll() {
		List<ClienteDTO> clientes = service.getAll().stream()
			.map(cliente -> new ClienteDTO(cliente))
			.collect(Collectors.toList());
		
		return ResponseEntity.ok(clientes);
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<?> find(@PathVariable Integer id) {
		Cliente cliente = service.getById(id);
		
		return ResponseEntity.ok(cliente);
	}
	
	@GetMapping("/page")
	public ResponseEntity<Page<?>> findPage(
			@RequestParam(name = "page", defaultValue = "0") Integer page,
			@RequestParam(name = "itemsPerPage", defaultValue = "24") Integer itemsPerPage,
			@RequestParam(name = "orderBy", defaultValue = "nome") String orderBy,
			@RequestParam(name = "direction", defaultValue = "ASC") String direction) {
		
		Page<ClienteDTO> clientesPage = service.getByPage(page, itemsPerPage, orderBy, direction)
			.map(cliente -> new ClienteDTO(cliente));
		
		return ResponseEntity.ok(clientesPage);
	}
	
	@PostMapping
	public ResponseEntity<?> create(@Valid @RequestBody ClienteInsertionDTO clienteDTO) {
		Cliente cliente = dtoService.parse(clienteDTO);
		cliente = service.create(cliente);
		
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest()
				.path("/{id}")
				.buildAndExpand(cliente.getId())
				.toUri();
		
		return ResponseEntity.created(uri).build();
	}
	
	@PutMapping("/{id}")
	public ResponseEntity<Void> update(@Valid @RequestBody ClienteDTO clienteDTO, @PathVariable Integer id) {
		clienteDTO.setId(id);
		Cliente cliente = dtoService.parse(clienteDTO);
		
		service.update(cliente);
		
		return ResponseEntity.noContent().build();
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<Void> delete(@PathVariable Integer id) {
		service.deleteById(id);
		
		return ResponseEntity.noContent().build();
	}
	
}
