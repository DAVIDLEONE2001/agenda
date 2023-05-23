package it.prova.agenda.service;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;

import it.prova.agenda.model.Agenda;
import it.prova.agenda.model.Utente;
import it.prova.agenda.repository.agenda.AgendaRepository;

public class AgendaServiceImpl implements AgendaService {

	@Autowired
	AgendaRepository repository;
	
	@Override
	public List<Agenda> listAll() {
		return (List<Agenda>) repository.findAll();
	}

	@Override
	public Agenda caricaSingoloElemento(Long id) {
		return repository.findById(id).orElse(null);
	}

	@Override
	@Transactional
	public Agenda aggiorna(Agenda agendaInstance) {
		
		return repository.save(agendaInstance);

	}

	@Override
	@Transactional
	public Agenda inserisciNuovo(Agenda agendaInstance) {

	 	return repository.save(agendaInstance);
		
	}

	@Override
	@Transactional
	public void rimuovi(Long idToRemove) {

		 repository.deleteById(idToRemove);
		
	}

	@Override
	public List<Agenda> agendeDiUtente(Utente utente) {
		return repository.findAllByUtente(utente);
	}

}
