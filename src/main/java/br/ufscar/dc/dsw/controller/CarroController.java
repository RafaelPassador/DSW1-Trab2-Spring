package br.ufscar.dc.dsw.controller;

import java.security.Principal;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import br.ufscar.dc.dsw.domain.Usuario;
import br.ufscar.dc.dsw.domain.Carro;
import br.ufscar.dc.dsw.service.spec.IUsuarioService;
import br.ufscar.dc.dsw.service.impl.UsuarioService;
import br.ufscar.dc.dsw.service.spec.ICarroService;

@Controller
@RequestMapping("/carros")
public class CarroController {

	@Autowired
	private ICarroService carroService;

	@Autowired
	private IUsuarioService usuarioService;

	@GetMapping("/cadastrar")
	public String cadastrar(Carro carro) {
		return "carro/cadastro";
	}

	@GetMapping("/listar")
	public String listar(ModelMap model, Principal principal) {
		
		Usuario user = usuarioService.buscarPorUsuario(principal.getName());

		int role = 0;
		switch(user.getRole()){
			case "ROLE_USER":
				role = 0;
			break;
			case "ROLE_STORE":
				role = 1;
			break;
			case "ROLE_ADMIN":
				role = 2;
			break;
		}

		model.addAttribute("visitingRole", role);
		model.addAttribute("carros", carroService.searchAll());
		System.out.println("ATE AQUI VEIO2");
		
		return "carro/lista";
	}


	@PostMapping("/salvar")
	public String salvar(@Valid Carro carro , BindingResult result, RedirectAttributes attr, Principal principal) {

		System.out.println("Entrouu");
		Usuario loja =  usuarioService.buscarPorUsuario(principal.getName());

		// System.out.println("AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA" + principal.getName());

		
		if (result.hasErrors()) {
			return "carro/cadastro";
		}

		carro.setLoja(loja);
		

		carroService.salvar(carro);
		attr.addFlashAttribute("sucess", "carro.create.sucess");
		return "redirect:/carros/listar";
	}

	@GetMapping("/editar/{id}")
	public String preEditar(@PathVariable("id") Long id, ModelMap model) {
		model.addAttribute("carro", carroService.searchById(id));
		return "carro/cadastro";
	}

	@PostMapping("/editar")
	public String editar(@Valid Carro carro, BindingResult result, RedirectAttributes attr, Principal principal) {

		Usuario loja =  usuarioService.buscarPorUsuario(principal.getName());
		
		if (result.getFieldErrorCount() > 1 || result.getFieldError("placa") == null) {
			return "carro/cadastro";
		}
		
		carro.setLoja(loja);
		
		carroService.salvar(carro);
		System.out.println("TCHAU MUNDO2");
		attr.addFlashAttribute("sucess", "carro.edit.sucess");
		return "redirect:/carros/listar";
	}

	@GetMapping("/excluir/{id}")
	public String excluir(@PathVariable("id") Long id, RedirectAttributes attr) {
		carroService.excluir(id);
		attr.addFlashAttribute("sucess", "carro.delete.sucess");
		return "redirect:/carros/listar";
	}

	@ModelAttribute("lojas")
	public List<Usuario> listaLojas() {
		return usuarioService.buscarTodos();
	}
}