package controller;

import java.net.URL;
import java.sql.SQLException;
import java.time.ZoneId;
import java.util.ResourceBundle;

import javafx.fxml.Initializable;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;

import database.DB;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.DatePicker;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import model.EtatEtudiant;

public class emprunterController implements Initializable {

	@FXML
	private AnchorPane anchorpane;
	@FXML
	private JFXTextField etudiantId;
	@FXML
	private JFXTextField nom;
	@FXML
	private JFXTextField prenom;
	@FXML
	private JFXTextField etat;
	@FXML
	private JFXTextField livreId;
	@FXML
	private JFXTextField titre;
	@FXML
	private JFXTextField auteur;
	@FXML
	private DatePicker dateRetour;
	@FXML
	private JFXButton emprunterBtn;
	@FXML
	private JFXButton EffacerBtn;

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		try {
			DB.connect();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
  /******************************************************
   * Afficher le nom, le pr�nom et l'�tat de l'�tudiant
   ******************************************************/
	@FXML
	void afficherNomPrenom(KeyEvent event) {
		//si on clique sur la cl� "Enter"
		if (event.getCode() == KeyCode.ENTER) {
			int IdEtud = Integer.parseInt(etudiantId.getText());

			try {
				//on va s�lectionner le nom, le pr�nom et l'�tat de la table etudiant en se basant sur son id
				DB.ps = DB.conn.prepareStatement("select nom,prenom,etat from etudiant where id = ?");
				DB.ps.setInt(1, IdEtud);
				DB.rs = DB.ps.executeQuery();
                //si on ne les trouve pas dans la table
				if (DB.rs.next() == false) {
					//on va afficher un message d'erreur
					Alert alert = new Alert(Alert.AlertType.ERROR);
					alert.setHeaderText(null);
					alert.setContentText("L'ID de cet �tudiant n'existe pas!");
					alert.showAndWait();
				} //sinon on va initialiser les champs du texte par les valeurs s�lectionn�es
				else {
					String Nom = DB.rs.getString("nom");
					nom.setText(Nom.trim());
					String Prenom = DB.rs.getString("prenom");
					prenom.setText(Prenom.trim());
					EtatEtudiant Etat = EtatEtudiant.valueOf(DB.rs.getString("etat"));
					String EtatEtud = Etat.toString();
					if (EtatEtud.equals("ACTIF") == true) {
						etat.setText("Actif");
					} else {
						etat.setText("Bloqu�");
					}
				}

			} catch (SQLException e) {
				e.printStackTrace();
			}

		}
	}
	 /******************************************************
	   * Afficher le tite et l'auteur du livre
	   ******************************************************/

	@FXML
	void afficherTitre(KeyEvent event) {
		//si on clique sur la cl� "Enter"
		if (event.getCode() == KeyCode.ENTER) {
			int IdLivre = Integer.parseInt(livreId.getText());
			try {
				//on va s�lectionner le titre et l'auteur de la table livre en se basant sur son id
				DB.ps = DB.conn.prepareStatement("select titre,auteur from livre where idLivre = ?");
				DB.ps.setInt(1, IdLivre);
				DB.rs = DB.ps.executeQuery();
				//si on ne les trouve pas dans la table
				if (DB.rs.next() == false) {
					Alert alert = new Alert(Alert.AlertType.ERROR);
					alert.setHeaderText(null);
					alert.setContentText("L'ID de cet livre n'existe pas!");
					alert.showAndWait();
				} //sinon on va initialiser les champs du texte par les valeurs s�lectionn�es
				else {

					String Titre = DB.rs.getString("titre");
					titre.setText(Titre.trim());
					String Auteur = DB.rs.getString("auteur");
					auteur.setText(Auteur.trim());
				}

			} catch (SQLException e) {
				e.printStackTrace();
			}

		}
	}
	
/******************************************
           * Emprunter un livre
 *****************************************/
	@FXML
	void EmprunterLivre(ActionEvent event) {

		int IdEtud = Integer.parseInt(etudiantId.getText());
		int IdLivre = Integer.parseInt(livreId.getText());

		java.util.Date date = java.util.Date
				.from(dateRetour.getValue().atStartOfDay(ZoneId.systemDefault()).toInstant());
		java.sql.Date DateRetour = new java.sql.Date(date.getTime());

		try {
			//voir si le livre est emprunt�
			DB.ps = DB.conn
					.prepareStatement("select exemplaireID from livresempruntes where livreID = ? and etudiantID = ?");
			DB.ps.setInt(1, IdLivre);
			DB.ps.setInt(2, IdEtud);
			DB.rs = DB.ps.executeQuery();
			//si oui
			if (DB.rs.next()) {
				//on va afficher un message d'erreur
				Alert alert = new Alert(Alert.AlertType.ERROR);
				alert.setHeaderText(null);
				alert.setContentText("Ce livre est d�j� emprunt�!\nVeuillez choisir un autre livre");
				alert.showAndWait();
			} //sinon
			else {
				//voir si le livre est r�serv� par l'�tudiant
				DB.ps = DB.conn.prepareStatement(
						"select IdExemplaire,titre,auteur,theme from r�servation where IdLivre = ? and IdUser = ?");
				DB.ps.setInt(1, IdLivre);
				DB.ps.setInt(2, IdEtud);
				DB.rs = DB.ps.executeQuery();
				//sinon
				if (DB.rs.next() == false) {
					Alert alert = new Alert(Alert.AlertType.ERROR);
					alert.setHeaderText(null);
					alert.setContentText("Vous n'avez pas r�serv� ce livre !");
					alert.showAndWait();
				} //si oui
				else {
					int ID = DB.rs.getInt("IdExemplaire");
					String Title = DB.rs.getString("titre");
					String Auteur = DB.rs.getString("auteur");
				    String Theme = DB.rs.getString("theme");
				  //on va ins�rer les donn�es entr�es dans les champs du texte dans la table livresempruntes
					DB.ps = DB.conn.prepareStatement(
							"insert into livresempruntes(etudiantID,livreID,exemplaireID,titre,auteur,theme,dateRetour)values(?,?,?,?,?,?,?)");
					DB.ps.setInt(1, IdEtud);
					DB.ps.setInt(2, IdLivre);
					DB.ps.setInt(3, ID);
					DB.ps.setString(4, Title);
					DB.ps.setString(5, Auteur);
					DB.ps.setString(6, Theme);
					DB.ps.setDate(7, DateRetour);

					int i = DB.ps.executeUpdate();
					//si la requ�te est bien effectu�e
					if (i > 0) {
						//la quantit� va diminuer par une unit�
						DB.ps = DB.conn.prepareStatement("update livre set qte = qte-1 where idLivre = ?");
						DB.ps.setInt(1, IdLivre);
						int j = DB.ps.executeUpdate();
						//si la requ�te est bien effectu�e
						if (j > 0) {
							//changer l'�tat de l'exemplaire � "EMPRUNTE" 
							DB.ps = DB.conn
									.prepareStatement("update exemplaire set etat = 'EMPRUNTE' where idExemplaire = ?");
							DB.ps.setInt(1, ID);
							int k = DB.ps.executeUpdate();
							//si la requ�te est bien effectu�e
							if (k > 0) {
								//supprimer le livre de la table de r�servation
								DB.ps = DB.conn.prepareStatement("delete from r�servation  where IdExemplaire = ?");
								DB.ps.setInt(1, ID);
								int l = DB.ps.executeUpdate();
								//si la requ�te est bien effectu�e
								if (l > 0) {
									//afficher un message qui nous informe que l'emprunt est bien effectu�
									Alert alert = new Alert(Alert.AlertType.INFORMATION);
									alert.setHeaderText(null);
									alert.setContentText("Le livre " + Title + "  est bien emprunt�!");
									alert.showAndWait();
								} // sinon afficher un message d'erreur
								else {
									Alert alert = new Alert(Alert.AlertType.ERROR);
									alert.setHeaderText(null);
									alert.setContentText("Erreur lors de l'emprunt du livre!");
									alert.showAndWait();
								}

							}
						}

					}
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

	}
/*******************************************
              * Vider les champs	
 *******************************************/
	@FXML
	void EffacerEmprunt(ActionEvent event) {
		livreId.clear();
		titre.clear();
		auteur.clear();
		etudiantId.clear();
		nom.clear();
		prenom.clear();
		etat.clear();
		dateRetour.setValue(null);
		

	}

}
