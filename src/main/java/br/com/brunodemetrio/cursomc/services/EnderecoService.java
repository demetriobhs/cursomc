package br.com.brunodemetrio.cursomc.services;

import java.util.Collection;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.brunodemetrio.cursomc.domain.Endereco;
import br.com.brunodemetrio.cursomc.repositories.EnderecoRepository;

@Service
public class EnderecoService {
	
	@Autowired
	private EnderecoRepository repository;
	
	public List<Endereco> create(Collection<Endereco> enderecos) {
		return repository.saveAll(enderecos);
	}

}
