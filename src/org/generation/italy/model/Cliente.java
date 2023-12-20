package org.generation.italy.model;

/**
 * Classe entity-bean Cliente che effettua il mapping del record della tabella cliente
 */

public class Cliente {

	/***********************/
	// DEFINIZIONE ATTRIBUTI
	/***********************/
	private String codiceFiscale;
	private String nominativo;

	/***************/
	// COSTRUTTORE/I
	/***************/

	public Cliente(String codiceFiscale, String nominativo) {
		this.codiceFiscale = codiceFiscale;
		this.nominativo = nominativo;
	}

	/********************/
	// GETTERS & SETTERS
	/********************/

	public String getCodiceFiscale() {
		return codiceFiscale;
	}

	public String getNominativo() {
		return nominativo;
	}
	
	/*******************************************/
	// METODO DERIVATO DALLA SUPERCLASSE OBJECT
	/*******************************************/

	@Override
	public String toString() {
		return "Cliente{" + "codiceFiscale=" + codiceFiscale + ", nominativo=" + nominativo + '}';
	}

}
