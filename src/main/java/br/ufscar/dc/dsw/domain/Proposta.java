package br.ufscar.dc.dsw.domain;

import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.springframework.format.annotation.DateTimeFormat;

@SuppressWarnings("serial")
@Entity
// @Table(name = "Proposta")
@IdClass(PropostaId.class)
public class Proposta extends AbstractEntity<Long> {

	@NotNull(message = "{NotNull.proposta.valor}")
	@Column(nullable = false, columnDefinition = "DECIMAL(12,2) DEFAULT 0.0")
	private BigDecimal valor;

	@NotBlank(message = "{NotBlank.proposta.condicoes}")
	@Size(max = 512)
	@Column(nullable = false, length = 512)
	private String condicoes;

	// @NotBlank(message = "{NotBlank.proposta.condicoes}")
	@Size(max = 11)
	@Column(nullable = false, length = 11)
	private String estado;

	@Size(max = 580)
	@Column(nullable = true, length = 580)
	private String contraproposta;

	@DateTimeFormat(pattern = "yyyy-MM-dd")
	@Column(nullable = false)
	private Date data_proposta;

	// @NotNull(message = "{NotNull.carro.loja}")
	@ManyToOne
	@JoinColumn(name = "loja_id")
	private Usuario loja;

	// @Id
	// @NotNull(message = "{NotNull.carro.loja}")
	@ManyToOne
	@JoinColumn(name = "carro_id")
	private Carro carro;

	// @Id
	// @NotNull(message = "{NotNull.carro.loja}")
	@ManyToOne
	@JoinColumn(name = "usuario_id")
	private Usuario usuario;

	public Carro getCarro() {
		return carro;
	}

	public Usuario getUsuario() {
		return usuario;
	}

	public String getCondicoes() {
		return condicoes;
	}

	public String getContraproposta() {
		return contraproposta;
	}

	public Date getData_proposta() {
		return data_proposta;
	}

	public String getEstado() {
		return estado;
	}

	public Usuario getLoja() {
		return loja;
	}

	public BigDecimal getValor() {
		return valor;
	}

	public void setCarro(Carro carro) {
		this.carro = carro;
	}

	public void setCondicoes(String condicoes) {
		this.condicoes = condicoes;
	}

	public void setContraproposta(String contraproposta) {
		this.contraproposta = contraproposta;
	}

	public void setData_proposta(Date data_proposta) {
		this.data_proposta = data_proposta;
	}

	public void setEstado(String estado) {
		this.estado = estado;
	}

	public void setLoja(Usuario loja) {
		this.loja = loja;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	public void setValor(BigDecimal valor) {
		this.valor = valor;
	}

}
