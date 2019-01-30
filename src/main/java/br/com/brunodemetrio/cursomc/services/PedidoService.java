package br.com.brunodemetrio.cursomc.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.brunodemetrio.cursomc.domain.Pedido;
import br.com.brunodemetrio.cursomc.repositories.PedidoRepository;
import br.com.brunodemetrio.cursomc.services.exceptions.ObjectNotFoundException;

@Service
public class PedidoService {
	
	@Autowired
	private PedidoRepository repository;
	
	public Pedido getById(Integer id) {
		Optional<Pedido> categoria = repository.findById(id);
	
		return categoria.orElseThrow(
				() -> new ObjectNotFoundException("Objeto não encontrado! ID: " + id + " Tipo: " + Pedido.class.getName()));
	}

}
