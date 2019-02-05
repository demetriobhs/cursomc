package br.com.brunodemetrio.cursomc.resources;

import static br.com.brunodemetrio.cursomc.resources.util.URLUtil.decodeURLParam;
import static br.com.brunodemetrio.cursomc.resources.util.URLUtil.parseIntegerList;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import br.com.brunodemetrio.cursomc.resources.dtos.ProdutoDTO;
import br.com.brunodemetrio.cursomc.services.ProdutoService;

@RestController
@RequestMapping("/produtos")
public class ProdutoResource {
	
	@Autowired
	private ProdutoService service;
	
	@GetMapping("/search")
	public ResponseEntity<Page<?>> search(
			@RequestParam(name = "nome", defaultValue = "") String nome,
			@RequestParam(name = "categoriaIds", defaultValue = "") String categoriaIdsText,
			@RequestParam(name = "page", defaultValue = "0") Integer page,
			@RequestParam(name = "itemsPerPage", defaultValue = "24") Integer itemsPerPage,
			@RequestParam(name = "orderBy", defaultValue = "nome") String orderBy,
			@RequestParam(name = "direction", defaultValue = "ASC") String direction) {

		String decodedNome = decodeURLParam(nome);
		List<Integer> categoriaIds = parseIntegerList(categoriaIdsText, ",");
		
		Page<ProdutoDTO> produtosPage = service.searchByNomeAndCategorias(decodedNome, categoriaIds, page, itemsPerPage, orderBy, direction)
				.map(produto -> new ProdutoDTO(produto));
		
		return ResponseEntity.ok(produtosPage);
	}

}
