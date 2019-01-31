package br.com.brunodemetrio.cursomc.resources;

import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

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

import br.com.brunodemetrio.cursomc.domain.Categoria;
import br.com.brunodemetrio.cursomc.resources.dtos.CategoriaDTO;
import br.com.brunodemetrio.cursomc.services.CategoriaService;

@RestController
@RequestMapping("/categorias")
public class CategoriaResource {
	
	@Autowired
	private CategoriaService service;
	
	@GetMapping
	public ResponseEntity<List<?>> findAll() {
		List<CategoriaDTO> categorias = service.getAllCategorias().stream()
				.map(categoria -> new CategoriaDTO(categoria))
				.collect(Collectors.toList());
		
		return ResponseEntity.ok(categorias);
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<?> find(@PathVariable Integer id) {
		Categoria categoria = service.getCategoriaBy(id);
		
		return ResponseEntity.ok(categoria);
	}
	
	@GetMapping("/page")
	public ResponseEntity<Page<?>> findByPage(
			@RequestParam(name = "page", defaultValue = "0") Integer page,
			@RequestParam(name = "itemsPerPage", defaultValue = "24") Integer itemsPerPage,
			@RequestParam(name = "orderBy", defaultValue = "nome") String orderBy,
			@RequestParam(name = "direction", defaultValue = "ASC") String direction) {
		
		Page<CategoriaDTO> categoriasPage = service.getCategoriasByPage(page, itemsPerPage, orderBy, direction)
				.map(categoria -> new CategoriaDTO(categoria));
		
		return ResponseEntity.ok(categoriasPage);
	}
	
	@PostMapping
	public ResponseEntity<Void> create(@RequestBody Categoria categoria) {
		categoria = service.create(categoria);
		
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest()
				.path("/{id}")
				.buildAndExpand(categoria.getId())
				.toUri();
		
		return ResponseEntity.created(uri).build();
	}
	
	@PutMapping("/{id}")
	public ResponseEntity<Void> update(@RequestBody Categoria categoria, @PathVariable Integer id) {
		service.update(categoria);
		
		return ResponseEntity.noContent().build();
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<Void> delete(@PathVariable Integer id) {
		service.deleteCategoriaBy(id);
		
		return ResponseEntity.noContent().build();
	}
}
