package br.ufscar.dc.dsw.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.ufscar.dc.dsw.dao.ICarroDAO;
import br.ufscar.dc.dsw.domain.Carro;
import br.ufscar.dc.dsw.service.spec.ICarroService;

@Service
@Transactional(readOnly = false)
public class CarroService implements ICarroService {

	@Autowired
	ICarroDAO dao;
	
	public void salvar(Carro carro) {
		dao.save(carro);
	}

	public void excluir(Long id) {
		dao.deleteById(id);
	}

	@Transactional(readOnly = true)
	public Carro searchById(Long id) {
		return dao.findById(id.longValue());
	}

	@Transactional(readOnly = true)
	public List<Carro> searchAll() {
		return dao.findAll();
	}
}