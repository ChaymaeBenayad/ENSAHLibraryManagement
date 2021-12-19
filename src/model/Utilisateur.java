package model;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

public class Utilisateur {
	private SimpleIntegerProperty id;
	private SimpleStringProperty nom;
	private SimpleStringProperty prenom;
	private SimpleStringProperty CNE;
	private SimpleStringProperty email;
	private SimpleStringProperty motDePasse;
	private SimpleIntegerProperty numTelephone;
	private EtatEtudiant etat;
	
	public Utilisateur() {
		super();
	};

	public Utilisateur(int id) {
    	super();
    	this.id = new SimpleIntegerProperty(id);	
    }
	public Utilisateur(int id, String nom, String prenom, String CNE, String email, int numTelephone,
			EtatEtudiant etat) {
		super();
		this.id = new SimpleIntegerProperty(id);
		this.nom = new SimpleStringProperty(nom);
		this.prenom = new SimpleStringProperty(prenom);
		this.CNE = new SimpleStringProperty(CNE);
		this.email = new SimpleStringProperty(email);
		this.numTelephone = new SimpleIntegerProperty(numTelephone);
		this.etat = etat;
	}
	
	public int getId() {
		return id.get();
	}

	public String getNom() {
		return nom.get();
	}

	public String getPrenom() {
		return prenom.get();
	}

	public String getCNE() {
		return CNE.get();
	}

	public String getEmail() {
		return email.get();
	}
	
	public String getMotDePasse() {
		return motDePasse.get();
	}

	public int getNumTelephone() {
		return numTelephone.get();
	}

	public EtatEtudiant getEtat() {
		return etat;
	}
	
	public void setId(int id) {
		this.id = new SimpleIntegerProperty(id);
	}

	

}
