package org.generation.italy.model;

/**
 * Classe entity-bean Prodotto che effettua il mapping del record della tabella prodotto
 */

public class Prodotto {
	
	/***********************/
	// DEFINIZIONE ATTRIBUTI
	/***********************/
	private String codiceProdotto, descrizione;
	private int quantita_disponibile;
	private float prezzo;

	/***************/
	// COSTRUTTORE/I
	/***************/
	public Prodotto(String codiceProdotto, String descrizione, int quantita_disponibile, float prezzo) {
		this.codiceProdotto = codiceProdotto;
		this.descrizione = descrizione;
		this.quantita_disponibile=quantita_disponibile;
		this.prezzo=prezzo;
	}

	/********************/
	// GETTERS & SETTERS
	/********************/
	public int getQuantita_disponibile() {
		return quantita_disponibile;
	}

	public void setQuantita_disponibile(int quantita_disponibile) {
		this.quantita_disponibile = quantita_disponibile;
	}

	public float getPrezzo() {
		return prezzo;
	}

	public void setPrezzo(float prezzo) {
		this.prezzo = prezzo;
	}

	public String getCodiceProdotto() {
		return codiceProdotto;
	}

	public String getDescrizione() {
		return descrizione;
	}

	public void setDescrizione(String descrizione) {
		this.descrizione = descrizione;
	}
	
	/*******************************************/
	// METODO DERIVATO DALLA SUPERCLASSE OBJECT
	/*******************************************/

	@Override
	public String toString() {
		return "Prodotto{" + "codice prodotto=" + codiceProdotto + ", descrizione=" + descrizione + ", quantità="+ quantita_disponibile + ", prezzo=" + prezzo +'}';
	}
}
