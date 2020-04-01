import java.io.*;
public class Utente implements Serializable{

	// variabile statica richiesta da Serializable
		static final long serialVersionUID = 1;
	// memorizza nome, username e password di un utente
	// come variabili d'istanza
	private String realname;
	private String username;
	private String password;
	
	// inizializza le variabili d'istanza
	public Utente(String nome, String user, String pwd) {
		realname = nome;
		username = user;
		password = pwd;
	}
	
	// controlla se le credenziali ricevute corrispondono a quelle dell'utente
	public boolean controlla(String user, String pwd) {
		return (username.equals(user) && password.equals(pwd));
	}
	
	// fornisce il nome completo dell'utente PER ORA INUTILE
	//public String ottieniNome() {
		//return realname;
	//}
	public String toString() {
		return "Realname "+ realname+ " Username "+ username + " Password " + password;
	}
}

