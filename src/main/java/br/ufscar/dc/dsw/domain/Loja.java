package br.ufscar.dc.dsw.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@SuppressWarnings("serial")
@Entity
@Table(name = "Loja")
public class Loja extends AbstractEntity<Long> {
	// public class Loja extends Usuario {
	// email,senha,cnpj,nome,descricao;
	@NotBlank(message = "{NotBlank.loja.CNPJ}")
	@Size(min = 18, max = 18)
	@Column(nullable = false, length = 18, unique = true)
	private String CNPJ;

	// @NotBlank(message = "{NotBlank.loja.nome}")
	// @Size(max = 150)
	// @Column(nullable = false, length = 150)
	// private String name;

	@NotBlank(message = "{NotBlank.loja.descricao}")
	@Size(max = 512)
	@Column(nullable = false, length = 512)
	private String descricao;

	public String getCNPJ() {
		return CNPJ;
	}

	public String getDescricao() {
		return descricao;
	}

	// public String getName() {
	// return name;
	// }
	public void setCNPJ(String cNPJ) {
		CNPJ = cNPJ;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}
	// public void setName(String name) {
	// this.name = name;
	// }

}
