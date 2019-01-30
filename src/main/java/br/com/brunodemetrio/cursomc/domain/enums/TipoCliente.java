package br.com.brunodemetrio.cursomc.domain.enums;

public enum TipoCliente {
	
	PESSOA_FISICA(1, "Pessoa Física"),
	PESSOA_JURIDICA(2, "Pessoa Jurídica");

	private Integer cod;
	
	private String descricao;
	
	private TipoCliente(Integer cod, String descricao) {
		this.cod = cod;
		this.descricao = descricao;
	}
	
	public int getCod() {
		return cod;
	}
	
	public String getDescricao() {
		return descricao;
	}
	
	public static TipoCliente parseToTipoCliente(Integer cod) {
		if (cod == null) {
			return null;
		}
		
		for (TipoCliente tipoCliente : TipoCliente.values()) {
			if (tipoCliente.equals(cod)) {
				return tipoCliente;
			}
		}
		
		throw new IllegalArgumentException("Código [" + cod + "] inválido.");
	}
	
}
