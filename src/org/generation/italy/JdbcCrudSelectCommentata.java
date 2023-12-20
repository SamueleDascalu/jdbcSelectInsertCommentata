package org.generation.italy;

import java.sql.Connection; //classe istanziata per mantenere il riferimento alla connessione stabilita verso il database 
import java.sql.DriverManager; //classe di riferimento per l'uso del JDBC driver installato come dipendenza nel pom.xml di Maven										
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException; //classe per la gestione delle eccezioni di tipo SQL exception
import java.util.ArrayList;

import org.generation.italy.model.Prodotto;

public class JdbcCrudSelectCommentata {

	public static void main(String[] args) {
		/**************************/
		/* CONNESSIONE AL DATABASE 
		/**************************/
		String databaseName = "magazzino"; // nome del database a cui connettersi
		String dbmsUserName = "root"; // nome utente configurato nel dbms
		String dbmsPassword = ""; // password utente configurato nel dbms
		String jdbcUrl = "jdbc:mariadb://localhost:3306/" + databaseName; // url contenente il protocollo che regola la connessione al DB

		try { // prova ad eseguire le istruzioni interne al blocco try-catch

			//con getConnection provo a connettermi al db, se la connessione fallisce avro un valore di ritorno
			//di tipo SQLException
			Connection jdbcConnectionToDatabase = 
					DriverManager.getConnection(jdbcUrl, dbmsUserName, dbmsPassword);

			System.out.println("Connessione al database magazzino riuscita!");
			
			/***********************************/
			/* ESECUZIONE DELLA QUERY DI SELECT
			/***********************************/

			//stringa contenente la query da eseguire
			String selectFromClienteByCodiceProdotto =
					" SELECT codice_prodotto, descrizione, quantita_disponibile, prezzo  "
				  + "   FROM prodotto                       " 
				  + "  WHERE prodotto.codice_prodotto LIKE ? "; // il ? sta ad indicare un valore da inserire in un secondo momento
			
			//variabile che contiene il valore che andro ad inserire nella query
			String parametroCodiceProdotto = "TV%";
			
			//metodo che crea ("prepara") la query da inviare al dbms
			PreparedStatement preparedStatement =
					jdbcConnectionToDatabase.prepareStatement(selectFromClienteByCodiceProdotto);
			//setString va ad agiungere la stringa contenuta in parametroCodiceProdotto nella stringa di query
			preparedStatement.setString(1, parametroCodiceProdotto); // 1 sta ad indicare il primo ? che trova 
			
			//metodo che fa in modo che la query venga eseguita dal dbms e resta "collegato" al cursore in memoria
			ResultSet rsSelect = preparedStatement.executeQuery();
			
			//creao una lista che andra a contenere i vari prodotti trovati con la query
			ArrayList<Prodotto> prodotti = new ArrayList<Prodotto>();
			
			//finche il cursore mi restituisce dei dati eseguo il blocco di codice
			while (rsSelect.next()) { //.next() chiede che venga restituita la prossima riga "row" della tabella cursore
				
				//CREO LE VARIABILI CHE IN CUI INSERIRE I DATI REUPERATI DAL DB
				String codProdotto = rsSelect.getString("codice_prodotto"); //recupero il dato contenuto nel campo codice_prodotto
				//se il valore restituito dal db é nullo, inizializzo la variabile come una stringa vuota
				if (rsSelect.wasNull()) {
					codProdotto = "";
				}

				String descrizione = rsSelect.getString("descrizione");
				if (rsSelect.wasNull()) {
					descrizione = "";
				}

				int quantita_disponibile = rsSelect.getInt("quantita_disponibile");
				if (rsSelect.wasNull()) {
					quantita_disponibile = 0;
				}

				float prezzo = rsSelect.getFloat("prezzo");
				if (rsSelect.wasNull()) {
					prezzo = 0;
				}
				
				//creo un nuovo prodotto con i valori che ho recuperato e lo aggiungo alla lista che ho creato in precedenza
				prodotti.add(new Prodotto(codProdotto, descrizione, quantita_disponibile, prezzo));
			}
			
			//scorro la lista di prodotti e ne mostro i valori
			for (Prodotto televisore: prodotti) {
				if (televisore != null) {
					System.out.println("Dati del prodotto => " + televisore.toString());
				} else {
					System.out.println("Il prodotto ricercato non e presente!!!");
				}
			}
			
			/***********************************/
			/* ESECUZIONE DELLA QUERY DI INSERT
			/***********************************/
			
			//CREO IL PRODOTTO CHE ANDRO AD INSERIRE NEL DB
			Prodotto prodotto = new Prodotto("TVLGC200", "Televisore LG C200", 10, 249.90f);
			
			//stringa contenente la query di insert
			String clienteToInsert = "INSERT INTO prodotto (codice_prodotto, descrizione, quantita_disponibile, prezzo) "
					+ "	VALUES (?,?,?,?)";
			
			//"preparo" la query in modo che sia comprensibile al db
			PreparedStatement preparedStatementInsert = jdbcConnectionToDatabase.prepareStatement(clienteToInsert);
			
			//aggiungo i valori alla query
			preparedStatementInsert.setString(1, prodotto.getCodiceProdotto());
			preparedStatementInsert.setString(2, prodotto.getDescrizione());
			preparedStatementInsert.setInt(3, prodotto.getQuantita_disponibile());
			preparedStatementInsert.setFloat(4, prodotto.getPrezzo());
			
			//chiedo al dbms di eseguire la query
			preparedStatementInsert.executeUpdate();

			//se l'esecuzione é andata a buon fine mostro i valori del prodotto inserito
			System.out.println("Prodotto inserito: => " + prodotto.toString());
			
			/***********************************/
			/* ESECUZIONE DELLA QUERY DI SELECT
			/***********************************/
			
			/*
			 * QUESTA QUERY E UGUALE A QUELLA DI SELECT CHE ABBIAMO FATTO PRIMA MA MOSTRA L'ELEMENTO AGGIUNTO
			 * */
			
			preparedStatement =
					jdbcConnectionToDatabase.prepareStatement(selectFromClienteByCodiceProdotto);
			preparedStatement.setString(1, parametroCodiceProdotto);
			
			rsSelect = preparedStatement.executeQuery();
			
			while (rsSelect.next()) {

				String codProdotto = rsSelect.getString("codice_prodotto");
				if (rsSelect.wasNull()) {
					codProdotto = "";
				}

				String descrizione // lettura del valore del campo 'nominativo'
						= rsSelect.getString("descrizione");
				if (rsSelect.wasNull()) {
					descrizione = "";
				}

				int quantita_disponibile = rsSelect.getInt("quantita_disponibile");
				if (rsSelect.wasNull()) {
					quantita_disponibile = 0;
				}

				float prezzo = rsSelect.getFloat("prezzo");
				if (rsSelect.wasNull()) {
					prezzo = 0;
				}

				prodotti.add(new Prodotto(codProdotto, descrizione, quantita_disponibile, prezzo));
			}
			
			for (Prodotto televisore: prodotti) {
				if (televisore != null) {
					System.out.println("Dati del prodotto => " + televisore.toString());
				} else {
					System.out.println("Il prodotto ricercato non e presente!!!");
				}
			}
			
		} catch (SQLException e) { //se il try fallisce ritorna un errore di tipo classe SQLException
			e.printStackTrace(); // stampa la pila (stack) degli errori, dal piu recente al meno recente
		}
	}

}
