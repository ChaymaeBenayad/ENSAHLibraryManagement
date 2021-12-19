package controller;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class AdminHomeController implements Initializable {

	@FXML
	private AnchorPane rootPane;
	@FXML
	private AnchorPane RootPane;
	@FXML
	private FontAwesomeIcon btnFermer;
   
	/************************************
	 * Fermer l'application
	 ************************************/
	@FXML
	void closeWindow(MouseEvent event) {
		Stage stage = (Stage) RootPane.getScene().getWindow();
		stage.close();
	}
   /************************************
    * Lancer l'interface de l'accueil
    ************************************/
	@FXML
	void loadAccueil(ActionEvent event) {
		try {
			AnchorPane root = (AnchorPane) FXMLLoader.load(getClass().getResource("/view/AdminHome.fxml"));
			Scene scene = new Scene(root);
			Stage primaryStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
			primaryStage.setScene(scene);
			primaryStage.show();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	 /**********************************************
	    * Lancer l'interface d'affichage des livres
	    *********************************************/
	@FXML
	void loadAfficherLivres(ActionEvent event) throws IOException {
		AnchorPane pane = FXMLLoader.load(getClass().getResource("/view/afficherLivres.fxml"));
		rootPane.getChildren().setAll(pane);
	}
	 /************************************************
	    * Lancer l'interface d'affichage des étudiants
	    **********************************************/
	@FXML
	void loadAfficherEtudiants(ActionEvent event) throws IOException {
		AnchorPane pane = FXMLLoader.load(getClass().getResource("/view/RechercherEtud.fxml"));
		rootPane.getChildren().setAll(pane);

	}
	 /**********************************************
	    * Lancer l'interface d'ajout d'un livre
	    **********************************************/
	@FXML
	void loadAjouterLivre(ActionEvent event) throws IOException {
		AnchorPane pane = FXMLLoader.load(getClass().getResource("/view/ajoutLivre.fxml"));
		rootPane.getChildren().setAll(pane);
	}
	 /**********************************************
	    * Lancer l'interface d'emprunt d'un livre
	    **********************************************/
	@FXML
	void loadEmprunterLivre(ActionEvent event) throws IOException {
		AnchorPane pane = FXMLLoader.load(getClass().getResource("/view/emprunter.fxml"));
		rootPane.getChildren().setAll(pane);

	}
	 /*******************************************************
	    * Lancer l'interface d'affichage des livres empruntes
	    *******************************************************/
	@FXML
	void loadLivresEmpruntes(ActionEvent event) throws IOException {
		AnchorPane pane = FXMLLoader.load(getClass().getResource("/view/afficherLivresEmpruntes.fxml"));
		rootPane.getChildren().setAll(pane);

	}
	 /*******************************************************
	    * Lancer l'interface d'affichage des livres réservés
	    *******************************************************/
	@FXML
	void loadLivresReserves(ActionEvent event) throws IOException {
		AnchorPane pane = FXMLLoader.load(getClass().getResource("/view/LivresReserves.fxml"));
		rootPane.getChildren().setAll(pane);

	}
	 /*******************************************************
	    * Lancer l'interface de retour d'un livre
	    *******************************************************/
	@FXML
	void loadRetournerLivre(ActionEvent event) throws IOException {
		AnchorPane pane = FXMLLoader.load(getClass().getResource("/view/RetournerLivre.fxml"));
		rootPane.getChildren().setAll(pane);

	}
	/*******************************************************
	    * Lancer l'interface de déconnexion 
	    *******************************************************/
	@FXML
	void loadSeDeconnecter(ActionEvent event) {
		try {
			AnchorPane root = (AnchorPane) FXMLLoader.load(getClass().getResource("/view/SigninAdmin.fxml"));
			Scene scene = new Scene(root);
			Stage primaryStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
			primaryStage.setScene(scene);
			primaryStage.hide();
			primaryStage.show();
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {

	}

}
