package com.bruno.eventoapp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

import com.bruno.eventoapp.models.Evento;

public interface EventoRepository extends CrudRepository<Evento, String>, JpaRepository<Evento, String>{
	Evento findByCodigo(long codigo);
		
}
