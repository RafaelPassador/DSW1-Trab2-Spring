package br.ufscar.dc.dsw.service.spec;

import java.util.List;

import br.ufscar.dc.dsw.domain.Proposta;

public interface IPropostaService {

	Proposta buscarPorId(Long id);

	List<Proposta> buscarTodos();

	List<Proposta> buscarTodosUsuario(Long usuario_id);

	void salvar(Proposta proposta);

	void excluir(Long id);

}