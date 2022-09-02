package br.ufscar.dc.dsw.dao;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import br.ufscar.dc.dsw.domain.Fotografia;

public interface IFotografiaDAO extends CrudRepository<Fotografia, Long> {

	Fotografia findById(long id);

	List<Fotografia> findAll();

	@SuppressWarnings("unchecked")
	Fotografia save(Fotografia fotografia);

	void deleteById(Long id);
}
