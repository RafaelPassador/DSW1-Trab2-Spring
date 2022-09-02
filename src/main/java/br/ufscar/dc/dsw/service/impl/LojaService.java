package br.ufscar.dc.dsw.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.ufscar.dc.dsw.dao.ILojaDAO;
import br.ufscar.dc.dsw.service.spec.ILojaService;
import br.ufscar.dc.dsw.domain.Loja;

@Service
@Transactional(readOnly = false)
public class LojaService implements ILojaService {
	@Autowired
	ILojaDAO dao;

	@Transactional(readOnly = true)
	public Loja buscarPorId(Long id) {
		return dao.findById(id.longValue());
	}

	@Transactional(readOnly = true)
	public List<Loja> buscarTodos() {
		// TODO Auto-generated method stub
		return dao.findAll();
	}

	@Override
	public void salvar(Loja cliente) {
		// TODO Auto-generated method stub
		dao.save(cliente);
	}

	@Override
	public void excluir(Long id) {
		// TODO Auto-generated method stub
		dao.deleteById(id);
	}

}
