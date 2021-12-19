package controller;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.util.Base64;
import java.util.ResourceBundle;

import database.DB;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import model.CompteId;
import model.Utilisateur;

public class Home implements Initializable {
	@FXML
	AnchorPane rootPane;
	@FXML
	private Label username;
	@FXML
	private AnchorPane anchorpane;

	@FXML
	public void quitter(MouseEvent event) {
		Stage stage = (Stage) anchorpane.getScene().getWindow();
		stage.close();

	}

	// Lancer l'interface d'accueil.
	public void acceuil() throws IOException {
		AnchorPane pane = FXMLLoader.load(getClass().getResource("/view/accueil.fxml"));
		rootPane.getChildren().setAll(pane);
	}

	// Lancer l'interface du compte .
	public void showProfil() throws IOException {
		AnchorPane pane = FXMLLoader.load(getClass().getResource("/view/ControllerProfil.fxml"));
		rootPane.getChildren().setAll(pane);
	}

	// Lancer l'interface pour changer le mot de passe.
	public void updatePass() throws IOException {
		AnchorPane pane = FXMLLoader.load(getClass().getResource("/view/UpdatePassword.fxml"));
		rootPane.getChildren().setAll(pane);

	}

	// Lancer l'interface pour consulter les livres réservés.
	public void MesReserv() throws IOException {
		AnchorPane pane = FXMLLoader.load(getClass().getResource("/view/MesReservations.fxml"));
		rootPane.getChildren().setAll(pane);

	}

	// Lancer l'interface pour consulter les livres ampruntés.
	public void Mesemprun() throws IOException {
		AnchorPane pane = FXMLLoader.load(getClass().getResource("/view/MesLivresEmpruntes.fxml"));
		rootPane.getChildren().setAll(pane);

	}

	// Lancer l'interface pour réserver un livre .
	public void Reserver() throws IOException {
		AnchorPane pane = FXMLLoader.load(getClass().getResource("/view/ReservationLivre.fxml"));
		rootPane.getChildren().setAll(pane);

	}

	// Lancer l'interface pour se déconnecter.
	public void Deconnecter(ActionEvent event) throws IOException {
		Parent homePage = FXMLLoader.load(getClass().getResource("/view/login.fxml"));
		Scene homepageScene = new Scene(homePage);
		Stage appstage = (Stage) ((Node) event.getSource()).getScene().getWindow();
		appstage.hide();
		appstage.setScene(homepageScene);
		appstage.show();
	}

	// Permet de vérifier que l'email est valide
	public static boolean isValidEmail(String email) {
		String regex = "^[\\w-_\\.+]*[\\w-_\\.]\\@([\\w]+\\.)+[\\w]+[\\w]$";
		return email.matches(regex);
	}

	// Permet de vérifier que c'est un numéro de téléphone valide (contient 10
	// nombres)
	public static boolean isvalidatePhoneNumber(String phoneNo) {
		if (phoneNo.matches("\\d{10}"))
			return true;
		else
			return false;
	}

	// Permet de vérifier que le nom ou le prenom est valide (contient que des
	// Lettres)
	public static boolean isValidName(String firstName) {
		return firstName.matches("[A-Z][a-z]*");
	}

	// Permet de vérifier que c'est mot de passe valide (contient des Lettres et des
	// nombres)
	public static boolean isValidPassword(String password) {
		String regex = "^(?=.*[0-9])" + "(?=.*[a-z])(?=.*[A-Z])" + "(?=\\S+$).{8,20}$";// 8 min 20 max
		if (password.matches(regex))
			return true;
		else
			return false;
	}

	// Permet de vérifier que Le CNE est valide (contient une lettre majuscule et 9
	// nombres)
	public static boolean isValidCNE(String CNE) {
		String regex = "^(?=.*[0-9])" + "(?=.*[A-Z])" + "(?=\\S+$).{10}$";// 10 max
		if (CNE.matches(regex))
			return true;
		else
			return false;
	}
	// Décrypter le mot de passe entrée
	public static String getDecodedString(String creptedPass) {
		return new String(Base64.getMimeDecoder().decode(creptedPass));
	}
	
	//Crypter le mot de passe entrée pour assurer la sécurité des données .
	public static String getEncodedString(String password) {
		  return Base64.getEncoder().encodeToString(password.getBytes());
		  }
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		AnchorPane pane;
		try {
			pane = FXMLLoader.load(getClass().getResource("/view/accueil.fxml"));
			rootPane.getChildren().setAll(pane);
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		// TODO Auto-generated method stub
		Utilisateur etu = CompteId.getUser();
		int ID = etu.getId();

		try {
			Connection conn = DB.connect();
			String sql = "SELECT* FROM etudiant WHERE id=?";
			DB.ps = conn.prepareStatement(sql);
			DB.ps.setInt(1, ID);
			DB.rs = DB.ps.executeQuery();
			while (DB.rs.next()) {
				String Nom = DB.rs.getString("nom");
				String Prenom = DB.rs.getString("prenom");
				String Username = Nom + " " + Prenom;
				username.setText(Username);
			}
			DB.conn.close();
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

}
