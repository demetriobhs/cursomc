package br.com.brunodemetrio.cursomc.resources;

import java.util.List;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import br.com.brunodemetrio.cursomc.domain.Cliente;
import br.com.brunodemetrio.cursomc.resources.dtos.ClienteDTO;
import br.com.brunodemetrio.cursomc.services.ClienteService;

@RestController
@RequestMapping("/clientes")
public class ClienteResource {
	
	@Autowired
	private ClienteService service;
	
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
	
	@PutMapping("/{id}")
	public ResponseEntity<Void> update(@Valid @RequestBody ClienteDTO clienteDTO, @PathVariable Integer id) {
		service.update(clienteDTO.parse());
		
		return ResponseEntity.noContent().build();
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<Void> delete(@PathVariable Integer id) {
		service.deleteById(id);
		
		return ResponseEntity.noContent().build();
	}
	
}
