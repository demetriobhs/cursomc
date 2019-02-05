package br.com.brunodemetrio.cursomc.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;

import br.com.brunodemetrio.cursomc.domain.Categoria;
import br.com.brunodemetrio.cursomc.domain.Produto;
import br.com.brunodemetrio.cursomc.repositories.ProdutoRepository;

@Service
public class ProdutoService {
	
	@Autowired
	private ProdutoRepository repository;
	
	// TODO [REFACTOR] Buscar melhor maneira de distribuir services e
	// repositories entre as classes e pesquisar as melhores pratica de
	// encapsulamento pra saber quem pode acessar quem
	@Autowired
	private CategoriaService categoriaService;
	
	
	// TODO [REFACTOR] pesquisar uma maneira melhor pra nao utilizar essa
	// quantidade enorme de parametros em um metodo. Quais alternativas eu
	// tenho?
	public Page<Produto> searchByNomeAndCategorias(String nome, List<Integer> categoriaIds,Integer page, Integer itemsPerPage, String orderBy, String direction) {
		PageRequest pageRequest = PageRequest.of(page, itemsPerPage, Direction.fromString(direction), orderBy);
		List<Categoria> categorias = categoriaService.getAllById(categoriaIds);
		
		return repository.findDistinctByNomeContainingAndCategoriasIn(nome, categorias, pageRequest);
	}

}
