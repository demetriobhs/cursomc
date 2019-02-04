package br.com.brunodemetrio.cursomc.services.dtos;

import org.springframework.stereotype.Service;

import br.com.brunodemetrio.cursomc.domain.Categoria;
import br.com.brunodemetrio.cursomc.resources.dtos.CategoriaDTO;

@Service
public class CategoriaDTOService {
	
	public Categoria parse(CategoriaDTO categoriaDTO) {
		return new Categoria(categoriaDTO.getId(), categoriaDTO.getNome());
	}

}
