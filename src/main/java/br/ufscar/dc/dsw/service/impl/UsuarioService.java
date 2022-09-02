package br.ufscar.dc.dsw.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.ufscar.dc.dsw.dao.ICarroDAO;
import br.ufscar.dc.dsw.dao.IPropostaDAO;
import br.ufscar.dc.dsw.dao.IUsuarioDAO;
import br.ufscar.dc.dsw.domain.Carro;
import br.ufscar.dc.dsw.domain.Proposta;
import br.ufscar.dc.dsw.domain.Usuario;
import br.ufscar.dc.dsw.service.spec.IUsuarioService;

@Service
@Transactional(readOnly = false)
public class UsuarioService implements IUsuarioService {

	@Autowired
	IUsuarioDAO dao;

	@Autowired
	IPropostaDAO pdao;

	@Autowired
	ICarroDAO cdao;

	public void salvar(Usuario usuario) {
		dao.save(usuario);
	}

	public void excluir(Long id) {
		for(Carro c : cdao.findAll())
			if(c.getLoja().getId() == id)
				cdao.deleteById(c.getId());
		for(Proposta p : pdao.findAll())
			if(p.getLoja().getId() == id || p.getUsuario().getId() == id)
				pdao.deleteById(p.getId());
		
		dao.deleteById(id);
	}

	@Transactional(readOnly = true)
	public Usuario buscarPorId(Long id) {
		return dao.findById(id.longValue());
	}

	@Override
	public Usuario buscarPorUsuario(String usuario) {
		// TODO Auto-generated method stub
		return dao.getUserByUsername(usuario);
	}

	@Transactional(readOnly = true)
	public List<Usuario> buscarTodos() {
		return dao.findAll();
	}

	@Transactional(readOnly = true)
	public List<Usuario> buscarTodosRoles(String role) {
		// List<Usuario> all = dao.findAll();
		// List<Usuario> byRole = new ArrayList<>();
		// for(Usuario i : all)
		// if(i.getRole().equals(role))
		// byRole.add(i);

		// return byRole;
		return dao.getUserByRole(role);
	}
}
