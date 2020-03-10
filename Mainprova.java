import annunci.*;
import java.util.*;

public class MainProva {

	public static void main(String[] args) {
		Scanner input= new Scanner (System.in);
		Vector <Annuncio> v = new Vector();
		GestioneVendite ciccio= new GestioneVendite(v);
		boolean controllo= true;
		do {
		System.out.println("Operazioni disponibili: ");
		System.out.println("");
		System.out.println("1- Crea un nuovo annuncio.");
		System.out.println("2- Visualizza tutti gli annunci.");
		System.out.println("3- Visualizza solo le aste al rialzo.");
		System.out.println("4- Visualizza solo gli acquisti diretti.");
		System.out.println("5- Visualizza gli annunci successivi ad una certa data.");
		System.out.println("6- Visualizza le aste scadute. ");
		System.out.println("7- Rimuovi annuncio di asta al rialzo scaduta. ");
		System.out.println("8- Termina le operazioni.");
		System.out.println("");
		int scelta= input.nextInt();
		input.nextLine();
		switch (scelta) {
			case 1 :	System.out.println("- Crea un nuovo annuncio -");
						System.out.println("");
						Date now =new Date();
						System.out.println("Aggiungi una breve descrizione del prodotto: ");
						String desc=input.nextLine();
						ciccio.aggiungiannuncio(desc, now);
						break;
			case 2: System.out.println("- Visualizza tutti gli annunci -");
					System.out.println("");
					ciccio.visualizza(); break;
			case 3: System.out.println("- Visualizza solo le aste al rialzo -");
					ciccio.visualizzaAste(); break;
			case 4: System.out.println("- Visualizza solo gli acquisti diretti -");
					ciccio.visualizzaDiretti(); break;
			case 5: System.out.println("- Visualizza solo gli acquisti diretti -");
					ciccio.visualizzaAfter(); break;
			case 6: System.out.println("- Visualizza le aste scadute -");
					ciccio.visualizzascadute(); break;
			case 7: System.out.println("- Rimuovi annuncio di asta al rialzo scaduta -");
					ciccio.rimuoviannuncio(); break;
			case 8: System.out.println("- Termina le operazioni -");
					controllo=false; break;
			default : System.out.println("Errore nell'inserimento");
		}
		}while (controllo);
		System.out.println("Grazie e Arrivederci.");
	}

}
