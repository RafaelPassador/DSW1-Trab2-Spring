package br.ufscar.dc.dsw.validation;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import br.ufscar.dc.dsw.dao.ICarroDAO;
import br.ufscar.dc.dsw.domain.Carro;

@Component
public class UniquePlateValidator implements ConstraintValidator<UniquePlate, String> {

	@Autowired
	private ICarroDAO dao;

	@Override
	public boolean isValid(String Plate, ConstraintValidatorContext context) {
		if (dao != null) {
			Carro car = dao.findByPlaca(Plate);
			return car == null;
		} else {
			// Durante a execução da classe LivrariaMvcApplication
			// não há injeção de dependência
			return true;
		}

	}
}