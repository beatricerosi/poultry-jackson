import java.util.*;
import eccezioni.*;
import annunci.*;


public class Menu {
	Scanner input= new Scanner (System.in);
	boolean controllo= true;
	private Vector <Utente> registrati;

public Menu(Vector registrati) {
	this.registrati=registrati;
}
//metodo che consente di accedere e usare il terminale
	public void login() {
		boolean flag=true;
		do {
		
		// chiede credenziali di accesso
		System.out.print("username: ");
		String username = input.nextLine();
		System.out.print("password: ");
		String password = input.nextLine();
		
		// verifica le credenziali inserite (cerca tra gli utenti)
		// invocando il metodo opportuno della classe Utente
		int i = 0; 
		boolean loginOK=false;
		while (i<registrati.size() && !loginOK) {
			loginOK = registrati[i].controlla(username, password);
			if (!loginOK) i++;
		}
		
		// se le credenziali sono corrette, avvia il menu' con le operazioni
		if (loginOK) {
			// saluta l'utente
			System.out.println("");
			System.out.println("Benvenuto, " + registrati[i].ottieniNome()+"!");
			avviaMenu();
			
		} else {
			System.out.println("Username o password errati!");
		}
		}while(flag);
		
	}
		
				
public void avviaMenu() {	
	// crea un file
	GestioneVendite ciccio = new GestioneVendite();

	do {
	System.out.println("Queste sono le operazioni disponibili: ");
	System.out.println("");
	System.out.println("1- Crea un nuovo annuncio.");
	System.out.println("2- Visualizza tutti gli annunci.");
	System.out.println("3- Visualizza solo le aste al rialzo.");
	System.out.println("4- Visualizza solo gli acquisti diretti.");
	System.out.println("5- Visualizza gli annunci successivi ad una certa data.");
	System.out.println("6- Visualizza le aste scadute. ");
	System.out.println("7- Rimuovi annuncio di asta al rialzo scaduta. ");
	System.out.println("8- Fai una nuova offerta per l'asta al rialzo. ");
	System.out.println("9- Esegui un acquisto diretto. ");
	System.out.println("10- Termina le operazioni.");
	System.out.println("");
	try {
	int opzione= input.nextInt();
	input.nextLine();
	switch (opzione) {
		case 1 :	System.out.println("- Crea un nuovo annuncio -");
					System.out.println("");
					Date now =new Date();
					System.out.println("Aggiungi una breve descrizione del prodotto: ");
					String desc=input.nextLine();
					Annuncio nuovoannuncio = new Annuncio (desc, now);
					boolean flag=true;
					do {
						System.out.println("Vuoi aggiungere un'asta al rialzo [R] o un acquisto diretto[D]?");
						try {
							char scelta=input.next().charAt(0);
							if (scelta=='R'||scelta=='r') {
								//boolean per controllare che l'utente inserisca la lettera giusta
								flag=false;
								ciccio.aggiungiasta(nuovoannuncio, desc);
							}
							else if(scelta=='D'||scelta=='d') {
								//boolean per controllare che l'utente inserisca la lettera giusta
								flag=false;
								ciccio.aggiungidiretto(nuovoannuncio, desc);
							}
							else {
								//si è deciso in estendere la classe eccezione per migliorare la lettura del codice
								throw new EccezioneDigitazione("Errore nell'inserimento.");
							}
						}catch (EccezioneDigitazione e) {
								System.out.println(e.getMessage());
								System.out.println("Riprova!");
							}
						}while(flag);
					break;
		case 2: System.out.println("- Visualizza tutti gli annunci -");
				ciccio.visualizza(); break;
		case 3: System.out.println("- Visualizza solo le aste al rialzo -");
				ciccio.visualizzaAste(); break;
		case 4: System.out.println("- Visualizza solo gli acquisti diretti -");
				ciccio.visualizzaDiretti(); break;
		case 5: System.out.println("- Visualizza gli annunci successivi ad una certa data -");
				ciccio.visualizzaAfter(); break;
		case 6: System.out.println("- Visualizza le aste scadute -");
				ciccio.visualizzascadute(); break;
		case 7: System.out.println("- Rimuovi annuncio di asta al rialzo scaduta -");
				ciccio.rimuoviasta(); break;
		case 8: System.out.println("- Fai una nuova offerta per un'asta al rialzo -");
				ciccio.nuovaofferta(); break;
		case 9: System.out.println("- Esegui un acquisto diretto -");
				ciccio.rimuovidiretto(); break;
		case 10: System.out.println("- Termina le operazioni -"); //qua forse conviene fare un metodo in GestioneVendite
				controllo=false; 
				if (ciccio.daSalvare()) {
					//inizializzo la variabile risposta a 0 di default
					char risposta = 0;
					do {
						try {
						System.out.println("Vuoi salvare le modifiche effettuate (S/N)?");
						risposta = input.nextLine().charAt(0);
						if (risposta=='S') { 
							boolean tuttoOk = ciccio.salva();
							if (tuttoOk) 
								System.out.println("Dati Salvati.");
							else
								System.out.println("Problema durante il salvataggio.");
							}
						else if(risposta=='N') System.out.println("I dati non sono stati salvati.");
						else throw new EccezioneDigitazione ("Valore inserito non valido");
						
						}catch (EccezioneDigitazione e) {
							System.out.println(e.getMessage());
							System.out.println("Inserisci un carattere [S] o [N]");
						}
					} while (risposta!='S' && risposta!='N');
				}
				break;
		default : System.out.println("Errore nell'inserimento");
	}
	}catch(InputMismatchException e) {
		input.next();
		System.out.println("Valore inserito non valido.");
	}
	}while (controllo);
	System.out.println("Grazie e Arrivederci.");
input.close();
}
}
