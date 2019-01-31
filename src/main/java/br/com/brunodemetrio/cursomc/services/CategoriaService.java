package br.com.brunodemetrio.cursomc.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;

import br.com.brunodemetrio.cursomc.domain.Categoria;
import br.com.brunodemetrio.cursomc.repositories.CategoriaRepository;
import br.com.brunodemetrio.cursomc.services.exceptions.DataIntegrityException;
import br.com.brunodemetrio.cursomc.services.exceptions.ObjectNotFoundException;

@Service
public class CategoriaService {
	
	@Autowired
	private CategoriaRepository repository;
	
	public Categoria getCategoriaBy(Integer id) {
		Optional<Categoria> categoria = repository.findById(id);
	
		return categoria.orElseThrow(
				() -> new ObjectNotFoundException("Objeto não encontrado! ID: " + id + " Tipo: " + Categoria.class.getName()));
	}
	
	public List<Categoria> getAllCategorias() {
		return repository.findAll();
	}
	
	public Page<Categoria> getCategoriasByPage(Integer page, Integer itemsPerPage, String orderBy, String direction) {
		PageRequest pageRequest = PageRequest.of(page, itemsPerPage, Direction.fromString(direction), orderBy);
		
		return repository.findAll(pageRequest);
	}
	
	public Categoria create(Categoria categoria) {
		categoria = repository.save(categoria);
		
		return categoria;
	}
	
	public Categoria update(Categoria categoria) {
		getCategoriaBy(categoria.getId());
		
		categoria = repository.save(categoria);
		
		return categoria;
	}
	
	public void deleteCategoriaBy(Integer id) {
		getCategoriaBy(id);
		
		try {
			repository.deleteById(id);
		} catch (DataIntegrityViolationException e) {
			throw new DataIntegrityException("Não é possível excluir categoria de ID [" + id + "]"
					+ " pois ela possui produto(s) relacionado(s).");
		}
	}
	
}
