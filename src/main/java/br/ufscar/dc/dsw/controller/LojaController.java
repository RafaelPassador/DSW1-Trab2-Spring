package br.ufscar.dc.dsw.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import br.ufscar.dc.dsw.domain.Loja;
import br.ufscar.dc.dsw.service.spec.ILojaService;

@Controller
@RequestMapping("/lojas")
public class LojaController {

	@Autowired
	private ILojaService service;

	@Autowired
	private BCryptPasswordEncoder encoder;

	@GetMapping("/cadastrar")
	public String cadastrar(Loja usuario) {
		return "loja/cadastro";
	}

	@GetMapping("/listar")
	public String listar(ModelMap model) {
		model.addAttribute("lojas", service.buscarTodos());
		return "loja/lista";
	}

	@PostMapping("/salvar")
	public String salvar(@Valid Loja loja, BindingResult result, RedirectAttributes attr) {

		if (result.hasErrors()) {
			return "loja/cadastro";
		}

		// System.out.println("password = " + cliente.getPassword());

		// cliente.setPassword(encoder.encode(cliente.getPassword()));
		service.salvar(loja);

		attr.addFlashAttribute("sucess", "loja.create.sucess");

		return "redirect:/lojas/listar";
	}

	@GetMapping("/editar/{id}")
	public String preEditar(@PathVariable("id") Long id, ModelMap model) {
		model.addAttribute("loja", service.buscarPorId(id));
		return "loja/cadastro";
	}

	@PostMapping("/editar")
	public String editar(@Valid Loja loja, BindingResult result, RedirectAttributes attr) {

		if (result.hasErrors()) {
			return "loja/cadastro";
		}

		// System.out.println(loja.getPassword());

		service.salvar(loja);
		attr.addFlashAttribute("sucess", "loja.edit.sucess");
		return "redirect:/lojas/listar";
	}

	@GetMapping("/excluir/{id}")
	public String excluir(@PathVariable("id") Long id, ModelMap model) {
		service.excluir(id);
		model.addAttribute("sucess", "loja.delete.sucess");
		return listar(model);
	}
}
