import eccezioni.*;
import java.io.*;
import java.util.*;

public class MainProva {

	public static void main(String[] args) {
		Scanner input = new Scanner (System.in);
		System.out.println("Benvenuto/a in Hasta L'Asta!");
		
		//accedi (Output) o registrati(Input)
		
		//creo un vettore di utenti inizialmente vuoto che deve essere riempito
		//una volta lanciato
		Vector <Utente> utenti = new Vector <Utente>();
		
		//creo l'oggetto menu delle aste che mi permette di inizializzare tutte le operazioni
		Menu mymenu=new Menu();
		//legge il file
		try {
			ObjectInputStream files_input = new ObjectInputStream(new BufferedInputStream(new FileInputStream("Utente.dat")));
			// legge l'intero vettore da file
			utenti = (Vector<Utente>)files_input.readObject();
			files_input.close();
		} catch (FileNotFoundException e) {
			// gestisce il caso in cui il file non sia presente
			System.out.println("Il file Utente non è mai stato salvato");
			System.out.println("Sara' creato al primo salvataggio");
			System.out.println();
		} catch (ClassNotFoundException e) {
			// gestisce il caso in cui il file non contenga un oggetto
			System.out.println("ERRORE di lettura");
			System.out.println(e);
		} catch (IOException e) {
			// gestisce altri errori di input/output
			System.out.println("ERRORE di I/O");
			System.out.println(e);
		}
	
		if(!utenti.isEmpty()) {
			mymenu.ingresso(utenti);
		}
		else {
			System.out.println("Non ci sono utenti ancora registrati.");
			System.out.println("Si proceda alla registrazione");
			//se il vettore è vuoto, si salta tutta la parte di accesso superflua
			//come ad esempio il controllo dell'user
			//per andare direttamente alla creazione dell'utente
			utenti.add(mymenu.creautente());
					//salva il vettore nel file
					try {
						ObjectOutputStream files_output = new ObjectOutputStream(new BufferedOutputStream(new FileOutputStream("Utente.dat")));
						// salva l'intero vettore nel file
						files_output.writeObject(utenti);
						files_output.close();
					} catch (IOException e) {
						System.out.println("ERRORE di I/O");
						System.out.println(e);
					}
						
						//una volta che l'utente viene creato, si procede alla fase di autenticazione
						mymenu.ingresso(utenti);
		}
		
		
		input.close();
	}
}

