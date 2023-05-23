package it.prova.agenda.repository.agenda;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import it.prova.agenda.model.Agenda;
import it.prova.agenda.model.Utente;

public interface AgendaRepository extends CrudRepository<Agenda, Long> {

	public List<Agenda> findAllByUtente(Utente utente);
	
}
