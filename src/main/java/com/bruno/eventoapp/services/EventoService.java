package com.bruno.eventoapp.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.bruno.eventoapp.models.Evento;
import com.bruno.eventoapp.repository.EventoRepository;

@Service
public class EventoService {
	
	@Autowired
	private EventoRepository eventoRepository;
	
	public Page<Evento> listAll(int pageNum, String sortField, String sortDir) {
		
		Sort sort = Sort.by("nome");
				
		sort = sortDir.equals("asc") ? sort.ascending() : sort.descending();
		
		Pageable pageable = PageRequest.of(pageNum - 1, 14, sort);
		
		return eventoRepository.findAll(pageable);
	}
	
	public Page<Evento> getEvenByPaginate(int curentPage, int size, String sortField, String sortDirection) {
		
		Sort sort = sortDirection.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(sortField).ascending() :
			Sort.by(sortField).descending();
		
		Pageable pageable = PageRequest.of(curentPage, size, sort);
		return eventoRepository.findAll(pageable);
	}
	

}
