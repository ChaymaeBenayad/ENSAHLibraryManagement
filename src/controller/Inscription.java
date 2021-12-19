package controller;
import java.io.IOException;

import java.net.URL;
import java.sql.*;
import java.util.*;
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

public class Inscription extends Home implements Initializable {

	@FXML private TextField nom;
	@FXML private TextField prenom;
	@FXML private TextField CNE1;
	@FXML private TextField email;
	@FXML private TextField Numtel;
	@FXML private PasswordField Pss;
	@FXML private PasswordField ComfPss;
	
	// ------------------Inscription de l'etudiant ------------------
	@FXML
	public void Signin(ActionEvent event) throws SQLException {
		String Nom= nom.getText();
		String Prenom= prenom.getText();
		String cne= CNE1.getText();
		String Email= email.getText();
		String numTel= Numtel.getText();
	    String Password1=Pss.getText();
	    String ComfPassword=ComfPss.getText();
	    
	    if(Nom.isEmpty()||Prenom.isEmpty()||Prenom.isEmpty()||cne.isEmpty()||Email.isEmpty()||numTel.isEmpty()|Password1.isEmpty()||ComfPassword.isEmpty()) {
	    	Alert alert= new Alert(AlertType.ERROR);
			alert.setTitle("Erreur");
			alert.setHeaderText(null);
			alert.setContentText("If faut remplir tous les champs");
			alert.showAndWait();
	    }else if(isValidName(Prenom)==false || isValidName(Nom)==false){
	    	Alert alert= new Alert(AlertType.ERROR);
			alert.setTitle("Erreur");
			alert.setHeaderText(null);
			alert.setContentText("Prenom ou Nom n'est pas valide");
			alert.showAndWait();
	    }else if(isValidCNE(cne)==false){
	    	Alert alert= new Alert(AlertType.ERROR);
			alert.setTitle("Erreur");
			alert.setHeaderText(null);
			alert.setContentText("Votre CNE n'est pas valide");
			alert.showAndWait();
	    }else if(isValidEmail(Email)==false){
	    	Alert alert= new Alert(AlertType.ERROR);
			alert.setTitle("Erreur");
			alert.setHeaderText(null);
			alert.setContentText("Email est n'est pas valide");
			alert.showAndWait();
	    }else if(isvalidatePhoneNumber(numTel)==false) {
	    	Alert alert= new Alert(AlertType.ERROR);
			alert.setTitle("Erreur");
			alert.setHeaderText(null);
			alert.setContentText("Votre numero de tel n'est pas valide");
			alert.showAndWait();
	    }else if(isValidPassword(Password1)==false) {
	    	Alert alert= new Alert(AlertType.ERROR);
			alert.setTitle("Erreur");
			alert.setHeaderText(null);
			alert.setContentText("Votre mot de passe n'est pas valide");
			alert.showAndWait();
	    }else if(Password1.equals(ComfPassword)==false) {
	    	Alert alert= new Alert(AlertType.ERROR);
			alert.setTitle("Erreur");
			alert.setHeaderText(null);
			alert.setContentText("Confirmer votre mot de passe");
			alert.showAndWait();
	    }else{			
			  String cryptedPass=getEncodedString(Password1);
	    	try {
			    Connection conn=DB.connect();
			    /*
				 * Cette requête permet d'insérer les information d'un etudiant dans la table etudiant s'ils sont
				 * s’ils sont valide.
				 */
				String sql ="insert into etudiant (nom,prenom,CNE,email,numTel,motDePass) value (?,?,?,?,?,?)";
				DB.ps =conn.prepareStatement(sql);
				DB.ps.setString(1,Nom);
				DB.ps.setString(2,Prenom);
				DB.ps.setString(3,cne);
				DB.ps.setString(4,Email);
				DB.ps.setString(5,numTel);
				DB.ps.setString(6,cryptedPass);
				 int x = DB.ps.executeUpdate();
				 if(x>0){
					    Alert alert= new Alert(AlertType.INFORMATION);
						alert.setTitle("Information");
						alert.setHeaderText(null);
						alert.setContentText("Votre inscription est fait par ");
						alert.showAndWait();
				 }else {
					 System.out.println("Insertion n'est pas fait");
				 }
				      DB.conn.close();
			}catch(Exception e) {
				e.printStackTrace();
			}	
	    }
	}
	  
	 //Lancer l'interface se connecter
	@FXML
		public void Orlogin(ActionEvent event) throws SQLException, IOException {
		    Parent homePage = FXMLLoader.load(getClass().getResource("/view/SeConnecter.fxml"));
			Scene homepageScene = new Scene(homePage);
			Stage appstage = (Stage) ((Node) event.getSource()).getScene().getWindow();
			appstage.hide(); 
			appstage.setScene(homepageScene);
			appstage.show();  
	  }
	  
		@Override
		public void initialize(URL arg0, ResourceBundle arg1) {
			// TODO Auto-generated method stub
		
		}
}
