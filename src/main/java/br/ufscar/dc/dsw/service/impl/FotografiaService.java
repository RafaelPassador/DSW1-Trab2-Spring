package br.ufscar.dc.dsw.service.impl;

import org.springframework.transaction.annotation.Transactional;

import br.ufscar.dc.dsw.dao.IFotografiaDAO;
import br.ufscar.dc.dsw.domain.Fotografia;
import br.ufscar.dc.dsw.service.spec.IFotografiaService;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Transactional(readOnly = false)
public class FotografiaService implements IFotografiaService {
	@Autowired
	IFotografiaDAO dao;

	@Override
	@Transactional(readOnly = true)
	public Fotografia searchById(Long id) {
		return dao.findById(id.longValue());
	}

	@Override
	@Transactional(readOnly = true)
	public List<Fotografia> buscarTodos() {
		return dao.findAll();
	}

	@Override
	public void salvar(Fotografia fotografia) {
		dao.save(fotografia);
	}

	@Override
	public void excluir(Long id) {
		dao.deleteById(id);
	}

}
