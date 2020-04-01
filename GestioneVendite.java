import annunci.*;
import eccezioni.*;
import java.util.*;
import java.text.*;
import java.io.*;

public class GestioneVendite {

	// flag per tenere traccia di modifiche non salvate su file
	private boolean modifica=false;
	private String file="Annuncio.dat";
	
	Scanner input = new Scanner (System.in);
	private Vector <Annuncio> v  = new Vector <Annuncio>();
	
	//Creo l'oggetto data now, con valore di data odierna come variabile statica
	//in modo da essere costante per tutti i metodi della classe
	private static Date now = new Date();
	
	public GestioneVendite (Vector <Annuncio> v) {
		this.v=v;
		try {
		ObjectInputStream file_input = new ObjectInputStream(new BufferedInputStream(new FileInputStream(file)));
		// legge l'intero vettore da file
		v = ((Vector<Annuncio>) file_input.readObject());
		file_input.close();
	} catch (FileNotFoundException e) {
		// gestisce il caso in cui il file non sia presente
		System.out.println("Il file Annuncio non è mai stato salvato");
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
}
	//metodo per aggiungere aste al rialzo
	public void aggiungiasta(Annuncio nuovoannuncio, String descrizione) {
				//richiamo il metodo per chiedere il prezzo
				double prezzoiniziale=chiediprezzo();
				
				// mi serve per mangiarmi lo spazio successivo
				//altrimenti input.nextLine() successivo mi mangia lo spazio e viene skippato
				input.nextLine();
				Date datagiusta=null;
				boolean controllo=true;
				do {
				try {
				//richiamo il metodo per chiedere la data
				datagiusta=chiedidata();
				if(!controllodatascadenza(datagiusta))
					throw new EccezioneData("Data invalida: La data di scadenza deve essere posteriore a quella attuale.");
				else controllo=false;
				}catch (EccezioneData e) {
					System.out.println(e.getMessage());
					System.out.println("Inserisci una data corretta.");
				}
				}while(controllo);
		
				System.out.println("Inserisci il nome dell'ultimo offerente:");
				String ultimoofferente= input.nextLine();
				nuovoannuncio= new AstaRialzo(descrizione, now, prezzoiniziale, datagiusta, ultimoofferente);
				// aggiungo il nuovo oggetto creato al vettore
				v.add(nuovoannuncio);
				modifica=true; // modifica non salvata
				System.out.println("Hai creato un nuovo annuncio!");
				System.out.println("");
	}
	
	//metodo per aggiungere acquisti diretti
	public void aggiungidiretto(Annuncio nuovoannuncio, String descrizione) {
				//metodo per chiedere il prezzo
				double prezzo=chiediprezzo();
				System.out.println("Inserisci il tempo di consegna (numero di giorni): ");
				boolean verifica=true;
					do {
						try {
							int tempiconsegna=input.nextInt();
							nuovoannuncio= new AcquistoDiretto (descrizione, now, prezzo, tempiconsegna);
							v.add(nuovoannuncio);
							modifica=true; // modifica non salvata
							verifica=false;
							System.out.println("Hai creato un nuovo annuncio!");
							System.out.println("");
						}catch(InputMismatchException e) {
							input.next();
							System.out.println("Hai inserito un carattere non valido.");
							System.out.println("Riprova!");
						}
					}while(verifica);
		}
	
//metodo per chiedere la data	
	public Date chiedidata() {
		boolean controllo=true;
		//in questo punto gestisco l'inserimento della data
		//con SimpleDateFormat sto impostando il nuovo formato possibile di data da inserire
		//imposto una booleana per fare in modo che se ci sia l'eccezione
		//il programma continui a chiedere una data valida e non si interrompa
		Date datagiusta=null;
		do {
			try {
				System.out.println("Inserisci una data di scadenza in formato dd-MM-yyyy: ");
				//String datascadenza=new String();
				String datascadenza=input.nextLine();
				
				//controllo per non lanciare un'eccezione nel caso in cui la data fosse ben formattata
				//ma per errore dovesse iniziare con uno spazio
				//se la data inizia per spazio, lo spazio viene automaticamente eliminato
				while (datascadenza.charAt(0)==' ') {
					datascadenza=datascadenza.substring(1,datascadenza.length());
				}
				
				//metodo per lavorare su anno, mese, giorno separatamente
				//la stringa dell'utente viene spezzata in tre elementi (giorno, mese e anno)
				//in questo modo posso chiedere la data all'utente una volta sola, senza spezzare in tre input
				//allo stesso tempo è possibile fare gli opportuni controlli singolarmente su gioni, mesi e anni
				String[] arraydata = datascadenza.split("-");
				int giorno=Integer.parseInt(arraydata[0]);
				int mese=Integer.parseInt(arraydata[1]);
				int anno=Integer.parseInt(arraydata[2]);
				
				// controlla la correttezza del numero di giorni del mese (ritorna valore booleano)
				boolean giornigiusti=controllogiorni(giorno, mese, anno);
				// controlla la correttezza del numero di mesi inseriti
				if((mese < 1 || mese > 12) || !giornigiusti)
					throw new EccezioneData("Numero di gioni o mesi non valido!");
				else {
					SimpleDateFormat newformat = new SimpleDateFormat ("dd-MM-yyyy");
					datagiusta=newformat.parse(datascadenza);
					controllo=false;
				}

			} catch (EccezioneData e) {
					System.out.println(e.getMessage());
					System.out.println("Inserire nuovamente la data");
			} catch (Exception e) {
					System.out.println("Impossibile ricavare una data valida dal formato inserito.");
					System.out.println("Inserisci una data di scadenza in un formato valido (es.: 02-05-2020)");
				}
		}while (controllo);
		
	return datagiusta;
}
	
	public void visualizza() {
		if (v.isEmpty()) {
			System.out.println("");
			System.out.println("Non è stato creato nessun annuncio.");
			System.out.println();
		}else {
			System.out.println("");
			System.out.println("Elenco degli annunci:");
			for (Annuncio x : v) {
			System.out.println(x);
			System.out.println("");
			}
	}
}
		
	public void visualizzaAste() {
		boolean vuotoA=true;
		System.out.println("");
		System.out.println("Elenco delle aste a rialzo:");
		for (Annuncio x:v) 
		if (x instanceof AstaRialzo){
			System.out.println(x);
			System.out.println("");
			vuotoA=false;
		}
		if (vuotoA) {
			System.out.println("Non è stato creato nessun annuncio di aste al rialzo. Fai tu la prima mossa!");
			System.out.println();
			}
	}

	public void visualizzaDiretti() {
		boolean vuotoD=true;
		System.out.println("");
		System.out.println("Elenco degli acquisti diretti:");
		for (Annuncio x:v) 
			if (x instanceof AcquistoDiretto) {
				System.out.println(x);
				System.out.println("");
				vuotoD=false;
			}
		if (vuotoD) {
			System.out.println("Non è stato creato nessun annuncio di acquisti diretti. Fai tu la prima mossa!");
			System.out.println();
			}
	}
	
	public void visualizzaAfter() {
		boolean vuoti=true;

		//richaimo il metodo per chiedere la data descritto sopra
		
		Date datagiusta=chiedidata();
		SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");  
		String dataformat= formatter.format(datagiusta);
		System.out.println("Ecco gi annunci dopo il "+ dataformat+":");
		for (Annuncio x:v) 
			if (x.getdata().after(datagiusta)) {
				System.out.println(x);
				System.out.println("");
				vuoti=false;
		}
		if(vuoti)
			System.out.println("Non ci sono aste al rialzo dopo il "+ dataformat);
			System.out.println();
	}

	//metodo per la visualizzazione delle aste scadute
	public void visualizzascadute() {
		boolean vuote=true;
		System.out.println("");
		for (Annuncio x:v) {
			if (x instanceof AstaRialzo) {
				if (((AstaRialzo) x).getdatascadenza().before(now)) {
					System.out.println(x);
					System.out.println("");
					vuote=false;
				}
			}
		}
			if(vuote) {
				System.out.println("Non ci sono aste scadute");
				System.out.println();
			}
		}
	
	//metodo per la rimozione di un annuncio Acquisto Diretto
	public void rimuovidiretto() {
		boolean empty=true;
		System.out.println("");
		//creo un array della stessa dimensione del vettore
				//l'array mi serve per storare gli indici di posizione delle aste scadute
				//seguendo un indice sequenziale, utile alla loro eliminazione
				int[] posizioni = new int [v.size()];
				int contatore=0;
				System.out.println("Questi sono gli Acquisti diretti disponibili:");
				System.out.println("");
				for (int i=0; i<v.size();i++) {
					if (v.get(i) instanceof AcquistoDiretto) {
							System.out.print(contatore + "- ");
							System.out.println(v.get(i));
							System.out.println("");
							empty=false;
							// creo un array che come indice ha un contatore che avanza man mano che trova delle aste scadute
							// l'array memorizza, ogni volta che trova un annuncio di Acqu. Diretto, la posizione dello stesso sul vettore
							// in questo modo ho un array che ordina sequenzialmente e ordinatamente le posizioni delle aste scadute sul vettore
							//il contatore funge da indice dell'array
							//Contatore parte da 0 per riempire ordinatamente l'array e si incrementa ad ogni ciclo
							posizioni[contatore]=i;
							contatore++;
					}
				}
				if(!empty) {
				boolean bandierina=true;
				System.out.println("Quale annuncio vuoi acquistare?");
				do {
					try {
				int elenco=input.nextInt();
				// se il numero inserito è maggiore del contatore (cioè dell'indice dell'ultimo elemento inserito nell'array), getto l'eccezione
				if(elenco>contatore) throw new ErroreIndice("Errore nella digitazione dell'indice.");
				else {
					bandierina=false;
					System.out.println("Complimenti! Hai acquistato: "+ v.get(posizioni[elenco]).getdescrizione());
					// stampo i tempi di consegna per l'acquisto fatto
					System.out.print("Per l'acquisto effettuato sono necessari ");
					System.out.print(((AcquistoDiretto) v.get(posizioni[elenco])).gettempiconsegna());
					System.out.println(" giorni.");
					// rimuovo l'annuncio nella posizione "elenco" dell'array
					v.remove(posizioni[elenco]);
				}
					}catch (ErroreIndice e) {
						System.out.print(e.getMessage());
						System.out.println("Digita il numero dell'annuncio che vuoi eliminare: ");
					}
					catch (InputMismatchException e) {
						input.next();
						System.out.println("Non hai inserito un carattere valido.");
						System.out.println("Digita il numero dell'annuncio che vuoi eliminare:");
					}
				}while(bandierina);
				}else {
					System.out.println("Non ci sono aquisti diretti diponibili.");
					System.out.println();
				}
	}
	
	//metodo per l'eliminazione di un annuncio Asta al Rialzo
	public void rimuoviasta() {
		//variabile utile a capire se il vettore è vuoto
		boolean empty=true;
		System.out.println("");
		//creo un array della stessa dimensione del vettore
		//l'array mi serve per storare gli indici di posizione delle aste scadute
		//seguendo un indice sequenziale, utile alla loro eliminazione
		int[] posizioni = new int [v.size()];
		int contatore=0;
		System.out.println("Queste sono le aste scadute:");
		System.out.println("");
		for (int i=0; i<v.size();i++) {
			if (v.get(i) instanceof AstaRialzo) {
				if (((AstaRialzo) v.get(i)).getdatascadenza().before(now)) {
					System.out.print(contatore + "- ");
					System.out.println(v.get(i));
					System.out.println("");
					empty=false;
					// creo un array che come indice ha un contatore che avanza man mano che trova delle aste scadute
					// l'array memorizza, ogni volta che trova un'asta scaduta, la posizione che la relativa asta aveva sul vettore
					// in questo modo ho un array che ordina sequenzialmente le posizioni delle aste scadute sul vettore
					posizioni[contatore]=i;
					contatore++;
				}
			}
		}
		if(!empty) {
		System.out.println("Quale annuncio vuoi eliminare?");
		boolean bandierina=true;
		do {
			try {
		int elenco=input.nextInt();
		// se il numero inserito è maggiore del contatore (cioè dell'indice dell'ultimo elemento inserito nell'array), getto l'eccezione
		if(elenco>contatore) throw new ErroreIndice("Errore nella digitazione dell'indice.");
		else {
			bandierina=false;
			// stampo il nome dell'ultimo offerente
			System.out.print("Il nome dell'ultimo offerente è: ");
			System.out.println(((AstaRialzo) v.get(posizioni[elenco])).getultimoofferente());
			// rimuovo l'annuncio nella posizione "elenco" dell'array
			v.remove(posizioni[elenco]);
		}
			}catch (ErroreIndice e) {
				input.next();
				System.out.print(e.getMessage());
				System.out.println("Digita il numero dell'annuncio che vuoi eliminare: ");
			}
			catch (InputMismatchException e) {
				input.next();
				System.out.println("Non hai inserito un carattere valido.");
				System.out.println("Digita il numero dell'annuncio che vuoi eliminare:");
			}
		}while(bandierina);
		}else {
			System.out.println("Non ci sono aste scadute");
			System.out.println();
		}	
		}
	
	public void nuovaofferta() {
		//faccio la stessa cosa di rimuovi Asta, ma invece di chiedere la rimozione, chiedo di comporre una nuova offerta
		boolean empty=true;
		int[] posizioni = new int [v.size()];
		int contatore=0;
		System.out.println("");
		System.out.println("Queste sono le aste ancora aperte:");
		System.out.println("");
		for (int i=0; i<v.size();i++) {
			if (v.get(i) instanceof AstaRialzo) {
				if (((AstaRialzo) v.get(i)).getdatascadenza().after(now)) {
					System.out.print(contatore + "- ");
					System.out.println(v.get(i));
					System.out.println("");
					empty=false;
					posizioni[contatore]=i;
					contatore++;
				}
			}
		}
		if(!empty) {
		System.out.println("Per quale asta vuoi fare un'offerta?");
		boolean bandierina=true;
		do {
			try {
		int elenco=input.nextInt();
		// se il numero inserito è maggiore del contatore (cioè dell'indice dell'ultimo elemento inserito nell'array), getto l'eccezione
		if(elenco>contatore) throw new ErroreIndice("Errore nella digitazione dell'indice.");
		else {
			bandierina=false;
			System.out.println("Quanto vuoi offrire?");
			boolean ripetiofferta=true;
			do {
			try {
			double nuovaofferta=input.nextDouble();
			//se l'offerta fatta è più bassa dell'ultima offerta, getto l'eccezione
			if (nuovaofferta<=((AstaRialzo) v.get(posizioni[elenco])).getimportoultimaofferta()) throw new EccezioneOfferta("Offerta non valida.");
			//dato che ho già verificato che l'oggetto in questione è istanza di AstaRialzo, posso castare senza incorrere in problemi
			//imposto la nuova offerta.
			else {
			ripetiofferta=false; 
			((AstaRialzo) v.get(posizioni[elenco])).nuovaofferta(nuovaofferta);
			System.out.println("Hai fatto una nuova offerta!");
			System.out.println("");
			}
			//gestisco l'eccezione dell'offerta minore dell'ultima offerta
			}catch(EccezioneOfferta e) {
				input.next();
				System.out.println(e.getMessage());
				System.out.println("Inserisci un importo che sia maggiore dell'ultima offerta.");
			}
			catch (InputMismatchException e) {
				System.out.println("Carattere inserito non valido");
			}	
			}while(ripetiofferta);
			}
			//gestisco l'eccezione nel caso in cui l'input inserito sia fuori dal range degli indici
			}catch (ErroreIndice e) {
				input.next();
				System.out.print(e.getMessage());
				System.out.println("Digita il numero dell'asta a cui vuoi fare un'offerta: ");
			}
			catch (InputMismatchException e) {
				input.next();
				System.out.println("Non hai inserito un carattere valido.");
				System.out.println("Digita il numero dell'annuncio che vuoi eliminare:");
			}	
		}while(bandierina);
		}else {
			System.out.println("Non ci sono aste scadute");
			System.out.println();
		}
	}
	
	//metodo che mi chiede il prezzo sia per acquisto diretto
	//che per asta al rialzo e per gestire l'eccezione sotto descritta
	public double chiediprezzo() {
		boolean control=true;
		//imposto inizialmente il prezzo a 0.0
		double prezzoiniziale=0.0;
		//gestisco l'eccezione dell'inserimento di un prezzo decimale
			do {
				try {
					System.out.println("Inserisci un prezzo:");
					prezzoiniziale=input.nextDouble();
					control=false;
				}catch (InputMismatchException e) {
					input.next();
					System.out.println("Formato non valido.");
					System.out.println("Inserisci un nuovo prezzo.");
					System.out.println("Usare la virgola per dividere le unità dai decimali.");
				}
			}while (control);
		return prezzoiniziale;
	}
	
	// verifica se ci sono modifiche non salvate
		public boolean daSalvare() {
			return modifica;
		}
		// salva il registro nel file
		// restituisce true se il salvataggio è andato a buon fine
		public boolean salva() {
			if (daSalvare()) { // salva solo se necessario (se ci sono modifiche)
				try {
					ObjectOutputStream file_output = new ObjectOutputStream(new BufferedOutputStream(new FileOutputStream(file)));
					// salva l'intero vettore nel file
					file_output.writeObject(v);
					file_output.close();
					modifica = false; // le modifiche sono state salvate
					return true;
				} catch (IOException e) {
					System.out.println("ERRORE di I/O");
					System.out.println(e);
					return false;
				}		
			} else return true;
		}
		
		// metodo statico che mi ritorna true se i giorni del mese sono stati inseriti correttamente
		//come regola generale si è deciso di utilizzare metodi statici nel caso in cui si siano creati dei metodi
		//il cui utilizzo era svincolato dalla creazione di un oggetto
		//che quindi avessero senso anche da soli
		public static boolean controllogiorni(int giorno, int mese, int anno) {
			
			boolean giusto=true;
			switch(mese) {
			 case 4: case 6:case 9:case 11: if(giorno>30) giusto=false; break;
			 
			 case 2: if (anno>1584 && anno % 4 == 0 && anno % 100 != 0 || anno % 400 == 0) {
					 if(giorno>29) giusto=false;
			 	}
				 else {
					 if(giorno>28) giusto=false;
				}
				break;

			 default : if(giorno>31) giusto=false; break;
				}
			return giusto;
		}
	
		//metodo statico per verificare la correttezza della data di scadenza
		public static boolean controllodatascadenza(Date datagiusta){
			boolean corret=true;
			if(now.after(datagiusta))
				corret=false;
		return corret;
		}
	}
