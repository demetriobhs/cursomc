package br.com.brunodemetrio.cursomc.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.brunodemetrio.cursomc.domain.Categoria;
import br.com.brunodemetrio.cursomc.repositories.CategoriaRepository;
import br.com.brunodemetrio.cursomc.services.exceptions.ObjectNotFoundException;

@Service
public class CategoriaService {
	
	@Autowired
	private CategoriaRepository repository;
	
	public Categoria getBy(Integer id) {
		Optional<Categoria> categoria = repository.findById(id);
	
		return categoria.orElseThrow(
				() -> new ObjectNotFoundException("Objeto não encontrado! ID: " + id + " Tipo: " + Categoria.class.getName()));
	}
	
	public Categoria create(Categoria categoria) {
		categoria = repository.save(categoria);
		
		return categoria;
	}
	
	public Categoria update(Categoria categoria) {
		getBy(categoria.getId());
		
		categoria = repository.save(categoria);
		
		return categoria;
	}

}
