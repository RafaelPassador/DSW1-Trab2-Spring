package br.ufscar.dc.dsw.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.ufscar.dc.dsw.dao.IPropostaDAO;
import br.ufscar.dc.dsw.domain.Proposta;
import br.ufscar.dc.dsw.service.spec.IPropostaService;

@Service
@Transactional(readOnly = false)
public class PropostaService implements IPropostaService {

	@Autowired
	IPropostaDAO dao;

	public void salvar(Proposta proposta) {
		dao.save(proposta);
	}

	public void excluir(Long id) {
		dao.deleteById(id);
	}

	@Transactional(readOnly = true)
	public Proposta buscarPorId(Long id) {
		return dao.findById(id.longValue());
	}

	@Transactional(readOnly = true)
	public List<Proposta> buscarTodos() {
		return dao.findAll();
	}

	@Transactional(readOnly = true)
	public List<Proposta> buscarTodosUsuario(Long usuario_id) {
		// TODO Auto-generated method stub
		List<Proposta> all = dao.findAll();
		List<Proposta> user = new ArrayList<>();
		for (Proposta i : all)
			if (i.getUsuario().getId() == usuario_id)
				user.add(i);
		return user;
		// return dao.getOfferByUser(usuario_id);
	}

}