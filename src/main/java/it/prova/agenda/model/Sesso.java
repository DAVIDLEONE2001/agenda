package it.prova.agenda.model;

import org.apache.commons.lang3.StringUtils;

import com.fasterxml.jackson.annotation.JsonCreator;

public enum Sesso {
	MASCHIO("M"), FEMMINA("F");

	private String abbreviazione;

	Sesso(String abbreviazione) {
		this.abbreviazione = abbreviazione;
	}

	public String getAbbreviazione() {
		return abbreviazione;
	}

	@JsonCreator
	public static Sesso getSessoFromCode(String input) {
		if(StringUtils.isBlank(input))
			return null;
		
		for (Sesso sessoItem : Sesso.values()) {
			if (sessoItem.equals(Sesso.valueOf(input))) {
				return sessoItem;
			}
		}
		return null;
	}

}
