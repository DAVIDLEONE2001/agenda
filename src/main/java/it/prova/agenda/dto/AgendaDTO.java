package it.prova.agenda.dto;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import it.prova.agenda.model.Agenda;

public class AgendaDTO {

	private Long id;
	private String descrizione;
	private LocalDateTime dataOraInizio;
	private LocalDateTime dataOraFine;
	private UtenteDTO utente;

	
	
	public AgendaDTO(Long id, String descrizione, LocalDateTime dataOraInizio, LocalDateTime dataOraFine) {
		super();
		this.id = id;
		this.descrizione = descrizione;
		this.dataOraInizio = dataOraInizio;
		this.dataOraFine = dataOraFine;
	}
	
	

	public AgendaDTO(Long id, String descrizione, LocalDateTime dataOraInizio, LocalDateTime dataOraFine,
			UtenteDTO utente) {
		super();
		this.id = id;
		this.descrizione = descrizione;
		this.dataOraInizio = dataOraInizio;
		this.dataOraFine = dataOraFine;
		this.utente = utente;
	}



	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getDescrizione() {
		return descrizione;
	}

	public void setDescrizione(String descrizione) {
		this.descrizione = descrizione;
	}

	public LocalDateTime getDataOraInizio() {
		return dataOraInizio;
	}

	public void setDataOraInizio(LocalDateTime dataOraInizio) {
		this.dataOraInizio = dataOraInizio;
	}

	public LocalDateTime getDataOraFine() {
		return dataOraFine;
	}

	public void setDataOraFine(LocalDateTime dataOraFine) {
		this.dataOraFine = dataOraFine;
	}

	public UtenteDTO getUtente() {
		return utente;
	}

	public void setUtente(UtenteDTO utente) {
		this.utente = utente;
	}

	public Agenda buildAgendaModel(boolean includeUtente) {
		Agenda result = new Agenda(this.id, this.descrizione, this.dataOraInizio, this.dataOraFine);
		if (includeUtente && this.utente != null)
			result.setUtente(this.utente.buildUtenteModel(true));

		return result;
	}
	
	public static AgendaDTO buildAgendaDTOFromModel(Agenda agendaModel) {
		AgendaDTO result = new AgendaDTO(agendaModel.getId(), agendaModel.getDescrizione(), agendaModel.getDataOraInizio(),
				agendaModel.getDataOraFine());

		if (agendaModel.getUtente()!=null)
			result.setUtente(UtenteDTO.buildUtenteDTOFromModel(agendaModel.getUtente()) );

		return result;
	}
	
	public static List<AgendaDTO> createAgendaDTOListFromModelList(List<Agenda> modelListInput,
			boolean includeUtente) {
		return modelListInput.stream().map(agendaEntity -> {
			AgendaDTO result = AgendaDTO.buildAgendaDTOFromModel(agendaEntity);
			if (includeUtente)
				result.setUtente(UtenteDTO.buildUtenteDTOFromModel(agendaEntity.getUtente()));
			return result;
		}).collect(Collectors.toList());
	}

}
