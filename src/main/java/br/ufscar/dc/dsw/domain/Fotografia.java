package br.ufscar.dc.dsw.domain;

import java.sql.Blob;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "Fotografia")
public class Fotografia extends AbstractEntity<Long> {
	@Column(nullable = false) // picture data
	public Blob data;

	@Column(nullable = false, length = 64)
	public String mimetype;

	@ManyToOne
	@JoinColumn(name = "placa")
	public Carro carro;
}
