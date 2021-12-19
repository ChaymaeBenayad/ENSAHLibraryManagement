package controller;

import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class FirstPage {
	//Lancer l'interface se connecter d'etudiant
	public void ToUser(ActionEvent event) throws IOException{
		  Parent homePage = FXMLLoader.load(getClass().getResource("/view/login.fxml"));
		  Scene homepageScene = new Scene(homePage);
		  Stage appstage = (Stage) ((Node) event.getSource()).getScene().getWindow();
		  appstage.hide(); 
		  appstage.setScene(homepageScene);
		  appstage.show();  
	}
	//Lancer l'interface se connecter d'admin
	public void ToAdmin(ActionEvent event) throws IOException{
		  Parent homePage = FXMLLoader.load(getClass().getResource("/view/SigninAdmin.fxml"));
		  Scene homepageScene = new Scene(homePage);
		  Stage appstage = (Stage) ((Node) event.getSource()).getScene().getWindow();
		  appstage.hide(); 
		  appstage.setScene(homepageScene);
		  appstage.show();  
	}	
}
