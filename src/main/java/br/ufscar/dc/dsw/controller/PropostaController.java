package br.ufscar.dc.dsw.controller;

import java.security.Principal;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.io.IOException;

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
import br.ufscar.dc.dsw.domain.Proposta;
import br.ufscar.dc.dsw.service.spec.IUsuarioService;
import br.ufscar.dc.dsw.service.spec.ICarroService;
import br.ufscar.dc.dsw.service.spec.IPropostaService;

import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.view.RedirectView;
import org.springframework.util.StringUtils;

@Controller
@RequestMapping("/propostas")
public class PropostaController {

	@Autowired
	private IPropostaService propService;

	@Autowired
	private ICarroService carroService;

	@Autowired
	private IUsuarioService usuarioService;

	private Proposta gamb, gamb2;

	@GetMapping("/cadastrar")
	public String cadastrar(Carro carro) {
		return "carro/cadastro";
	}

	@GetMapping("/listar")
	public String listar(ModelMap model, Principal principal) {

		Usuario user = usuarioService.buscarPorUsuario(principal.getName());
		int role = 0;
		switch (user.getRole()) {
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
		List<Proposta> myOffers = new ArrayList<>();
		for (Proposta p : propService.buscarTodos())
			if ((role == 0 && p.getUsuario().getId() == user.getId())
					|| (role == 1 && p.getLoja().getId() == user.getId()))
				myOffers.add(p);
		model.addAttribute("propostas", myOffers);

		return "proposta/lista";
	}

	@PostMapping("/salvar")
	public String salvar(@Valid Proposta prop, BindingResult result, RedirectAttributes attr, Principal principal) {

		Usuario user = usuarioService.buscarPorUsuario(principal.getName());

		if (result.hasErrors()) {
			System.out.println("TEM ERRO");
			return "proposta/cadastro";
		}
		// System.out.println("OII");
		// if(prev.getCarro() == null)
		// System.out.println("MANOoOOOOO");
		// System.out.println("OII2");
		prop.setLoja(gamb.getLoja());
		prop.setCarro(gamb.getCarro());
		prop.setUsuario(user);
		prop.setData_proposta(new Date());
		prop.setEstado("ABERTO");
		if (prop.getLoja() == null)
			System.out.println("WHYYY LUCI WHYTY");
		System.out.println("Quase" + prop.getLoja().getName());
		System.out.println("Quase" + prop.getCarro().getPlaca());
		System.out.println("Quase" + prop.getValor());
		System.out.println("Quase" + prop.getCondicoes());
		System.out.println("Quase" + prop.getCarro().getPlaca());
		propService.salvar(prop);
		System.out.println("Salvo");
		attr.addFlashAttribute("sucess", "proposta.create.sucess");
		return "redirect:/propostas/listar";
	}

	@GetMapping("/comprar/{id}")
	public String preComprar(@PathVariable("id") Long id, ModelMap model, Principal principal,
			RedirectAttributes attr) {
		Carro carro = carroService.searchById(id);
		model.addAttribute("carro", carro);
		Proposta nova = new Proposta();
		Usuario store = usuarioService.buscarPorId(carro.getLoja().getId());
		Usuario user = usuarioService.buscarPorUsuario(principal.getName());
		for (Proposta p : propService.buscarTodos()) {
			if (p.getCarro().getPlaca().equals(carro.getPlaca()) && p.getUsuario().getId() == user.getId()
					&& p.getEstado().toLowerCase().equals("aberto")) {
				attr.addFlashAttribute("fail", "proposta.repetida.fail");
				return "redirect:/carros/listar";
				// return "carro/lista";
			}
			if (p.getCarro().getPlaca().equals(carro.getPlaca()) && p.getUsuario().getId() == user.getId()
					&& p.getEstado().toLowerCase().equals("aceita")) {
				attr.addFlashAttribute("fail", "proposta.comprada.fail");
				return "redirect:/carros/listar";
				// return "carro/lista";
			}
		}
		nova.setLoja(store);
		nova.setCarro(carro);
		gamb = nova;
		System.out.println("AQUI DEU" + nova.getLoja().getName() + " " + nova.getCarro().getPlaca());
		model.addAttribute("proposta", nova);
		return "proposta/cadastro";
	}

	@GetMapping("/editar/{id}")
	public String preEditar(@PathVariable("id") Long id, ModelMap model, Principal principal, RedirectAttributes attr) {
		gamb2 = propService.buscarPorId(id);
		for (Proposta pro : propService.buscarTodos()) {
			if (gamb2.getId() == pro.getId() && !pro.getEstado().toLowerCase().equals("aberto")) {
				attr.addFlashAttribute("fail", "proposta.resposta.fail");
				return "redirect:/propostas/listar";
			}
		}
		model.addAttribute("proposta", gamb2);
		return "proposta/resposta";
	}

	@PostMapping("/editar")
	public String editar(@Valid Proposta proposta, BindingResult result, RedirectAttributes attr, Principal principal) {

		int okay = result.getFieldErrorCount("valor") + result.getFieldErrorCount("condicoes");
		if (result.getErrorCount() > okay) {
			for (ObjectError o : result.getAllErrors())
				System.out.println(o.getDefaultMessage());
			return "proposta/resposta";
		}

		gamb2.setContraproposta(proposta.getContraproposta());
		gamb2.setEstado("NÃO ACEITO");
		propService.salvar(gamb2);
		attr.addFlashAttribute("sucess", "proposta.edit.sucess");
		return "redirect:/propostas/listar";
	}

	@GetMapping("/aceitar/{id}")
	public String excluir(@PathVariable("id") Long id, RedirectAttributes attr) {
		Proposta p = propService.buscarPorId(id);
		for (Proposta pro : propService.buscarTodos()) {
			if (p.getId() == pro.getId() && !pro.getEstado().toLowerCase().equals("aberto")) {
				attr.addFlashAttribute("fail", "proposta.resposta.fail");
				return "redirect:/propostas/listar";
			}
		}
		p.setEstado("ACEITA");
		propService.salvar(p);
		attr.addFlashAttribute("sucess", "proposta.aceita.sucess");
		return "redirect:/propostas/listar";
	}

	@ModelAttribute("lojas")
	public List<Usuario> listaLojas() {
		return usuarioService.buscarTodos();
	}

	// other fields and getters, setters are not shown
}