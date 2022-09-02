package br.ufscar.dc.dsw.domain;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@SuppressWarnings("serial")
@Entity
@Table(name = "Cliente")
public class Cliente extends AbstractEntity<Long> {
	// public class Cliente extends Usuario {

	// @NotBlank(message = "{NotBlank.cliente.CPF}")
	// @Size(min = 14, max = 14)
	// @Column(nullable = false, unique = true, length = 14)
	// private String CPF;

	// @NotBlank(message = "{NotBlank.cliente.nome}")
	// @Size(max = 50)
	// @Column(nullable = false, length = 50)
	// public String name;

	@NotBlank(message = "{NotBlank.cliente.telefone}")
	@Size(max = 15)
	@Column(nullable = false, length = 15)
	private String telefone;

	@NotBlank(message = "{NotBlank.cliente.sexo}")
	@Size(max = 50)
	@Column(nullable = false, length = 50)
	private String sexo;

	@NotNull(message = "{NotNull.cliente.nascimento}")
	@Temporal(TemporalType.DATE)
	@Column(nullable = false)
	private Date nascimento;

	// @OneToMany(mappedBy = "cliente")
	// private List<Proposta> propostas;

	// public String getCPF() {
	// return CPF;
	// }
	public Date getNascimento() {
		return nascimento;
	}

	// public String getName() {
	// return name;
	// }
	// public List<Proposta> getPropostas() {
	// return propostas;
	// }
	public String getSexo() {
		return sexo;
	}

	public String getTelefone() {
		return telefone;
	}

	// public void setCPF(String cPF) {
	// CPF = cPF;
	// }
	public void setNascimento(Date nascimento) {
		this.nascimento = nascimento;
	}

	// public void setName(String name) {
	// this.name = name;
	// }
	// public void setPropostas(List<Proposta> propostas) {
	// this.propostas = propostas;
	// }
	public void setSexo(String sexo) {
		this.sexo = sexo;
	}

	public void setTelefone(String telefone) {
		this.telefone = telefone;
	}

}
