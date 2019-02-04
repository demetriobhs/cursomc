package br.com.brunodemetrio.cursomc.resources.dtos;

import java.io.Serializable;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;

import org.hibernate.validator.constraints.Length;

import br.com.brunodemetrio.cursomc.services.validation.annotation.ClienteInsertion;

@ClienteInsertion
public class ClienteInsertionDTO implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@NotEmpty(message = "Campo obrigatório")
	@Length(min = 5, max = 120, message = "Tamanho válido entre 5 e 120 caracteres")
	private String nome;
	
	@NotEmpty(message = "Campo obrigatório")
	@Email(message = "Email inválido")
	private String email;
	
	@NotEmpty(message = "Campo obrigatório")
	private String cpfOuCnpj;
	
	private Integer tipoCliente;
	
	// TODO [REFACTOR] pesquisar a melhor pratica de criacao de DTO
	// para segregar atributos de outro contexto como este de endereco
	@NotEmpty(message = "Campo obrigatório")
	private String logradouro;
	
	@NotEmpty(message = "Campo obrigatório")
	private String numero;
	
	private String complemento;
	
	private String bairro;
	
	@NotEmpty(message = "Campo obrigatório")
	private String cep;
	
	private Integer cidadeId;
	
	// TODO [REFACTOR] pesquisar a melhor pratica de criacao de DTO
	// para segregar atributos de outro contexto como este de telefones
	@NotEmpty(message = "Campo obrigatório")
	private String telefone1;
	
	private String telefone2;
	
	private String telefone3;

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

	public String getCpfOuCnpj() {
		return cpfOuCnpj;
	}

	public void setCpfOuCnpj(String cpfOuCnpj) {
		this.cpfOuCnpj = cpfOuCnpj;
	}

	public Integer getTipoCliente() {
		return tipoCliente;
	}

	public void setTipoCliente(Integer tipoCliente) {
		this.tipoCliente = tipoCliente;
	}

	public String getLogradouro() {
		return logradouro;
	}

	public void setLogradouro(String logradouro) {
		this.logradouro = logradouro;
	}

	public String getNumero() {
		return numero;
	}

	public void setNumero(String numero) {
		this.numero = numero;
	}

	public String getComplemento() {
		return complemento;
	}

	public void setComplemento(String complemento) {
		this.complemento = complemento;
	}

	public String getBairro() {
		return bairro;
	}

	public void setBairro(String bairro) {
		this.bairro = bairro;
	}

	public String getCep() {
		return cep;
	}

	public void setCep(String cep) {
		this.cep = cep;
	}

	public Integer getCidadeId() {
		return cidadeId;
	}

	public void setCidadeId(Integer cidadeId) {
		this.cidadeId = cidadeId;
	}

	public String getTelefone1() {
		return telefone1;
	}

	public void setTelefone1(String telefone1) {
		this.telefone1 = telefone1;
	}

	public String getTelefone2() {
		return telefone2;
	}

	public void setTelefone2(String telefone2) {
		this.telefone2 = telefone2;
	}

	public String getTelefone3() {
		return telefone3;
	}

	public void setTelefone3(String telefone3) {
		this.telefone3 = telefone3;
	}

}
