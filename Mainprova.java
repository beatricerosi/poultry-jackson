import java.util.Vector;

import annunci.Annuncio;

//import java.util.*;
//import annunci.Annuncio;
//import eccezioni.EccezioneDigitazione;

public class MainProva {

	public static void main(String[] args) {
		System.out.println("Benvenuto/a in Hasta L'Asta!");
		//Scanner input = new Scanner(System.in);
		
		//accedi (Output) o registrati(Input)
		//class UtenteRegistrato (nome, cognome, username, password)
		//eccezione (if/else)
		//Utente-->vettore
		
		Vector <Utente> utenti = new Vector <Utente>();
		
		// crea tre utenti impostando username e password
		//Utente[] utenti = new Utente[3];
		Utente tizio = new Utente("Tizio", "tizio", "123");
		utenti.add(tizio);
		Utente caio = new Utente("Caio", "caio", "456");
		utenti.add(caio);
		//utenti[2] = new Utente("Sempronio", "sempronio", "789");
		
		Menu menu1=new Menu(<utenti> utenti);

		System.out.println("Accedi alle aste online");
				menu1.login();
	}
}
