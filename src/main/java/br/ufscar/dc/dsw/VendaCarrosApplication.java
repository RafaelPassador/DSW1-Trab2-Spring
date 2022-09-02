package br.ufscar.dc.dsw;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import br.ufscar.dc.dsw.dao.ICarroDAO;
import br.ufscar.dc.dsw.dao.IUsuarioDAO;
import br.ufscar.dc.dsw.domain.Carro;
import br.ufscar.dc.dsw.domain.Usuario;

@SpringBootApplication
public class VendaCarrosApplication {

	public static void main(String[] args) {
		SpringApplication.run(VendaCarrosApplication.class, args);
	}

	@Bean
	public CommandLineRunner demo(IUsuarioDAO usuarioDAO, BCryptPasswordEncoder encoder, ICarroDAO carroDAO) {
		return (args) -> {

			Usuario u1 = new Usuario();
			u1.setUsername("admin");
			u1.setPassword(encoder.encode("admin"));
			u1.setCPF("012.345.678-90");
			u1.setName("Administrador");
			u1.setRole("ROLE_ADMIN");
			u1.setEnabled(true);
			usuarioDAO.save(u1);

			Usuario u2 = new Usuario();
			u2.setUsername("beltrano");
			u2.setPassword(encoder.encode("123"));
			u2.setCPF("985.849.614-10");
			u2.setName("Beltrano Andrade");
			u2.setRole("ROLE_USER");
			u2.setEnabled(true);
			usuarioDAO.save(u2);

			Usuario u3 = new Usuario();
			u3.setUsername("fulano");
			u3.setPassword(encoder.encode("123"));
			u3.setCPF("367.318.380-04");
			u3.setName("Fulano Silva");
			u3.setRole("ROLE_USER");
			u3.setEnabled(true);
			usuarioDAO.save(u3);

			Usuario u4 = new Usuario();
			u4.setUsername("loja");
			u4.setPassword(encoder.encode("loja"));
			u4.setCPF("11.111.111/1111-11");
			u4.setName("Lojinha");
			u4.setDescricao("Muito louca");
			u4.setRole("ROLE_STORE");
			u4.setEnabled(true);
			usuarioDAO.save(u4);

			Usuario u5 = new Usuario();
			u5.setUsername("USER");
			u5.setPassword(encoder.encode("USER"));
			u5.setCPF("111.111.111-11");
			u5.setName("jORGE");
			u5.setTelefone("1");
			u5.setSexo("Avião");
			u5.setRole("ROLE_USER");
			SimpleDateFormat f = new SimpleDateFormat("yyyy-MM-dd");
			Date d = f.parse("2001-11-23");
			u5.setNascimento(d);
			u5.setEnabled(true);
			usuarioDAO.save(u5);

			Carro c = new Carro();
			c.setAno(2000);
			c.setChassi("brabo");
			c.setDescricao("relampago");
			c.setLoja(u4);
			c.setModelo("Disney");
			c.setPlaca("katchau");
			c.setQuilometragem(BigDecimal.valueOf(10));
			c.setValor(BigDecimal.valueOf(100.20));
			carroDAO.save(c);

		};
	}
}
