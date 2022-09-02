package br.ufscar.dc.dsw.domain;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import org.springframework.format.annotation.DateTimeFormat;

import br.ufscar.dc.dsw.validation.UniqueCNPJ;

@SuppressWarnings("serial")
@Entity
@Table(name = "Usuario")
public class Usuario extends AbstractEntity<Long> {

	@NotBlank
	@Column(nullable = false, length = 20, unique = true)
	private String username;

	@NotBlank
	@Column(nullable = false, length = 64)
	private String password;

	@NotBlank
	@Column(nullable = false, length = 60)
	private String name;
	@UniqueCNPJ(message = "{Unique.usuario.CNPJ}")
	@NotBlank
	@Size(min = 14, max = 18)
	@Column(nullable = false, length = 18)
	private String CPF;

	@NotBlank
	@Column(nullable = false, length = 10)
	private String role;

	// @NotBlank(message = "{NotBlank.loja.descricao}")
	@Size(max = 512)
	@Column(nullable = true, length = 512)
	private String descricao;

	@Column(nullable = false)
	private boolean enabled;

	// @NotBlank(message = "{NotBlank.cliente.telefone}")
	@Size(max = 15)
	@Column(nullable = true, length = 15)
	private String telefone;

	// @NotBlank(message = "{NotBlank.cliente.sexo}")
	@Size(max = 50)
	@Column(nullable = true, length = 50)
	private String sexo;

	// @Temporal(TemporalType.DATE)
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	@Column(nullable = true)
	private Date nascimento;

	public Date getNascimento() {
		return nascimento;
	}

	public String getSexo() {
		return sexo;
	}

	public String getTelefone() {
		return telefone;
	}

	public void setNascimento(Date nascimento) {
		this.nascimento = nascimento;
	}

	public void setSexo(String sexo) {
		this.sexo = sexo;
	}

	public void setTelefone(String telefone) {
		this.telefone = telefone;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getCPF() {
		return CPF;
	}

	public void setCPF(String cPF) {
		CPF = cPF;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	public boolean isEnabled() {
		return enabled;
	}

	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}

}