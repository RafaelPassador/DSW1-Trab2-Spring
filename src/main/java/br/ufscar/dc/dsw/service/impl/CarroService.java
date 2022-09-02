package br.ufscar.dc.dsw.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.ufscar.dc.dsw.dao.ICarroDAO;
import br.ufscar.dc.dsw.dao.IPropostaDAO;
import br.ufscar.dc.dsw.domain.Carro;
import br.ufscar.dc.dsw.domain.Proposta;
import br.ufscar.dc.dsw.service.spec.ICarroService;

@Service
@Transactional(readOnly = false)
public class CarroService implements ICarroService {

	@Autowired
	ICarroDAO dao;

	@Autowired
	IPropostaDAO pdao;

	public void salvar(Carro carro) {
		dao.save(carro);
	}

	public void excluir(Long id) {
		for (Proposta p : pdao.findAll())
			if (p.getCarro().getId() == id)
				pdao.deleteById(p.getId());
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

	@Override
	public List<String> searchImages(String paths) {

		String[] imagens;
		List<String> listImagens = new ArrayList<>();

		if (paths != null && paths.split("\\|").length > 0) {
			imagens = paths.split("\\|");

			listImagens = Arrays.asList(imagens);
		}

		return listImagens;
	}
}