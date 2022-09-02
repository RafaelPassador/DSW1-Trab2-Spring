package br.ufscar.dc.dsw.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import br.ufscar.dc.dsw.domain.Fotografia;
import br.ufscar.dc.dsw.service.spec.IFotografiaService;

@Controller
@RequestMapping("/fotos")
public class FotografiaController {
	@Autowired
	private IFotografiaService fotografiaService;

	@GetMapping("/{id}")
	@ResponseBody
	public ResponseEntity<byte[]> foto(@PathVariable("id") Long id) {
		Fotografia fotografia = fotografiaService.searchById(id);
		if (fotografia == null) {
			return ResponseEntity.notFound().build();
		} else {
			return ResponseEntity.ok()
					.contentType(MediaType.parseMediaType(fotografia.mimetype))
					.body(fotografia.data);
		}
	}
}
