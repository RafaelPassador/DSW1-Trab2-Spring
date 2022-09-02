package br.ufscar.dc.dsw.dao;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import br.ufscar.dc.dsw.domain.Carro;

@SuppressWarnings("unchecked")
public interface ICarroDAO extends CrudRepository<Carro, Long> {

	Carro findById(long id);

	List<Carro> findAll();

	Carro findByPlaca(String placa);

	Carro save(Carro carro);

	void deleteById(Long id);
}