package controller;

import java.net.URL;
import java.sql.*;
import java.util.ResourceBundle;

import database.DB;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;
import model.CompteId;
import model.Utilisateur;

public class SeConnecterAdmin extends Home implements Initializable {
	@FXML
	private TextField nom;

	@FXML
	private PasswordField PssF;

	// ------------------Fonction se connecter pour l'admin ------------------
	@FXML
	public void login(ActionEvent event) throws SQLException {

		String username = nom.getText();
		String Password = PssF.getText();

		if (username.isEmpty() || Password.isEmpty()) {
			Alert alert = new Alert(AlertType.ERROR);
			alert.setTitle("Erreur");
			alert.setHeaderText(null);
			alert.setContentText("If faut remplir tous les champs");
			alert.showAndWait();
		} else if (isValidName(username) == false) {
			Alert alert = new Alert(AlertType.ERROR);
			alert.setTitle("Erreur");
			alert.setHeaderText(null);
			alert.setContentText("Votre nom d’utilisateur est incorrecte");
			alert.showAndWait();
		} else if (isValidPassword(Password) == false) {
			Alert alert = new Alert(AlertType.ERROR);
			alert.setTitle("Erreur");
			alert.setHeaderText(null);
			alert.setContentText("Votre mot de passe est incorrecte");
			alert.showAndWait();
		} else
			try {
				Connection conn = DB.connect();
				/*
				 * Cette requête permet de vérifier si le nom d’utilisateur se trouve dans la
				 * table admin.
				 */
				String sql = "SELECT* FROM admin WHERE nomAdmin=?";
				DB.ps = conn.prepareStatement(sql);
				DB.ps.setString(1, username);
				DB.rs = DB.ps.executeQuery();
				if (DB.rs.next()) {
					/*
					 * On utilise la methode getDecodedString() pour décrypter le mot de passe et
					 * vérifier s'il est similaire au mot de passe utilisé par l'admin
					 */
					String pass = DB.rs.getString("motDePass");
					String decryptedPass = getDecodedString(pass);
					if (Password.equals(decryptedPass)) {
						int id = DB.rs.getInt("id");
						Utilisateur uti = new Utilisateur(id);
						CompteId.setUser(uti);
						Parent homePage = FXMLLoader.load(getClass().getResource("/view/AdminHome.fxml"));
						Scene homepageScene = new Scene(homePage);
						Stage appstage = (Stage) ((Node) event.getSource()).getScene().getWindow();
						appstage.hide();
						appstage.setScene(homepageScene);
						appstage.show();
					}
				} else {
					Alert alert = new Alert(AlertType.ERROR);
					alert.setTitle("Erreur");
					alert.setHeaderText(null);
					alert.setContentText("Nom d’utilisateur ou mot de passe est incorrecte");
					alert.showAndWait();
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
	}

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {

	}

}
