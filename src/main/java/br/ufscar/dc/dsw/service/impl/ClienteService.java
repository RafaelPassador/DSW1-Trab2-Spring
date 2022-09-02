package br.ufscar.dc.dsw.service.impl;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.ufscar.dc.dsw.dao.IClienteDAO;
import br.ufscar.dc.dsw.domain.Cliente;
import br.ufscar.dc.dsw.service.spec.IClienteService;

@Service
@Transactional(readOnly = false)
public class ClienteService implements IClienteService {

	@Autowired
	IClienteDAO dao;

	@Transactional(readOnly = true)
	public Cliente buscarPorId(Long id) {
		return dao.findById(id.longValue());
	}

	@Transactional(readOnly = true)
	public List<Cliente> buscarTodos() {
		// TODO Auto-generated method stub
		return dao.findAll();
	}

	@Override
	public void salvar(Cliente cliente) {
		// TODO Auto-generated method stub
		dao.save(cliente);
	}

	@Override
	public void excluir(Long id) {
		// TODO Auto-generated method stub
		dao.deleteById(id);
	}

}
