package it.prova.agenda.web.api;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import it.prova.agenda.dto.AgendaDTO;
import it.prova.agenda.dto.UtenteDTO;
import it.prova.agenda.model.Agenda;
import it.prova.agenda.model.Utente;
import it.prova.agenda.service.AgendaService;
import it.prova.agenda.service.UtenteService;

@RestController
@RequestMapping("/api/agenda")
public class AgendaController {

	@Autowired
	private AgendaService agendaService;
	@Autowired
	private UtenteService utenteService;

	@GetMapping("/utente")
	public List<AgendaDTO> getAll() {
		// senza DTO qui hibernate dava il problema del N + 1 SELECT
		// (probabilmente dovuto alle librerie che serializzano in JSON)
		return AgendaDTO.createAgendaDTOListFromModelList(agendaService.listAll(), false);
	}
	@GetMapping
	public List<AgendaDTO> getAllByUtente() {
		// senza DTO qui hibernate dava il problema del N + 1 SELECT
		// (probabilmente dovuto alle librerie che serializzano in JSON)
		return AgendaDTO.createAgendaDTOListFromModelList(agendaService.agendeDiUtente(utenteService.utenteSession()), false);
	}

	@GetMapping("/{id}")
	public AgendaDTO findById(@PathVariable(value = "id", required = true) long id) {
		Agenda agenda = agendaService.caricaSingoloElemento(id);

		if (agenda == null)
			throw new RuntimeException("Regista not found con id: " + id);

		return AgendaDTO.buildAgendaDTOFromModel(agenda);
	}

	// gli errori di validazione vengono mostrati con 400 Bad Request ma
	// elencandoli grazie al ControllerAdvice
	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public AgendaDTO createNew(@Valid @RequestBody AgendaDTO agendaInput) {
		// se mi viene inviato un id jpa lo interpreta come update ed a me (producer)
		// non sta bene
		if (agendaInput.getId() != null)
			throw new RuntimeException("Non è ammesso fornire un id per la creazione");
		agendaInput.setUtente(UtenteDTO.buildUtenteDTOFromModel(utenteService.utenteSession()));
		Agenda agendaInserita = agendaService.inserisciNuovo(agendaInput.buildAgendaModel(false));
		return AgendaDTO.buildAgendaDTOFromModel(agendaInserita);
	}

	@PutMapping("/{id}")
	public AgendaDTO update(@Valid @RequestBody AgendaDTO agendaInput, @PathVariable(required = true) Long id) {
		Agenda agenda = agendaService.caricaSingoloElemento(id);

		if (agenda == null)
			throw new RuntimeException("Regista not found con id: " + id);

		agendaInput.setId(id);
		Agenda agendaAggiornata = agendaService.aggiorna(agendaInput.buildAgendaModel(true));
		return AgendaDTO.buildAgendaDTOFromModel(agendaAggiornata);
	}

	@DeleteMapping("/{id}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void delete(@PathVariable(required = true) Long id) {
		agendaService.rimuovi(id);
	}

}
