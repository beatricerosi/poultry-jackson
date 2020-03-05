import annunci.*;
import java.util.*;
import java.text.*;

public class GestioneVendite {
	
		Scanner input = new Scanner (System.in);
		private Vector <Annuncio> v  = new Vector <Annuncio>();
		
		public GestioneVendite (Vector <Annuncio> v) {
			this.v=v;
		}
		//metodo per la creazione di un nuovo annuncio
		public void aggiungiannuncio(String descrizione, Date now){
			Annuncio nuovoannuncio = new Annuncio (descrizione, now);
			boolean flag=true;
			
		do {
			try {
			System.out.println("Vuoi aggiungere un'asta al rialzo [R] o un acquisto diretto?[D]");
			char scelta=input.next().charAt(0);
			
			if (scelta=='R') {
				
				flag=false;
				
				boolean control=true;
				double prezzoiniziale=0.0;
				//gestisco l'eccezione dell'inserimento di un prezzo decimale
				
				do {
				try {
					
					System.out.println("Inserisci in prezzo iniziale:");
					prezzoiniziale=input.nextDouble();
					input.nextLine();
					control=false;
				
				
				}catch (InputMismatchException e) {
					input.next();
					System.out.println("Formato non valido.");
					System.out.println("Inserisci un nuovo prezzo.");
					System.out.println("Usare la virgola per dividere le unità dai decimali");
				}
				}while (control);
				
				//in questo punto sto gestendo l'inserimento della data
				//con SimpleDateFormat sto impostando il nuovo formato possibile di data da inserire
				//imposto una booleana per fare in modo che se ci sia l'eccezione
				//il programma continui a chiedere una data valida e non si interrompa
				
				boolean controllo=true;
				Date datagiusta=null;
				do {
					try {
						System.out.println("Inserisci una data di scadenza in formato dd-MM-yyyy: ");
						SimpleDateFormat newformat = new SimpleDateFormat ("dd-MM-yyyy"); 
						String datascadenza=input.next();
						datagiusta=newformat.parse(datascadenza);
						boolean before = now.before(datagiusta);
						if (before) {
							System.out.println("la data inserita è: " + datagiusta);
							controllo=false;
						}else {
							System.out.println("La data di scadenza deve essere superiore a quella attuale");
						}
					}catch (ParseException e) {
						System.out.println("impossibile ricavare una data valida dal formato inserito");
					}
					}while (controllo);
					
					System.out.println("Inserisci il nome dell'ultimo offerente");
					String ultimoofferente= input.nextLine();
					input.nextLine();
					
					nuovoannuncio= new AstaRialzo(descrizione, now, prezzoiniziale, datagiusta, ultimoofferente);
					// bisogna aggiungere l'oggetto al vettore
					v.add(nuovoannuncio);
					System.out.println("Hai creato un nuovo annuncio");
				}
			else if (scelta=='D') {
				
				flag=false;
				
				//gestisci l'eccezione del prezzo
				System.out.println("Inserisci un prezzo:");
				double prezzo=input.nextDouble();
				System.out.println("Inserisci il tempo di consegna (numero di giorni): ");
				int tempiconsegna=input.nextInt();
				nuovoannuncio= new AcquistoDiretto (descrizione, now, prezzo, tempiconsegna);
				v.add(nuovoannuncio);
				System.out.println("Hai creato un nuovo annuncio");
			}
			else {
				//si è deciso in estendere la classe eccezione per migliorare la lettura del codice
				throw new EccezioneDigitazione("Errore nell'inserimento");
			}
			}catch (EccezioneDigitazione e) {
				System.out.println(e.getMessage());
				System.out.println("Riprova");
			}
			}while(flag);
		}
	public void visualizza() {
		for (Annuncio x : v) {
		System.out.println(x);
		System.out.println();
		}
	}
}
