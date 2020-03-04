import java.util.*;

public class AcquistoDiretto extends Annuncio{
	
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

}
