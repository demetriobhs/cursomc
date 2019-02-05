package br.com.brunodemetrio.cursomc.repositories;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import br.com.brunodemetrio.cursomc.domain.Categoria;
import br.com.brunodemetrio.cursomc.domain.Produto;

@Repository
public interface ProdutoRepository extends JpaRepository<Produto, Integer> {
	// [REFERENCE] a query comentada abaixo tem o mesmo resultado de um metodo
	// que segue o padrao de nomenclatura de metodos do Spring JPA, que nesse
	// caso eh findDistinctByNomeContainingAndCategoriasIn().
	// https://docs.spring.io/spring-data/jpa/docs/current/reference/html/#jpa.query-methods.query-creation
//	@Query("SELECT DISTINCT p FROM Produto p INNER JOIN p.categorias c WHERE p.nome LIKE %:nome% AND c IN :categorias")
	@Transactional(readOnly = true)
	Page<Produto> findDistinctByNomeContainingAndCategoriasIn(String nome, List<Categoria> categorias, Pageable pageRequest);

}
