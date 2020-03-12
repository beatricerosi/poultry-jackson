package annunci;
import java.util.*;

public class AcquistoDiretto extends Annuncio{

		// variabile statica richiesta da Serializable
		static final long serialVersionUID = 1;
	
	private double prezzo;
	private int tempiconsegna;
	
	public AcquistoDiretto(String descrizione, Date now, double prezzo, int tempiconsegna) {
		super (descrizione, now);
		this.prezzo=prezzo;
		this.tempiconsegna=tempiconsegna;
	}
	//imposto i debiti metodi getter
	
	public double getprezzo() {
		return prezzo;
	}
	public int gettempiconsegna() {
		return tempiconsegna;
	}
	//overriding dei metodi toString per una visualizzazione adeguata
	public String toString() {
		return "Categoria: Acquisto diretto. " + "Prodotto: " + descrizione+ ". Prezzo: "+ prezzo + ". Per la consegna servono: " + tempiconsegna + " giorni.";
	}

}
