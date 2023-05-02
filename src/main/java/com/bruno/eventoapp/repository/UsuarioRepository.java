package com.bruno.eventoapp.repository;

import org.springframework.data.repository.CrudRepository;

import com.bruno.eventoapp.models.Usuario;

public interface UsuarioRepository extends CrudRepository<Usuario, String>{

	Usuario findByLogin(String login);
}
