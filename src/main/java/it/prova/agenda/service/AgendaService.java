package it.prova.agenda.service;

import java.util.List;

import it.prova.agenda.model.Agenda;
import it.prova.agenda.model.Utente;

public interface AgendaService {

	public List<Agenda> listAll();

	public Agenda caricaSingoloElemento(Long id);

	public Agenda aggiorna(Agenda agendaInstance);

	public Agenda inserisciNuovo(Agenda agendaInstance);

	public void rimuovi(Long idToRemove);
	
	public List<Agenda> agendeDiUtente (Utente utente);
	
}
