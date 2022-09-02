package br.ufscar.dc.dsw.domain;

import java.io.Serializable;

import groovy.transform.EqualsAndHashCode;

@EqualsAndHashCode
public class PropostaId implements Serializable {
	private Carro carro;
	private Loja loja;
	private Long id;

	public PropostaId(Carro carro, Loja loja, Long id) {
		this.id = id;
		this.carro = carro;
		this.loja = loja;
	}
}
