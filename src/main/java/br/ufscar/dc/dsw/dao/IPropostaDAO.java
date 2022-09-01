package br.ufscar.dc.dsw.dao;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import br.ufscar.dc.dsw.domain.Proposta;

@SuppressWarnings("unchecked")
public interface IPropostaDAO extends CrudRepository<Proposta, Long>{

	Proposta findById(long id);

	List<Proposta> findAll();
	
	Proposta save(Proposta carro);

	void deleteById(Long id);

    // @Query("SELECT u FROM Proposta u WHERE u.loja_id = :id")
    // public List<Proposta> getOfferByStore(@Param("id") Long id);	

    // @Query("SELECT u FROM Proposta u WHERE u.usuario_id = :id")
    // public List<Proposta> getOfferByUser(@Param("id") Long id);
    
}