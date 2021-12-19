package controller;

import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;


import database.DB;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import model.EtatEtudiant;
import model.Utilisateur;

public class RechercherEtudiantController implements Initializable {
	
	@FXML
    private Label label;
	
	@FXML
	private TextField filterField;

	@FXML
	private TableView<Utilisateur> tabEtud;

	@FXML
	private TableColumn<Utilisateur, Integer> idCol;

	@FXML
	private TableColumn<Utilisateur, String> nomCol;

	@FXML
	private TableColumn<Utilisateur, String> prenomCol;

	@FXML
	private TableColumn<Utilisateur, String> CNECol;

	@FXML
	private TableColumn<Utilisateur, String> emailCol;

	@FXML
	private TableColumn<Utilisateur, Integer> teleCol;

	@FXML
	private TableColumn<Utilisateur, EtatEtudiant> etatCol;

	ObservableList<Utilisateur> data = FXCollections.observableArrayList();

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		initialiserCol();
		afficherEtudiants();

	}
	 /**********************************************************
     * Initialiser les colonnes de la table des étudiants
     ************************************************************/
	private void initialiserCol() {

		idCol.setCellValueFactory(new PropertyValueFactory<Utilisateur, Integer>("id"));
		nomCol.setCellValueFactory(new PropertyValueFactory<Utilisateur, String>("nom"));
		prenomCol.setCellValueFactory(new PropertyValueFactory<Utilisateur, String>("prenom"));
		CNECol.setCellValueFactory(new PropertyValueFactory<Utilisateur, String>("CNE"));
		emailCol.setCellValueFactory(new PropertyValueFactory<Utilisateur, String>("email"));
		teleCol.setCellValueFactory(new PropertyValueFactory<Utilisateur, Integer>("numTelephone"));
		etatCol.setCellValueFactory(new PropertyValueFactory<Utilisateur, EtatEtudiant>("etat"));
	}
	/*****************************************************
	    * Affichage de tous les étudiants
	    *****************************************************/
	private void afficherEtudiants() {
		try {
			DB.connect();
			// affichage de tous les données de la table etudiant
			DB.ps = DB.conn.prepareStatement("select * from etudiant");
			DB.rs = DB.ps.executeQuery();
			while (DB.rs.next()) {
				
				Integer IDEtud = DB.rs.getInt("id");
				String Nom = DB.rs.getString("nom");
				String Prenom = DB.rs.getString("prenom");
				String Cne = DB.rs.getString("CNE");
				String Email = DB.rs.getString("email");
				Integer Telephone = DB.rs.getInt("numTel");
				EtatEtudiant Etat = EtatEtudiant.valueOf(DB.rs.getString("etat"));

				data.add(new Utilisateur(IDEtud, Nom, Prenom, Cne, Email, Telephone, Etat));
				
				  //mettre ObservableList dans une FilteredList (affiche initialement toutes les données)
		        FilteredList<Utilisateur> dataFiltree = new FilteredList<>(data, b -> true);
				
				// Définir le prédicat de filtre à chaque changement de filtre
				filterField.textProperty().addListener((observable, ancVal, nouvVal) -> {
					dataFiltree.setPredicate(etudiant -> {
						// Si le texte du filtre est vide, afficher tous les élèves
										
						if (nouvVal == null || nouvVal.isEmpty()) {
							return true;
						}
						// Comparer CNE et le nom de chaque étudiant avec le texte du filtre
						String lowerCaseFilter = nouvVal.toLowerCase();
						String upperCaseFilter = nouvVal.toUpperCase();
						
						if (etudiant.getNom().toLowerCase().indexOf(lowerCaseFilter) != -1 ) {
							return true; //Le filtre correspond au nom 
						} else if (String.valueOf(etudiant.getCNE()).indexOf(upperCaseFilter)!=-1)
						     return true; //Le filtre correspond au CNE
						     else  
						    	 return false; //Ne correspond pas
					});
				});
				
				//mettre FilteredList dans une SortedList
				SortedList<Utilisateur> dataTriee = new SortedList<>(dataFiltree);
				
				// Liez le comparateur SortedList au comparateur TableView.
				//Sinon, le tri de la TableView n'aurait aucun effet
				dataTriee.comparatorProperty().bind(tabEtud.comparatorProperty());
				//Ajoutez des données triées (et filtrées) à la table
				tabEtud.setItems(dataTriee);
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}

		
	}

}
