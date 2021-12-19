package controller;
import java.io.IOException;
import java.net.URL;
import java.sql.*;
import java.util.ResourceBundle;

import database.DB;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;
import model.CompteId;
import model.Utilisateur;
import javafx.event.ActionEvent;

public class SeConnecter extends Home implements Initializable {
	@FXML private TextField textF;
	@FXML private PasswordField PssF;

	@Override
	public void initialize(URL arg0, ResourceBundle arg1){
		try {
			DB.connect();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	// ------------------Fonction se connecter pour l’étudiant  ------------------
	@FXML
	public void login(ActionEvent event) throws SQLException{
		String CNE= textF.getText();
	    String Password=PssF.getText();
	       
	    if(CNE.isEmpty()||Password.isEmpty()) {
	    	Alert alert= new Alert(AlertType.ERROR);
			alert.setTitle("Erreur");
			alert.setHeaderText(null);
			alert.setContentText("If faut remplir tous les champs !");
			alert.showAndWait();
		}else if(isValidCNE(CNE)==false){
	    	Alert alert= new Alert(AlertType.ERROR);
			alert.setTitle("Erreur");
			alert.setHeaderText(null);
			alert.setContentText("Votre CNE est incorrect !");
			alert.showAndWait();
	    }else if(isValidPassword(Password)==false) {
	    	Alert alert= new Alert(AlertType.ERROR);
			alert.setTitle("Erreur");
			alert.setHeaderText(null);
			alert.setContentText("Votre mot de passe est incorrect !");
			alert.showAndWait();
	  }else {
		 
		  try{
			    DB.connect();
			    /* Cette requête permet de vérifier si le nom d’utilisateur se trouve dans la
				 table etudiant.*/
			    String sql ="SELECT* FROM etudiant WHERE CNE=?";
			    DB.ps =DB.conn.prepareStatement(sql);
			    DB.ps.setString(1,CNE);
			    DB.rs=DB.ps.executeQuery();
			    if(DB.rs.next()){
			    	 String pass=DB.rs.getString("motDePass");
			    	 /*On utilise la methode  getDecodedString() pour décrypter le mot de passe et vérifier
						s'il est similaire au mot de passe utilisé par l'etudiant*/
			    	 String decryptedPass=getDecodedString(pass);
			    	 if(Password.equals(decryptedPass)){
			    		  int id=DB.rs.getInt("id");
						  Utilisateur uti= new Utilisateur(id);
						  CompteId.setUser(uti);
						  Parent homePage = FXMLLoader.load(getClass().getResource("/view/Home.fxml"));
						  Scene homepageScene = new Scene(homePage);
						  Stage appstage = (Stage) ((Node) event.getSource()).getScene().getWindow();
						  appstage.hide(); 
						  appstage.setScene(homepageScene);
						  appstage.show();   
			    	 }
			}else {
				Alert alert= new Alert(AlertType.ERROR);
				alert.setTitle("Erreur");
				alert.setHeaderText(null);
				alert.setContentText("Nom d’utilisateur ou mot de passe est incorrect");
				alert.showAndWait();
			}
			DB.conn.close();
		}catch(Exception e) {
			e.printStackTrace();
		}	
	  }
	}
	 
	  //Lancer l'interface motde passe Oublié .
	@FXML
	public void forgetPass(ActionEvent event) throws IOException  {
		  Parent homePage = FXMLLoader.load(getClass().getResource("/view/ForgetPassword.fxml"));
		  Scene homepageScene = new Scene(homePage);
		  Stage appstage = (Stage) ((Node) event.getSource()).getScene().getWindow();
		  appstage.hide(); 
		  appstage.setScene(homepageScene);
		  appstage.show();  
	}
}
