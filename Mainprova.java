import eccezioni.*;
import java.io.*;
import java.util.*;
import annunci.*;

public class MainProva {

	public static void main(String[] args) {
		Scanner input = new Scanner (System.in);
		System.out.println("Benvenuto/a in Hasta L'Asta!");
		
		//accedi (Output) o registrati(Input)
		
		//creo un vettore di utenti inizialmente vuoto che deve essere riempito
		//una volta lanciato
		Vector <Utente> utenti = new Vector <Utente>();
		
		//creo il menu delle aste
		Menu mymenu=new Menu(utenti);
		
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
		
		for(Utente x:utenti) {
		System.out.println("");
		System.out.println(x);
		}
		boolean bandiera=true;
		do {
		if(!utenti.isEmpty()) {
		System.out.println("Accedi [A] o Registrati [R]");
		char scelta=input.next().charAt(0);
		input.nextLine();
		try {
		if (scelta=='A') {
			System.out.println("Accedi alle aste online");
			try {
				ObjectInputStream files_input=new ObjectInputStream(new BufferedInputStream(new FileInputStream("Utente.dat")));
				utenti = (Vector<Utente>) files_input.readObject();
				//mymenu.login();
				files_input.close();
				} catch (ClassNotFoundException e) {
				// se il file non contiene un oggetto....
				System.out.println("PROBLEMA (manca oggetto nel file)");
				System.out.println(e);
				
				} catch(IOException e) {
				System.out.println("ERRORE di I/O");
				System.out.println(e); 
				}
			bandiera=false;
			mymenu.login();
			mymenu.avviaMenu();
		}
		else if(scelta=='R') {
			utenti.add(mymenu.creautente());
			for(Utente x:utenti) {
				System.out.println("");
				System.out.println(x);
				}
			try {
				ObjectOutputStream files_output = new ObjectOutputStream(new BufferedOutputStream(new FileOutputStream("Utente.dat")));
				// salva l'intero vettore nel file
				files_output.writeObject(utenti);
				files_output.close();
			} catch (IOException e) {
				System.out.println("ERRORE di I/O");
				System.out.println(e);
			}		
			System.out.println("Registrazione eseguita con successo!");
			mymenu.avviaMenu();
			bandiera=false;
		}
		else throw new EccezioneDigitazione ("Errore nella digitazione delle opzioni.");
		}catch(EccezioneDigitazione e) {
			System.out.println(e.getMessage());
			System.out.println("Inserisci un carattere valido");
		}
		}
		else {
			System.out.println("Non ci sono utenti ancora registrati.");
			System.out.println("Si proceda alla registrazione");
			utenti.add(mymenu.creautente());
			mymenu.avviaMenu();
			for(Utente x:utenti) {
				System.out.println("");
				System.out.println(x);
				}
			try {
				ObjectOutputStream files_output = new ObjectOutputStream(new BufferedOutputStream(new FileOutputStream("Utente.dat")));
				// salva l'intero vettore nel file
				files_output.writeObject(utenti);
				files_output.close();
			} catch (IOException e) {
				System.out.println("ERRORE di I/O");
				System.out.println(e);
			}
			bandiera=false;
			
		}
		}while (bandiera);
		
		//input.close();
	}	
}

