package br.ufscar.dc.dsw.dao;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import br.ufscar.dc.dsw.domain.Proposta;
import br.ufscar.dc.dsw.domain.PropostaId;

@SuppressWarnings("unchecked")
public interface IPropostaDAO extends CrudRepository<Proposta, Long> {

	Proposta findById(long id);

	List<Proposta> findAll();

	Proposta save(Proposta proposta);

	void deleteById(Long id);

	// @Query("SELECT u FROM Proposta u WHERE u.loja_id = :id")
	// public List<Proposta> getOfferByStore(@Param("id") Long id);

	// @Query("SELECT u FROM Proposta u WHERE u.usuario_id = :id")
	// public List<Proposta> getOfferByUser(@Param("id") Long id);

}