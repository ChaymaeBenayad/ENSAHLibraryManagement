package controller;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;

import database.DB;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;

public class AjouterLivreController implements Initializable {
    //initialisation de la liste qui contient les th�mes des livres
	ObservableList<String> themeList = FXCollections.observableArrayList("Algorithmique et D�veloppement Informatique",
			"Biologie", "E-Commerce", "�nerg�tique", "Environnement", "Gestion de projet", "Hydrologie et hydraulique",
			"Probabilit�s et Statistiques", "RDM et MMC", "Topographie et SIG", "S�curit� et Cryptographie",
			"Urbanisme");
	@FXML
	private FontAwesomeIcon bntRetour;
	@FXML
	private JFXTextField titre;
	@FXML
	private JFXTextField auteur;
	@FXML
	private JFXTextField editeur;
	@FXML
	private JFXComboBox<String> theme;
	@FXML
	private JFXTextField langue;
	@FXML
	private JFXTextField isbn;
	@FXML
	private JFXTextField nbPages;
	@FXML
	private JFXTextField qte;
	@FXML
	private JFXButton btnAjouter;
	@FXML
	private JFXButton btnEffacer;
	@FXML
	private AnchorPane anchorpane;
	private boolean modifier;
	int IDLivre;

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		try {
			DB.connect();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		theme.setItems(themeList);
	}

	/************************************************************************************************************
	                                                 * Ajouter un livre
	 ***********************************************************************************************************/
	@FXML
	private void ajouterLivre(ActionEvent event) {
		//d�claration des variables qui prennent comme valeurs les textes saisis par l'admin dans les champs
		String Titre = titre.getText();
		String Auteur = auteur.getText();
		String Editeur = editeur.getText();
		String Theme = theme.getSelectionModel().getSelectedItem().toString();
		String Langue = langue.getText();
		String ISBN = isbn.getText();
		int NbPages = Integer.parseInt(nbPages.getText());
		int Quantite = Integer.parseInt(qte.getText());
        //si la variable bool�enne modifier re�oit false on va ex�cuter l'op�ration de l'ajout d'un livre
		if (modifier == false) {
			try {
				//insertion des donn�es du livre dans la table livre
				DB.ps = DB.conn.prepareStatement(
						"insert into livre(titre,auteur,editeur,theme,langue,ISBN,nbPages,qte)values(?,?,?,?,?,?,?,?)");
				DB.ps.setString(1, Titre);
				DB.ps.setString(2, Auteur);
				DB.ps.setString(3, Editeur);
				DB.ps.setString(4, Theme);
				DB.ps.setString(5, Langue);
				DB.ps.setString(6, ISBN);
				DB.ps.setInt(7, NbPages);
				DB.ps.setInt(8, Quantite);

				int i = DB.ps.executeUpdate();
				//si la requ�te est bien effectu�e
				if (i > 0) {
					//on va ins�rer les donn�es dans la table exemplaire en se basant  sur l'id du livre ins�r�
					DB.ps = DB.conn.prepareStatement("select idLivre from livre where ISBN = ?");
					DB.ps.setString(1, ISBN);
					DB.rs = DB.ps.executeQuery();
					while (DB.rs.next()) {
						int livreID = DB.rs.getInt("idLivre");
						int q = 0;
						//le nombre des exemplaires d�pend de la quantit� du livre ins�r�
						while (q < Quantite) {
							DB.ps = DB.conn.prepareStatement(
									"insert into exemplaire(idLivre,titre,auteur,editeur,theme,langue,ISBN,nbPages)values(?,?,?,?,?,?,?,?)");
							DB.ps.setInt(1, livreID);
							DB.ps.setString(2, Titre);
							DB.ps.setString(3, Auteur);
							DB.ps.setString(4, Editeur);
							DB.ps.setString(5, Theme);
							DB.ps.setString(6, Langue);
							DB.ps.setString(7, ISBN);
							DB.ps.setInt(8, NbPages);
							DB.ps.executeUpdate();
							q++;
						}
						// affichage d'un message qui nous informe que l'insertion est bien effectu�e
						Alert alert = new Alert(Alert.AlertType.INFORMATION);
						alert.setHeaderText(null);
						alert.setContentText("Le livre est bien ajout�!");
						alert.showAndWait();

					}
				}//sinon on va afficher un message d'erreur 
				else {
					Alert alert = new Alert(Alert.AlertType.ERROR);
					alert.setHeaderText(null);
					alert.setContentText("Erreur lors de l'ajout du livre!");
					alert.showAndWait();
				}

			} catch (SQLException e) {
				e.printStackTrace();
			}
		} //sinon on va modifier le livre
		else {
			try {
				//modification des donn�es du livre en se basant sur son id
				DB.ps = DB.conn.prepareStatement("select qte from livre where idLivre=?");
				DB.ps.setInt(1, IDLivre);
				DB.rs = DB.ps.executeQuery();
				while (DB.rs.next()) {
					int qu = DB.rs.getInt("qte");
					DB.ps = DB.conn.prepareStatement(
							"update livre set titre=?,auteur=?,editeur=?,theme=?,langue=?,ISBN=?,nbPages=?,qte=? where idLivre=?");
					DB.ps.setString(1, Titre);
					DB.ps.setString(2, Auteur);
					DB.ps.setString(3, Editeur);
					DB.ps.setString(4, Theme);
					DB.ps.setString(5, Langue);
					DB.ps.setString(6, ISBN);
					DB.ps.setInt(7, NbPages);
					DB.ps.setInt(8, Quantite);
					DB.ps.setInt(9, IDLivre);
					int i = DB.ps.executeUpdate();
					//si la requ�te est bien effectu�e
					if (i > 0) {
						DB.ps = DB.conn.prepareStatement("select qte from livre where idLivre=?");
						DB.ps.setInt(1, IDLivre);
						DB.rs = DB.ps.executeQuery();
						while (DB.rs.next()) {
							int quantity = DB.rs.getInt("qte");
							//si la quantit� du livre apr�s la modification a augment� 
							if (quantity > qu) {
								while (quantity > qu) {
									//on va ins�rer des exemplaires dans la table exemplaire au nombre de la quantit� ajout�e
									DB.ps = DB.conn.prepareStatement(
											"insert into exemplaire(idLivre,titre,auteur,editeur,theme,langue,ISBN,nbPages)values(?,?,?,?,?,?,?,?)");
									DB.ps.setInt(1, IDLivre);
									DB.ps.setString(2, Titre);
									DB.ps.setString(3, Auteur);
									DB.ps.setString(4, Editeur);
									DB.ps.setString(5, Theme);
									DB.ps.setString(6, Langue);
									DB.ps.setString(7, ISBN);
									DB.ps.setInt(8, NbPages);
									DB.ps.executeUpdate();
									qu++;
								}
								//apr�s on va modifier les donn�es des exemplaires en se basant sur l'id du livre modifi�
								modiferExemplaire();
							}
							//sinon si la quantit� du livre apr�s la modification a diminu� 
							else if (quantity < qu) {
								int diff = qu - quantity;
								//on va supprimer des exemplaires de la table exemplaire au nombre de la quantit� retir�e
								DB.ps = DB.conn.prepareStatement(
										"delete from exemplaire where idLivre = ? and etat = 'DISPONIBLE' limit " + diff
												+ "");
								DB.ps.setInt(1, IDLivre);
								DB.ps.executeUpdate();
								//apr�s on va modifier les donn�es des exemplaires en se basant sur l'id du livre modifi�
								modiferExemplaire();
							} 
							//sinon on va modifier les donn�es des exemplaires en se basant sur l'id du livre modifi�
							else {
								modiferExemplaire();
							}
						}

					}

				}

			} catch (SQLException e) {
				e.printStackTrace();
			}

		}
	}

	/************************************************
	 * Modifier les exemplaires du livre modifi�
	 *************************************************/
	void modiferExemplaire() {
		String Titre = titre.getText();
		String Auteur = auteur.getText();
		String Editeur = editeur.getText();
		String Theme = theme.getSelectionModel().getSelectedItem().toString();
		String Langue = langue.getText();
		String ISBN = isbn.getText();
		int NbPages = Integer.parseInt(nbPages.getText());
		try {
			// modification des donn�es des exemplaires en se basant sur l'id du livre
			// modifi�
			DB.ps = DB.conn.prepareStatement(
					"update exemplaire set titre=?,auteur=?,editeur=?,theme=?,langue=?,ISBN=?,nbPages=? where idLivre=?");
			DB.ps.setString(1, Titre);
			DB.ps.setString(2, Auteur);
			DB.ps.setString(3, Editeur);
			DB.ps.setString(4, Theme);
			DB.ps.setString(5, Langue);
			DB.ps.setString(6, ISBN);
			DB.ps.setInt(7, NbPages);
			DB.ps.setInt(8, IDLivre);
			int k = DB.ps.executeUpdate();
			// si la requ�te est bien effectu�e
			if (k > 0) {
				// affichage d'un message qui nous informe que la modification est bien
				// effectu�e
				Alert alert = new Alert(Alert.AlertType.INFORMATION);
				alert.setHeaderText(null);
				alert.setContentText("Le livre est bien modifi�!");
				alert.showAndWait();
			} else {
				// sinon on va afficher un message d'erreur
				Alert alert = new Alert(Alert.AlertType.ERROR);
				alert.setHeaderText(null);
				alert.setContentText("Erreur lors de la modification du livre!");
				alert.showAndWait();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

	}
    /***********************************************************************
     * Initialiser les champs du texte pour faciliter la modification
     * *********************************************************************/
	void setJFXTextField(int id, String Titre, String Auteur, String Editeur, String Theme, String Langue, String ISBN,
			int NbPages, int quantite) {

		IDLivre = id;
		titre.setText(Titre);
		auteur.setText(Auteur);
		editeur.setText(Editeur);
		theme.setValue(Theme);
		langue.setText(Langue);
		isbn.setText(ISBN);
		nbPages.setText(String.valueOf(NbPages));
		qte.setText(String.valueOf(quantite));
	}

	/*****************************************************
	 * Changer la valeur de la variable bool�enne modifier
	 *****************************************************/
	void setUpdate(boolean b) {
		this.modifier = b;

	}
   /**********************************************
    * Vider les champs du texte
    * *******************************************/
	@FXML
	private void effacer(ActionEvent event) {
		titre.clear();
		auteur.clear();
		editeur.clear();
		theme.setValue(null);
		langue.clear();
		isbn.clear();
		nbPages.clear();
		qte.clear();
	}

	/******************************************************
	 * Retourner � l'interface de l'affichage des livres
	 ******************************************************/
	@FXML
	void PagePrecedante(MouseEvent event) throws IOException {
		AnchorPane pane = FXMLLoader.load(getClass().getResource("/view/afficherLivres.fxml"));
		anchorpane.getChildren().setAll(pane);

	}

}
