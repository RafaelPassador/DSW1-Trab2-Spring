package br.ufscar.dc.dsw.domain;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@SuppressWarnings("serial")
@Entity
@Table(name = "Carro")
public class Carro extends AbstractEntity<Long>{
    //   loja_id bigint not null, primary key(placa), foreign key(loja_id) references Loja(id) on delete cascade)

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
	@Column(nullable = false, length = 20)
	private Integer quilometragem;

    @NotBlank(message = "{NotBlank.carro.descricao}")
    @Size(max = 512)
    @Column(nullable = false, length = 512)
    private String descricao;

    @NotNull(message = "{NotNull.carro.valor}")
	@Column(nullable = false, columnDefinition = "DECIMAL(12,2) DEFAULT 0.0")
	private BigDecimal valor;

    @Column(nullable = true, length = 64)//pictures path
    private String pictures;

    @NotNull(message = "{NotNull.carro.loja}")
    @ManyToOne
    @JoinColumn(name = "usuario_id")
    private Usuario usuario;

    public Integer getAno() {
        return ano;
    }
    public String getChassi() {
        return chassi;
    }
    public String getDescricao() {
        return descricao;
    }
    public Usuario getUsuario() {
        return usuario;
    }
    public String getModelo() {
        return modelo;
    }
    public String getPlaca() {
        return placa;
    }
    public Integer getQuilometragem() {
        return quilometragem;
    }
    public BigDecimal getValor() {
        return valor;
    }
    public String getPictures() {
        return pictures;
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
    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }
    public void setModelo(String modelo) {
        this.modelo = modelo;
    }
    public void setPlaca(String placa) {
        this.placa = placa;
    }
    public void setQuilometragem(Integer quilometragem) {
        this.quilometragem = quilometragem;
    }
    public void setValor(BigDecimal valor) {
        this.valor = valor;
    }

}
