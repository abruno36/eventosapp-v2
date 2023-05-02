package com.bruno.eventoapp.repository;

import org.springframework.data.repository.CrudRepository;

import com.bruno.eventoapp.models.Convidado;
import com.bruno.eventoapp.models.Evento;

public interface ConvidadoRepository extends CrudRepository<Convidado, String>{
	Iterable<Convidado> findByEvento(Evento evento);
	Convidado findByRg(String rg);

}
