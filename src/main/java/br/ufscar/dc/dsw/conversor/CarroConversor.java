package br.ufscar.dc.dsw.conversor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import br.ufscar.dc.dsw.domain.Carro;
import br.ufscar.dc.dsw.service.spec.ICarroService;

@Component
public class CarroConversor implements Converter<String, Carro> {

	@Autowired
	private ICarroService service;

	@Override
	public Carro convert(String text) {

		if (text.isEmpty()) {
			return null;
		}

		Long id = Long.valueOf(text);
		return service.searchById(id);
	}
}