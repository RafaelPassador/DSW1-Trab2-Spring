package br.ufscar.dc.dsw.service.spec;

import java.util.List;

import br.ufscar.dc.dsw.domain.Usuario;

public interface IUsuarioService {

	Usuario buscarPorId(Long id);

	List<Usuario> buscarTodos();

	List<Usuario> buscarTodosRoles(String role);

	void salvar(Usuario editora);

	Usuario buscarPorUsuario(String usuario);

	void excluir(Long id);
}
