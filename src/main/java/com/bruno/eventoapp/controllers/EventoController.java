package com.bruno.eventoapp.controllers;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.bruno.eventoapp.models.Convidado;
import com.bruno.eventoapp.models.Evento;
import com.bruno.eventoapp.repository.ConvidadoRepository;
import com.bruno.eventoapp.repository.EventoRepository;
import com.bruno.eventoapp.services.EventoService;

@Controller
public class EventoController {

	@Autowired
	private EventoRepository er;
	
	@Autowired
	private EventoService eventoService;
	
	@Autowired
	private ConvidadoRepository cr;
	
	@RequestMapping(value="/cadastrarEvento", method=RequestMethod.GET)
	public String form(){
		return "evento/formEvento";
	}
	
	@RequestMapping(value="/cadastrarEvento", method=RequestMethod.POST)
	public String form(@Valid Evento evento, BindingResult result, RedirectAttributes attributes){
		if(result.hasErrors()){
			attributes.addFlashAttribute("mensagem", "Verifique os campos!");
			return "redirect:/cadastrarEvento";
		}
		
	
		er.save(evento);
		String nomeEvent = evento.getNome();
		attributes.addFlashAttribute("sucesso", "Evento " + nomeEvent + "  adicionado com sucesso!");
		return "redirect:/cadastrarEvento";
	}
	
	@RequestMapping("/eventos")
	public ModelAndView listaEventos(){
		ModelAndView mv = new ModelAndView("listaEventos");
		Iterable<Evento> eventos = er.findAll();
		mv.addObject("eventos", eventos);
		return mv;
	}
	
	@RequestMapping(value="/{codigo}", method=RequestMethod.GET)
	public ModelAndView detalhesEvento(@PathVariable("codigo") long codigo){
		Evento evento = er.findByCodigo(codigo);
		ModelAndView mv = new ModelAndView("evento/detalhesEvento");
		mv.addObject("evento", evento);
		
		Iterable<Convidado> convidados = cr.findByEvento(evento);
		mv.addObject("convidados", convidados);
		
		return mv;
	}
	
	@RequestMapping("/deletarEvento")
	public String deletarEvento(long codigo){
		Evento evento = er.findByCodigo(codigo);
		er.delete(evento);
		return "redirect:/eventos";
	}
	
	
	@RequestMapping(value="/{codigo}", method=RequestMethod.POST)
	public String detalhesEventoPost(@PathVariable("codigo") long codigo, @Valid Convidado convidado,  BindingResult result, RedirectAttributes attributes){
		if(result.hasErrors()){
			attributes.addFlashAttribute("mensagem", "Verifique os campos!");
			return "redirect:/{codigo}";
		}
		Evento evento = er.findByCodigo(codigo);
		convidado.setEvento(evento);
		cr.save(convidado);
		attributes.addFlashAttribute("mensagem", "Convidado adicionado com sucesso!");
		return "redirect:/{codigo}";
	}
	
	@RequestMapping("/deletarConvidado")
	public String deletarConvidado(String rg){
		Convidado convidado = cr.findByRg(rg);
		cr.delete(convidado);
		
		Evento evento = convidado.getEvento();
		long codigoLong = evento.getCodigo();
		String codigo = "" + codigoLong;
		return "redirect:/" + codigo;
	}
	
	@GetMapping("/eventos")
	public String viewHomePage(Model eventos) {
		return findPaginated(0, "nome", "asc", eventos);
	}
	@GetMapping("/page/{pagenum}")
	public String findPaginated(@PathVariable int pagenum, 
			@RequestParam("sortField") String sortField,
			@RequestParam("sortDir") String sortDir,
			Model model) {
		
		int pageSize = 5;
		
		Page<Evento> eventlist  = eventoService.getEvenByPaginate(pagenum, pageSize, sortField, sortDir);
		
		model.addAttribute("eventlist", eventlist);
		model.addAttribute("currentPage", pagenum);
		model.addAttribute("totalPages", eventlist.getTotalPages());
		model.addAttribute("totalItems", eventlist.getTotalElements());
		model.addAttribute("sortField", sortField);
		model.addAttribute("sortDir", sortDir);
		model.addAttribute("reverseSortDir", sortDir.equals("asc") ? "desc" : "asc");

		return "listaEventos";
	}
}	
