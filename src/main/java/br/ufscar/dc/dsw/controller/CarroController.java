package br.ufscar.dc.dsw.controller;

import java.security.Principal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import br.ufscar.dc.dsw.domain.Usuario;
import br.ufscar.dc.dsw.domain.Carro;
import br.ufscar.dc.dsw.domain.Fotografia;
import br.ufscar.dc.dsw.service.spec.IUsuarioService;
import br.ufscar.dc.dsw.service.spec.ICarroService;
import br.ufscar.dc.dsw.service.spec.IFotografiaService;

@Controller
@RequestMapping("/carros")
public class CarroController {

	@Autowired
	private ICarroService carroService;

	@Autowired
	private IFotografiaService fotografiaService;

	@Autowired
	private IUsuarioService usuarioService;

	String gamb;

	@GetMapping("/cadastrar")
	public String cadastrar(Carro carro) {
		return "carro/cadastro";
	}

	@GetMapping("/listar")
	public String listar(ModelMap model, Principal principal) {
		List<Carro> Cars = carroService.searchAll();
		System.out.println("Entrou");
		if (principal != null) {

			Usuario user = usuarioService.buscarPorUsuario(principal.getName());
			int role = -1;
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
			if (role == 1) {
				List<Carro> storeCars = new ArrayList<>();
				for (Carro c : carroService.searchAll())
					if (c.getLoja().getId() == user.getId())
						storeCars.add(c);

				Cars = storeCars;
			}
			model.addAttribute("visitingRole", role);
		}
		model.addAttribute("carros", Cars);
		System.out.println("ATE AQUI VEIO2 = " + Cars.size());

		return "carro/lista";
	}

	@PostMapping("/salvar")
	public String salvar(@Valid Carro carro, BindingResult result, RedirectAttributes attr, Principal principal,
			@RequestParam("image") MultipartFile file) throws IOException {

		System.out.println("Entrouu salva");
		Usuario loja = usuarioService.buscarPorUsuario(principal.getName());

		// System.out.println("AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA" +
		// principal.getName());

		if (result.hasErrors()) {
			for (ObjectError o : result.getAllErrors())
				System.out.println(o.getDefaultMessage());
			return "carro/cadastro";
		}

		carro.setLoja(loja);

		carroService.salvar(carro);
		attr.addFlashAttribute("sucess", "carro.create.sucess");

		return saveCarPhoto(carro, file);
	}

	@GetMapping("/editar/{id}")
	public String preEditar(@PathVariable("id") Long id, ModelMap model) {
		Carro car = carroService.searchById(id);
		if (car.getPictures() != null)
			gamb = car.getPictures();
		else
			gamb = "";
		if (!gamb.isEmpty())
			gamb = gamb.substring(0, gamb.length() - 1);
		model.addAttribute("carro", car);
		return "carro/cadastro";
	}

	@PostMapping("/editar")
	public String editar(@Valid Carro carro, BindingResult result, RedirectAttributes attr, Principal principal,
			@RequestParam("image") MultipartFile file) throws IOException {
		if (!gamb.isEmpty())
			carro.setPictures(gamb);
		boolean overFoto = false;
		if (carro.getFotosImagePath() != null)
			overFoto = carro.getFotosImagePath().size() >= 10;
		if (overFoto) {
			attr.addFlashAttribute("fail", "carro.fotos.fail");
			return "redirect:/carros/listar";
		}

		System.out.println("Editandoo" + carro.getPictures() + " bool = " + overFoto);
		Usuario loja = usuarioService.buscarPorUsuario(principal.getName());

		if (result.getFieldErrorCount() > 1 || result.getFieldError("placa") == null) {
			return "carro/cadastro";
		}
		carro.setLoja(loja);

		carroService.salvar(carro);
		System.out.println("TCHAU MUNDO2" + carro.getPictures());
		attr.addFlashAttribute("sucess", "carro.edit.sucess");
		// return "redirect:/carros/listar";
		return saveCarPhoto(carro, file);
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

	// @PostMapping("/salvar")
	public String saveCarPhoto(Carro carro, @RequestParam("image") MultipartFile multipartFile) throws IOException {
		Fotografia fotografia = new Fotografia();
		fotografia.carro = carro;
		fotografia.data = multipartFile.getBytes();
		fotografia.mimetype = multipartFile.getContentType();
		fotografiaService.salvar(fotografia);
		return "redirect:/carros/listar";
	}

	@ModelAttribute("pictures")
	public Map<Long, List<String>> listaFotos() {
		Map<Long, List<String>> mapPhoto = new HashMap<>();

		for (Fotografia fotografia : fotografiaService.buscarTodos()) {
			List<String> imagens = mapPhoto.get(fotografia.carro.getId());
			if (imagens == null) {
				imagens = new ArrayList<>();
				mapPhoto.put(fotografia.carro.getId(), imagens);
			}
			imagens.add("/fotos/" + fotografia.getId());
		}

		return mapPhoto;
	}

	// other fields and getters, setters are not shown
}