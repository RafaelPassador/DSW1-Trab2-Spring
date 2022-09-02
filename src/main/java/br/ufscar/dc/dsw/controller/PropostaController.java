package br.ufscar.dc.dsw.controller;

import java.security.Principal;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import java.io.IOException;

import javax.servlet.http.HttpSession;
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
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import br.ufscar.dc.dsw.domain.Usuario;
import br.ufscar.dc.dsw.domain.Carro;
import br.ufscar.dc.dsw.domain.Proposta;
import br.ufscar.dc.dsw.service.spec.IUsuarioService;
import br.ufscar.dc.dsw.service.impl.UsuarioService;
import br.ufscar.dc.dsw.service.spec.ICarroService;
import br.ufscar.dc.dsw.service.spec.IPropostaService;
import br.ufscar.dc.Utils.FileUploadUtil;

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

	private Proposta gamb;

	@GetMapping("/cadastrar")
	public String cadastrar(Carro carro) {
		return "carro/cadastro";
	}

	@GetMapping("/listar")
	public String listar(ModelMap model, Principal principal) {

		System.out.println("ATE ENTROU");
		model.addAttribute("propostas", propService.buscarTodos());
		System.out.println("ATE AQUI VEIO22");

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
	public String preEditar(@PathVariable("id") Long id, ModelMap model) {
		Carro carro = carroService.searchById(id);
		model.addAttribute("carro", carro);
		Proposta nova = new Proposta();
		Usuario store = usuarioService.buscarPorId(carro.getLoja().getId());
		nova.setLoja(store);
		nova.setCarro(carro);
		gamb = nova;
		System.out.println("AQUI DEU" + nova.getLoja().getName() + " " + nova.getCarro().getPlaca());
		model.addAttribute("proposta", nova);
		return "proposta/cadastro";
	}

	@PostMapping("/editar")
	public String editar(@Valid Carro carro, BindingResult result, RedirectAttributes attr, Principal principal) {

		Usuario loja = usuarioService.buscarPorUsuario(principal.getName());

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

	// @Autowired
	// private UserRepository repo;

	@PostMapping("/carros/salvar")
	public RedirectView saveCarPhoto(Carro carro, @RequestParam("image") MultipartFile multipartFile)
			throws IOException {

		String fileName = StringUtils.cleanPath(multipartFile.getOriginalFilename());
		carro.setPictures(fileName);

		// Carro savedCarro = repo.save(carro);

		String uploadDir = "carros-fotos/" + carro.getId();

		FileUploadUtil.saveFile(uploadDir, fileName, multipartFile);

		return new RedirectView("/carros", true);
	}

	// other fields and getters, setters are not shown
}