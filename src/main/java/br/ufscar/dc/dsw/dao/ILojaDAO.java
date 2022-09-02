package br.ufscar.dc.dsw.dao;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import br.ufscar.dc.dsw.domain.Loja;

@SuppressWarnings("unchecked")
public interface ILojaDAO extends CrudRepository<Loja, Long> {
	Loja findById(long id);

	Loja findByCNPJ(String CNPJ);

	List<Loja> findAll();

	Loja save(Loja loja);

	void deleteById(Long id);
}
