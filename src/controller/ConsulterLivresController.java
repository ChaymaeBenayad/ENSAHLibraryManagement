package controller;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

import database.DB;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import model.EtatLivre;
import model.Livre;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.util.Callback;
import javafx.scene.control.TableCell;

public class ConsulterLivresController implements Initializable {

	ObservableList<Livre> data = FXCollections.observableArrayList();

	@FXML
	private TableView<Livre> livreTab;
	@FXML
	private TableColumn<Livre, Integer> idCol;
	@FXML
	private TableColumn<Livre, String> titreCol;
	@FXML
	private TableColumn<Livre, String> auteurCol;
	@FXML
	private TableColumn<Livre, String> editeurCol;
	@FXML
	private TableColumn<Livre, String> themeCol;
	@FXML
	private TableColumn<Livre, String> langueCol;
	@FXML
	private TableColumn<Livre, String> ISBNCol;
	@FXML
	private TableColumn<Livre, Integer> nbPagesCol;
	@FXML
	private TableColumn<Livre, Integer> quantiteCol;
	@FXML
	private TableColumn<Livre, EtatLivre> etatCol;
	@FXML
	private AnchorPane Pane;

	/*********************************
	 * Rafraîchir la table des livres
	 ***********************************/
	private void rafraichirTable() {

		try {
			// vider la liste qui contient les données des livres
			data.clear();
			// affichage de tous les données de la table livre
			DB.ps = DB.conn.prepareStatement("select * from livre");
			DB.rs = DB.ps.executeQuery();

			while (DB.rs.next()) {

				Integer Id = DB.rs.getInt("idLivre");
				String Titre = DB.rs.getString("titre");
				String Auteur = DB.rs.getString("auteur");
				String Editeur = DB.rs.getString("editeur");
				String Theme = DB.rs.getString("theme");
				String Langue = DB.rs.getString("langue");
				String ISBN = DB.rs.getString("ISBN");
				Integer NbPages = DB.rs.getInt("nbPages");
				Integer Quantite = DB.rs.getInt("qte");
				EtatLivre Etat = EtatLivre.valueOf(DB.rs.getString("etat"));

				data.add(new Livre(Id, Titre, Auteur, Editeur, Theme, Langue, ISBN, NbPages, Quantite, Etat));
				livreTab.getItems().setAll(data);
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}

	}

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		try {
			DB.connect();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		consulterLivres();
	}

	/********************************************************
	 * Affichage de tous les livres
	 ********************************************************/
	private void consulterLivres() {
		rafraichirTable();
		//initialisation des colonnes de la table des livres
		idCol.setCellValueFactory(new PropertyValueFactory<Livre, Integer>("id"));
		titreCol.setCellValueFactory(new PropertyValueFactory<Livre, String>("titre"));
		auteurCol.setCellValueFactory(new PropertyValueFactory<Livre, String>("auteur"));
		editeurCol.setCellValueFactory(new PropertyValueFactory<Livre, String>("editeur"));
		themeCol.setCellValueFactory(new PropertyValueFactory<Livre, String>("theme"));
		langueCol.setCellValueFactory(new PropertyValueFactory<Livre, String>("langue"));
		ISBNCol.setCellValueFactory(new PropertyValueFactory<Livre, String>("ISBN"));
		nbPagesCol.setCellValueFactory(new PropertyValueFactory<Livre, Integer>("nbPages"));
		quantiteCol.setCellValueFactory(new PropertyValueFactory<Livre, Integer>("quantite"));
		etatCol.setCellValueFactory(new PropertyValueFactory<Livre, EtatLivre>("etat"));

		// création d'une colonne qui va contenir des icones pour la modification et
		// pour la suppression d'un livre
		TableColumn<Livre, String> colBtn = new TableColumn<Livre, String>("");
		// crétion d'une cellule de cette colonne
		Callback<TableColumn<Livre, String>, TableCell<Livre, String>> cellFactory = new Callback<TableColumn<Livre, String>, TableCell<Livre, String>>() {
			@Override
			public TableCell<Livre, String> call(final TableColumn<Livre, String> param) {
				final TableCell<Livre, String> cell = new TableCell<Livre, String>() {
					// création de deux icones pour la modification et pour la suppression d'un
					// livre
					private final FontAwesomeIcon fntIcon1 = new FontAwesomeIcon();
					private final FontAwesomeIcon fntIcon2 = new FontAwesomeIcon();

					{
						// en cliquant sur l'icone de suppression
						fntIcon2.setOnMouseClicked((MouseEvent event) -> {
							Livre livre = getTableView().getItems().get(getIndex());
							int id = livre.getId();
							try {
								// supression du livre en se basant sur son id
								DB.ps = DB.conn.prepareStatement("delete from livre where idLivre = ?");
								DB.ps.setInt(1, id);
								int i = DB.ps.executeUpdate();
								// si la requête est bien effectuée
								if (i > 0) {
									// supression des exemplaires du livre en se basant sur son id
									DB.ps = DB.conn.prepareStatement("delete from exemplaire where idLivre = ?");
									DB.ps.setInt(1, id);
									int j = DB.ps.executeUpdate();
									// si la requête est bien effectuée
									if (j > 0) {
										// affichage d'un message qui nous informe que la suppression est bien effectuée
										Alert alert = new Alert(Alert.AlertType.INFORMATION);
										alert.setHeaderText(null);
										alert.setContentText("Le livre est bien supprimé!");
										alert.showAndWait();
										// rafraichir la table des livres après la suppression
										rafraichirTable();
									} // sinon on va afficher un message d'erreur
									else {
										Alert alert = new Alert(Alert.AlertType.ERROR);
										alert.setHeaderText(null);
										alert.setContentText("Erreur lors de la suppression du livre!");
										alert.showAndWait();
									}
								}
							} catch (SQLException e) {
								e.printStackTrace();
							}
						});
						// en cliquant sur l'icone de modification
						fntIcon1.setOnMouseClicked((MouseEvent event) -> {
							// on va lancer l'interface de la modification du livre
							Livre livre = getTableView().getItems().get(getIndex());
							FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/ModifierLivre.fxml"));

							try {
								AnchorPane pane = loader.load();
								Pane.getChildren().setAll(pane);

							} catch (IOException e) {
								e.printStackTrace();
							}
							// recevoir le controleur de l'ajout du livre pour utiliser ses fonctions
							AjouterLivreController ajouterLivreController = loader.getController();
							// appeler la fonction qui donne la valeur true à la variable booléenne
							// modifier
							ajouterLivreController.setUpdate(true);
							// appeler la fonction qui initialise les champs du texte du livre qu'on veut
							// modifier
							// pour faciliter la modification
							ajouterLivreController.setJFXTextField(livre.getId(), livre.getTitre(), livre.getAuteur(),
									livre.getEditeur(), livre.getTheme(), livre.getLangue(), livre.getISBN(),
									livre.getNbPages(), livre.getQuantite());
						});
					}

					@Override
					public void updateItem(String item, boolean empty) {
						super.updateItem(item, empty);
						if (empty) {
							setGraphic(null);
						} else {
							fntIcon1.setGlyphName("EDIT");
							fntIcon2.setGlyphName("TRASH");
							HBox icons = new HBox(fntIcon1, fntIcon2);
							icons.setStyle("-fx-alignment:center;" + " -fx-cursor: hand ;" + "-fx-fill:black;");
							HBox.setMargin(fntIcon1, new Insets(2, 6, 2, 4));
							HBox.setMargin(fntIcon2, new Insets(2, 6, 2, 4));
							setGraphic(icons);

						}
					}
				};
				return cell;
			}
		};

		colBtn.setCellFactory(cellFactory);

		livreTab.getColumns().add(colBtn);
		livreTab.getItems().setAll(data);

	}

}
