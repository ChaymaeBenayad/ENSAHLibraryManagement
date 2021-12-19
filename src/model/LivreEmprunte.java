package model;

import java.util.Date;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;

public class LivreEmprunte {

	private SimpleIntegerProperty idEtud;
	private SimpleIntegerProperty idLiv;
	private SimpleStringProperty titre;
	private SimpleStringProperty auteur;
	private SimpleStringProperty theme;
	private ObjectProperty<Date> dateEmprunt;
	private ObjectProperty<Date> dateRetour;

	public LivreEmprunte(Integer idEtud, Integer idLiv, String titre, String auteur, String theme, Date dateEmprunt,
			Date dateRetour) {
		super();
		this.idEtud = new SimpleIntegerProperty(idEtud);
		this.idLiv = new SimpleIntegerProperty(idLiv);
		this.titre = new SimpleStringProperty(titre);
		this.auteur = new SimpleStringProperty(auteur);
		this.theme = new SimpleStringProperty(theme);
		this.dateEmprunt = new SimpleObjectProperty<Date>(dateEmprunt);
		this.dateRetour = new SimpleObjectProperty<Date>(dateRetour);
	}
	
	public LivreEmprunte(Integer idLiv, String titre, String auteur, String theme, Date dateEmprunt,
			Date dateRetour) {
		super();
		this.idLiv = new SimpleIntegerProperty(idLiv);
		this.titre = new SimpleStringProperty(titre);
		this.auteur = new SimpleStringProperty(auteur);
		this.theme = new SimpleStringProperty(theme);
		this.dateEmprunt = new SimpleObjectProperty<Date>(dateEmprunt);
		this.dateRetour = new SimpleObjectProperty<Date>(dateRetour);
	}

	public Integer getIdEtud() {
		return idEtud.get();
	}

	public Integer getIdLiv() {
		return idLiv.get();
	}

	public String getTitre() {
		return titre.get();
	}

	public String getAuteur() {
		return auteur.get();
	}

	public String getTheme() {
		return theme.get();
	}

	public Date getDateEmprunt() {
		return dateEmprunt.get();
	}

	public Date getDateRetour() {
		return dateRetour.get();
	}

}
