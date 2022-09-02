package br.ufscar.dc.dsw.dao;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import br.ufscar.dc.dsw.domain.Cliente;

@SuppressWarnings("unchecked")
public interface IClienteDAO extends CrudRepository<Cliente, Long> {
	Cliente findById(long id);

	// Cliente findByCNPJ (String CPF);

	List<Cliente> findAll();

	Cliente save(Cliente editora);

	void deleteById(Long id);
}
