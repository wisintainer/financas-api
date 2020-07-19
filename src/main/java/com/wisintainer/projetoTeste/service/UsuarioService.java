package com.wisintainer.projetoTeste.service;

import com.wisintainer.projetoTeste.model.entity.Usuario;

public interface UsuarioService {

	Usuario autenticar(String email, String senha);

	Usuario salvarUsuario(Usuario usuario);

	void validarEmail(String email);
}
