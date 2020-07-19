package com.wisintainer.projetoTeste.service;

import java.util.Optional;

import org.junit.Before;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.wisintainer.projetoTeste.exception.ErroAutenticacao;
import com.wisintainer.projetoTeste.exception.RegraNegocioException;
import com.wisintainer.projetoTeste.model.entity.Usuario;
import com.wisintainer.projetoTeste.model.repository.UsuarioRepository;
import com.wisintainer.projetoTeste.service.impl.UsuarioServiceImpl;

@ExtendWith(SpringExtension.class)
@ActiveProfiles("test")
public class UsuarioServiceTest {

	UsuarioService service;

	@MockBean
	UsuarioRepository repository;

	@BeforeEach
	public void configurarTestes() {
		service = new UsuarioServiceImpl(repository);
	}

	@Test
	public void deveAutenticarUmUsuarioComSucesso() {

		String email = "email@email.com";
		String senha = "senha";

		Usuario usuario = Usuario.builder().email(email).senha(senha).id(1l).build();
		Mockito.when(repository.findByEmail(email)).thenReturn(Optional.of(usuario));

		// acao
		Usuario result = service.autenticar(email, senha);
 
		// teste
		org.assertj.core.api.Assertions.assertThat(result).isNotNull();

	}

	@Test
	public void deveValidarEmail() {
		UsuarioRepository usuarioRepositoryTest = Mockito.mock(UsuarioRepository.class);
		Assertions.assertDoesNotThrow(() -> {

			// cenario
			Mockito.when(repository.existsByEmail(Mockito.anyString())).thenReturn(false);

			// acao
			service.validarEmail("email@email.com");
		});
	}

	@Test
	public void deveLancarErroAoValidarEmailQuandoExistirEmailCadastrado() {
		Assertions.assertThrows(RegraNegocioException.class, () -> {
			// cenario
			Mockito.when(repository.existsByEmail(Mockito.anyString())).thenReturn(true);

			// acao
			service.validarEmail("email@email.com");
		});
	}

}
