package br.com.brunodemetrio.cursomc.services.dtos;

import org.springframework.stereotype.Service;

import br.com.brunodemetrio.cursomc.domain.Cidade;
import br.com.brunodemetrio.cursomc.domain.Cliente;
import br.com.brunodemetrio.cursomc.domain.Endereco;
import br.com.brunodemetrio.cursomc.domain.enums.TipoCliente;
import br.com.brunodemetrio.cursomc.resources.dtos.ClienteDTO;
import br.com.brunodemetrio.cursomc.resources.dtos.ClienteInsertionDTO;

@Service
public class ClienteDTOService {
	
	public Cliente parse(ClienteDTO clienteDTO) {
		return new Cliente(
				clienteDTO.getId(), 
				clienteDTO.getNome(), 
				clienteDTO.getEmail(), 
				null, 
				null);
	}
	
	public Cliente parse(ClienteInsertionDTO clienteDTO) {
		Cidade cidade = new Cidade(clienteDTO.getCidadeId(), null, null);
		
		Cliente cliente = new Cliente(
				null, 
				clienteDTO.getNome(), 
				clienteDTO.getEmail(), 
				clienteDTO.getCpfOuCnpj(), 
				TipoCliente.parseToTipoCliente(clienteDTO.getTipoCliente()));
		
		// TODO [REFACTOR] migrar essa criacao de endereco
		// para um service de EnderecoDTOService
		Endereco endereco = new Endereco(
				null, 
				clienteDTO.getLogradouro(), 
				clienteDTO.getNumero(), 
				clienteDTO.getComplemento(), 
				clienteDTO.getBairro(), 
				clienteDTO.getCep(), 
				cliente, 
				cidade);
		
		cliente.getEnderecos().add(endereco);
		cliente.getTelefones().add(clienteDTO.getTelefone1());
		if (clienteDTO.getTelefone2() != null) {
			cliente.getTelefones().add(clienteDTO.getTelefone2());
		}
		if (clienteDTO.getTelefone3() != null) {
			cliente.getTelefones().add(clienteDTO.getTelefone3());
		}
		
		return cliente;
	}

}
