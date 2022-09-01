package br.ufscar.dc.dsw.domain;

import java.io.Serializable;

import groovy.transform.EqualsAndHashCode;

@EqualsAndHashCode
public class PropostaId extends AbstractEntity<Long> implements Serializable{
    private Carro carro;
    private Loja loja;

    public PropostaId(Carro carro, Loja loja, Long id){
        setId(id);
        this.carro = carro;
        this.loja = loja;
    }   
}
