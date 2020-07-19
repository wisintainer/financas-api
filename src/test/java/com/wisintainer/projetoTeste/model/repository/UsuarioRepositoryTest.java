package com.wisintainer.projetoTeste.model.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.wisintainer.projetoTeste.model.entity.Usuario;

import java.util.Optional;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

@DataJpaTest // encerrou cada teste, limpa a base de dados em memoria
@AutoConfigureTestDatabase(replace = Replace.NONE) // cria instancia de banco em memoria exatamento com as configuracoes
													// do meu properties para fazer um teste mais real
@ExtendWith(SpringExtension.class)
@ActiveProfiles("test")
public class UsuarioRepositoryTest {

	@Autowired
	UsuarioRepository repository;

	@Autowired
	TestEntityManager entityManager;

	@Test
	public void deveVerificarAExistenciaDeUmEmail() {
		// cenario
		Usuario usuario = Usuario.builder().nome("usuario").email("usuario@email.com").build();
		entityManager.persist(usuario);

		// acao/execucao
		boolean result = repository.existsByEmail("usuario@email.com");

		// verificacao
		Assertions.assertThat(result).isTrue();
	}

	@Test
	public void deveRetornarFalsoQuandoNaoHouverUsuarioCadastrado() {
		// cenario

		// acao
		boolean result = repository.existsByEmail("usuario@email.com");

		// verificacao
		Assertions.assertThat(result).isFalse();
	}

	@Test
	public void devePersistirUmUsuarioNaBaseDeDados() {
		// cenario
		Usuario usuario = criarUsuario();

		// acao
		Usuario usuarioSalvao = repository.save(usuario);

		// teste
		Assertions.assertThat(usuarioSalvao.getId()).isNotNull();
	}

	@Test
	public void deveBuscarUmUsuarioPorEmail() {
		// cenario
		Usuario usuario = criarUsuario();
		entityManager.persist(usuario);

		// verificacao
		Optional<Usuario> result = repository.findByEmail("usuario@email.com");
		Assertions.assertThat(result.isPresent()).isTrue();
	}

	@Test
	public void deveRetornarVazioaoBuscarUmUsuarioPorEmailQueNaoExisteNaBase() {
		// verificacao
		Optional<Usuario> result = repository.findByEmail("usuario@email.com");
		Assertions.assertThat(result.isPresent()).isFalse();
	}

	public static Usuario criarUsuario() {
		Usuario usuario = Usuario.builder().nome("usuario").email("usuario@email.com").senha("senha").build();
		return usuario;
	}
}
