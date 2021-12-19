package model;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

public class Livre {
	private SimpleIntegerProperty id;
	private SimpleStringProperty titre;
	private SimpleStringProperty auteur;
	private SimpleStringProperty editeur;
	private SimpleStringProperty theme;
	private SimpleStringProperty langue;
	private SimpleStringProperty ISBN;
	private SimpleIntegerProperty nbPages;
	private SimpleIntegerProperty quantite;
	private EtatLivre etat;

	public Livre(Integer id, String titre, String auteur, String editeur, String theme, String langue, String ISBN,
			Integer nbPages, Integer quantite, EtatLivre etat) {
		super();
		this.id = new SimpleIntegerProperty(id);
		this.titre = new SimpleStringProperty(titre);
		this.auteur = new SimpleStringProperty(auteur);
		this.editeur = new SimpleStringProperty(editeur);
		this.theme = new SimpleStringProperty(theme);
		this.langue = new SimpleStringProperty(langue);
		this.ISBN = new SimpleStringProperty(ISBN);
		this.nbPages = new SimpleIntegerProperty(nbPages);
		this.quantite = new SimpleIntegerProperty(quantite);
		this.etat = etat;
	}
	public Livre(Integer id, String titre, String auteur, String editeur, String theme, String langue, String ISBN,
			Integer nbPages, Integer quantite) {
		super();
		this.id = new SimpleIntegerProperty(id);
		this.titre = new SimpleStringProperty(titre);
		this.auteur = new SimpleStringProperty(auteur);
		this.editeur = new SimpleStringProperty(editeur);
		this.theme = new SimpleStringProperty(theme);
		this.langue = new SimpleStringProperty(langue);
		this.ISBN = new SimpleStringProperty(ISBN);
		this.nbPages = new SimpleIntegerProperty(nbPages);
		this.quantite = new SimpleIntegerProperty(quantite);
	}
	public Livre(Integer id, String titre, String auteur, String editeur, String theme, String langue, String ISBN,
			Integer nbPages) {
		super();
		this.id = new SimpleIntegerProperty(id);
		this.titre = new SimpleStringProperty(titre);
		this.auteur = new SimpleStringProperty(auteur);
		this.editeur = new SimpleStringProperty(editeur);
		this.theme = new SimpleStringProperty(theme);
		this.langue = new SimpleStringProperty(langue);
		this.ISBN = new SimpleStringProperty(ISBN);
		this.nbPages = new SimpleIntegerProperty(nbPages);
	}
	
	public Livre(Integer id, String titre, String auteur, String theme) {
		this.id = new SimpleIntegerProperty(id);
		this.titre = new SimpleStringProperty(titre);
		this.auteur = new SimpleStringProperty(auteur);
		this.theme = new SimpleStringProperty(theme);	
	}

	public Integer getId() {
		return id.get();
	}

	public String getTitre() {
		return titre.get();
	}

	public String getAuteur() {
		return auteur.get();
	}

	public String getEditeur() {
		return editeur.get();
	}

	public String getTheme() {
		return theme.get();
	}

	public String getLangue() {
		return langue.get();
	}

	public String getISBN() {
		return ISBN.get();
	}

	public Integer getNbPages() {
		return nbPages.get();
	}

	public Integer getQuantite() {
		return quantite.get();
	}

	public EtatLivre getEtat() {
		return etat;
	}

}
