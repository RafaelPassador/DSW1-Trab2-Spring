package br.ufscar.dc.dsw.domain;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import br.ufscar.dc.dsw.validation.UniquePlate;

@SuppressWarnings("serial")
@Entity
@Table(name = "Carro")
public class Carro extends AbstractEntity<Long> {
	// loja_id bigint not null, primary key(placa), foreign key(loja_id) references
	// Loja(id) on delete cascade)

	@UniquePlate(message = "{Unique.car.placa}")
	@NotBlank(message = "{NotBlank.carro.placa}")
	@Size(min = 7, max = 7)
	@Column(nullable = false, unique = true, length = 7)
	private String placa;

	@NotBlank(message = "{NotBlank.carro.modelo}")
	@Size(max = 128)
	@Column(nullable = false, length = 128)
	private String modelo;

	@NotBlank(message = "{NotBlank.carro.chassi}")
	@Size(max = 64)
	@Column(nullable = false, length = 64)
	private String chassi;

	@NotNull(message = "{NotNull.carro.ano}")
	@Column(nullable = false, length = 5)
	private Integer ano;

	@NotNull(message = "{NotNull.carro.km}")
	@Column(nullable = false, columnDefinition = "DECIMAL(8,2) DEFAULT 0.0")
	private BigDecimal quilometragem;

	@NotBlank(message = "{NotBlank.carro.descricao}")
	@Size(max = 512)
	@Column(nullable = false, length = 512)
	private String descricao;

	@NotNull(message = "{NotNull.carro.valor}")
	@Column(nullable = false, columnDefinition = "DECIMAL(8,2) DEFAULT 0.0")
	private BigDecimal valor;

	@Column(nullable = true, length = 1024) // pictures path
	private String pictures;

	// @NotNull(message = "{NotNull.carro.loja}")
	@ManyToOne
	@JoinColumn(name = "loja_id")
	private Usuario loja;

	public Integer getAno() {
		return ano;
	}

	public String getChassi() {
		return chassi;
	}

	public String getDescricao() {
		return descricao;
	}

	public Usuario getLoja() {
		return loja;
	}

	public String getModelo() {
		return modelo;
	}

	public String getPlaca() {
		return placa;
	}

	public BigDecimal getQuilometragem() {
		return quilometragem;
	}

	public BigDecimal getValor() {
		return valor;
	}

	public void setValor(BigDecimal valor) {
		this.valor = valor;
	}

	public String getPictures() {
		return pictures;
	}

	public void setPictures(String pictures) {
		if (this.pictures == null || this.pictures.length() == 0) {
			System.out.println("Tava nulo");
			this.pictures = pictures + "|";
		}
		this.pictures += pictures + "|";
	}

	public void setAno(Integer ano) {
		this.ano = ano;
	}

	public void setChassi(String chassi) {
		this.chassi = chassi;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public void setLoja(Usuario loja) {
		this.loja = loja;
	}

	public void setModelo(String modelo) {
		this.modelo = modelo;
	}

	public void setPlaca(String placa) {
		this.placa = placa;
	}

	public void setQuilometragem(BigDecimal quilometragem) {
		this.quilometragem = quilometragem;
	}

	@Transient
	public List<String> getFotosImagePath() {
		if (pictures == null || getId() == null)
			return null;
		String[] spl = pictures.split("\\|");
		List<String> arr = new ArrayList<>(Arrays.asList(spl));
		for (int i = 0; i < arr.size(); i++)
			arr.set(i, "/image/carros-fotos/" + getId() + "/" + arr.get(i));
		return arr;
	}
}
