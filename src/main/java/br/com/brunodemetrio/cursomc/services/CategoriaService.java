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
	
	public List<Categoria> getAll() {
		return repository.findAll();
	}
	
	public Categoria getById(Integer id) {
		ensureCategoriaExists(id);
		
		Optional<Categoria> categoria = repository.findById(id);
	
		return categoria.get();
	}
	
	public Page<Categoria> getByPage(Integer page, Integer itemsPerPage, String orderBy, String direction) {
		PageRequest pageRequest = PageRequest.of(page, itemsPerPage, Direction.fromString(direction), orderBy);
		
		return repository.findAll(pageRequest);
	}
	
	public Categoria create(Categoria categoria) {
		categoria = repository.save(categoria);
		
		return categoria;
	}
	
	// TODO [REFACTOR] Garantir que esse metodo seja chamado apenas quando for para fazer update
	// parcial do dado. Caso seja para fazer update total, criar um novo metodo que atualize
	// o dado por completo.
	// TODO [REFACTOR] garantir que a categoria tenha um ID. Fazer isso em uma validacao.
	public Categoria update(Categoria categoria) {
		Categoria persistedCategoria = getById(categoria.getId());
		
		persistedCategoria.setNome(categoria.getNome());
		
		categoria = repository.save(persistedCategoria);
		
		return categoria;
	}
	
	public void deleteById(Integer id) {
		ensureCategoriaExists(id);
		
		try {
			repository.deleteById(id);
		} catch (DataIntegrityViolationException e) {
			throw new DataIntegrityException("Não é possível excluir categoria de ID [" + id + "]"
					+ " pois ela possui produto(s) relacionado(s).");
		}
	}
	// TODO [REFACTOR] Esse metodo se repete em todas as classes, com a variacao apenas
	// no repository e na classe principal. Pensar em uma maneira de refatorar pra que
	// exista somente um metodo desse. Um exemplo do que ocorreu, eh que o ID estada vindo
	// nulo e eu tive que acrescentar essa verificacao id == null, dai tive que fazer isso 
	// em todas as classes. Trampo! So vou colocar esse REFACTOR aqui nesse metodo, mas
	// tenho que refatorar de todas as classes que usam um metodo igual
	private void ensureCategoriaExists(Integer id) {
		if (id == null || !repository.existsById(id)) {
			throw new ObjectNotFoundException("Objeto não encontrado! ID: " + id + " Tipo: " + Categoria.class.getName());
		}
	}
	
}
