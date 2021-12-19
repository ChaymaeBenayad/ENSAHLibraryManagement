package controller;

import java.net.URL;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ResourceBundle;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXListView;
import com.jfoenix.controls.JFXTextField;

import database.DB;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;

public class RetournerLivreController implements Initializable {

	@FXML
	private JFXTextField livreID;

	@FXML
	private JFXTextField etudiantID;

	@FXML
	private JFXButton EnterBtn;

	@FXML
	private JFXListView<String> ListeInfo;

	@FXML
	private JFXButton retournerBtn;

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		try {
			DB.connect();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	 /******************************************************
	   * Afficher les informations du livre et de l'étudiant
	   ******************************************************/
	@FXML
	void afficherInfo(ActionEvent event) {
		ObservableList<String> Info = FXCollections.observableArrayList();
		int IdLivre = Integer.parseInt(livreID.getText());
		int IdEtud = Integer.parseInt(etudiantID.getText());

		try {
			//voir si le livre est emprunté
			DB.ps = DB.conn.prepareStatement("select * from livresempruntes where livreID = ? and etudiantID = ?");
			DB.ps.setInt(1, IdLivre);
			DB.ps.setInt(2, IdEtud);
			DB.rs = DB.ps.executeQuery();
			//sinon 
			if (DB.rs.next() == false) {
				//on va afficher un message d'erreur
				Alert alert = new Alert(Alert.AlertType.ERROR);
				alert.setHeaderText(null);
				alert.setContentText("Ce livre n'est pas emprunté!");
				alert.showAndWait();
			}//sinon on va afficher les informations relatives au livre et à l'étudiant
			else {
				Integer IdLiv = IdLivre;
				Integer IdEtudiant = IdEtud;
				Timestamp DateEmprunt = DB.rs.getTimestamp("dateEmprunt");
				Date DateRetour = DB.rs.getDate("dateRetour");
				Info.add("Date et heure d'emprunt : " + DateEmprunt.toString());
				Info.add("Date de retour : " + DateRetour.toString());

				Info.add("INFORMATIONS DU LIVRE : ");
				DB.ps = DB.conn.prepareStatement("select titre,auteur,editeur,theme from livre where idLivre = ?");
				DB.ps.setInt(1, IdLiv);
				ResultSet rs1 = DB.ps.executeQuery();
				while (rs1.next()) {
					Info.add("Titre : " + rs1.getString("titre"));
					Info.add("Auteur : " + rs1.getString("auteur"));
					Info.add("Editeur : " + rs1.getString("editeur"));
					Info.add("Theme : " + rs1.getString("theme"));
				}

				Info.add("INFORMATIONS DE L'ETUDIANT : ");
				DB.ps = DB.conn.prepareStatement("select CNE,nom,prenom from etudiant where id = ?");
				DB.ps.setInt(1, IdEtudiant);
				rs1 = DB.ps.executeQuery();
				while (rs1.next()) {
					Info.add("CNE : " + rs1.getString("CNE"));
					Info.add("Nom : " + rs1.getString("nom"));
					Info.add("Prénom : " + rs1.getString("prenom"));
				}
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}

		ListeInfo.getItems().setAll(Info);
	}
	/***************************************
	         * Retourner un livre
	 ***************************************/

	@FXML
	void retournerLivre(ActionEvent event) {
		int IdLivre = Integer.parseInt(livreID.getText());
		int IdEtud = Integer.parseInt(etudiantID.getText());
		try {
			DB.ps = DB.conn.prepareStatement("select qte from livre where idLivre = ?");
			DB.ps.setInt(1, IdLivre);
			DB.rs = DB.ps.executeQuery();
			while (DB.rs.next()) {
				int quantite = DB.rs.getInt("qte");
				//voir si le livre est emprunté
				DB.ps = DB.conn.prepareStatement(
						"select exemplaireID from livresempruntes  where livreID = ? and etudiantID = ?");
				DB.ps.setInt(1, IdLivre);
				DB.ps.setInt(2, IdEtud);
				DB.rs = DB.ps.executeQuery();
				//sinon
				if (DB.rs.next() == false) {
					//on va afficher un message d'erreur
					Alert alert = new Alert(Alert.AlertType.ERROR);
					alert.setHeaderText(null);
					alert.setContentText("Ce livre n'est pas emprunté!");
					alert.showAndWait();
				}//si oui
				else {
					//la quantité va augmenté par une unité
					int idExemp = DB.rs.getInt("exemplaireID");
						DB.ps = DB.conn
								.prepareStatement("update livre set qte = ? , etat = 'DISPONIBLE' where idLivre = ?");
						DB.ps.setInt(1, quantite + 1);
						DB.ps.setInt(2, IdLivre);
						int i = DB.ps.executeUpdate();
						//si la requête est bien effectuée
						if (i > 0) {
							//changer l'état de l'exemplaire à "DISPONIBLE" 
							DB.ps = DB.conn.prepareStatement(
									"update exemplaire set etat = 'DISPONIBLE' where idExemplaire = ?");
							DB.ps.setInt(1, idExemp);
							int j = DB.ps.executeUpdate();
							//si la requête est bien effectuée
							if (j > 0) {
								//supprimer le livre de la table livresempruntes
								DB.ps = DB.conn.prepareStatement(
										"delete from livresempruntes  where livreID = ? and etudiantID = ?");
								DB.ps.setInt(1, IdLivre);
								DB.ps.setInt(2, IdEtud);
								int k = DB.ps.executeUpdate();
								//si la requête est bien effectuée
								if (k > 0) {
									//afficher un message qui nous informe que le retour est bien effectué
									Alert alert = new Alert(Alert.AlertType.INFORMATION);
									alert.setHeaderText(null);
									alert.setContentText("Le livre est bien retourné!");
									alert.showAndWait();
								} // sinon afficher un message d'erreur
								else {
									Alert alert = new Alert(Alert.AlertType.ERROR);
									alert.setHeaderText(null);
									alert.setContentText("Erreur lors du retour du livre!");
									alert.showAndWait();
								}
							}

						}


				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
