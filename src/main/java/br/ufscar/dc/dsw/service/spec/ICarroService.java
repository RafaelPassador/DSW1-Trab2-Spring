package br.ufscar.dc.dsw.service.spec;

import java.util.List;

import br.ufscar.dc.dsw.domain.Carro;

public interface ICarroService {

	Carro searchById(Long id);

	List<Carro> searchAll();

	void salvar(Carro carro);

	void excluir(Long id);

	List<String> searchImages(String paths);

}