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

import br.ufscar.dc.dsw.domain.Usuario;
import br.ufscar.dc.dsw.service.spec.IUsuarioService;

@Controller
@RequestMapping("/usuarios")
public class UsuarioController {

	@Autowired
	private IUsuarioService service;

	@Autowired
	private BCryptPasswordEncoder encoder;

	@GetMapping("/cadastrarLoja")
	public String cadastrarLoja(Usuario usuario, ModelMap model) {
		model.addAttribute("type", 0);
		return "usuario/cadastroL";
	}

	@GetMapping("/cadastrarCliente")
	public String cadastrarCliente(Usuario usuario, ModelMap model) {
		model.addAttribute("type", 1);
		return "usuario/cadastroC";
	}

	@GetMapping("/listarCliente")
	public String listarCliente(ModelMap model) {
		model.addAttribute("usuarios", service.buscarTodosRoles("ROLE_USER"));
		return "usuario/listaC";
	}

	@GetMapping("/listarLoja")
	public String listarLoja(ModelMap model) {
		model.addAttribute("usuarios", service.buscarTodosRoles("ROLE_STORE"));
		return "usuario/listaL";
	}

	@PostMapping("/salvar")
	// public String salvar(@Valid Cliente usuario, BindingResult result,
	// RedirectAttributes attr) {
	public String salvar(@Valid Usuario usuario, BindingResult result, RedirectAttributes attr) {

		// System.out.println("HELLOOOOOO");sssss
		boolean red = usuario.getCPF().length() > 14;
		if (result.hasErrors()) {
			return "usuario/cadastro" + (red ? "L" : "C");
		}

		// Usuario usuario = (Usuario) usuario2;

		// System.out.println("password = " + usuario.getPassword());

		usuario.setPassword(encoder.encode(usuario.getPassword()));
		service.salvar(usuario);
		attr.addFlashAttribute("sucess", "usuario.create.sucess");
		return "redirect:/usuarios/listar" + (red ? "Loja" : "Cliente");
	}

	@GetMapping("/editar/{id}")
	public String preEditar(@PathVariable("id") Long id, ModelMap model) {
		Usuario editable = service.buscarPorId(id);
		boolean red = editable.getCPF().length() > 14;
		model.addAttribute("type", (red ? 0 : 1));
		model.addAttribute("usuario", editable);
		return "usuario/cadastro" + (red ? "L" : "C");
	}

	@PostMapping("/editar")
	public String editar(@Valid Usuario usuario, BindingResult result, RedirectAttributes attr) {

		boolean red = usuario.getCPF().length() > 14;
		if (result.getFieldErrorCount() > 1 || result.getFieldError("CPF") == null) {
			return "usuario/cadastro" + (red ? "L" : "C");
		}

		System.out.println(usuario.getPassword());

		service.salvar(usuario);
		attr.addFlashAttribute("sucess", (red ? "loja" : "cliente") + ".edit.sucess");
		return "redirect:/usuarios/listar" + (red ? "Loja" : "Cliente");
	}

	@GetMapping("/excluir/{id}")
	public String excluir(@PathVariable("id") Long id, ModelMap model) {
		Usuario editable = service.buscarPorId(id);
		boolean red = editable.getCPF().length() > 14;
		service.excluir(id);
		model.addAttribute("sucess", (red ? "loja" : "cliente") + ".delete.sucess");
		return red ? listarLoja(model) : listarCliente(model);
	}
}
