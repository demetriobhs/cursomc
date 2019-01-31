package br.com.brunodemetrio.cursomc.resources.dtos;

import java.io.Serializable;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;

import org.hibernate.validator.constraints.Length;

import br.com.brunodemetrio.cursomc.domain.Cliente;

public class ClienteDTO implements Serializable {
	
	private static final long serialVersionUID = 1L;

	private Integer id;
	
	@NotEmpty(message = "Campo obrigatório")
	@Length(min = 5, max = 120, message = "Tamanho válido entre 5 e 120 caracteres")
	private String nome;
	
	@NotEmpty(message = "Campo obrigatório")
	@Email(message = "Email inválido")
	private String email;
	
	public ClienteDTO() {
	}

	public ClienteDTO(Cliente cliente) {
		this.id = cliente.getId();
		this.nome = cliente.getNome();
		this.email = cliente.getEmail();
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}
	
	public Cliente parse() {
		return new Cliente(id, nome, email, null, null);
	}
	
}
