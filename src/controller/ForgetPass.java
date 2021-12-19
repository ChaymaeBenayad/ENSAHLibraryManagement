package controller;

import java.net.URL;
import java.sql.*;
import java.util.ResourceBundle;

import database.DB;
import javafx.event.ActionEvent;
import javafx.fxml.*;
import javafx.scene.control.*;
import javafx.scene.control.Alert.AlertType;

public class ForgetPass extends Home implements Initializable {
	@FXML private TextField email;
	@FXML private PasswordField Pass;
	@FXML private PasswordField ConfPass;
	
	// ------------------Fonction pour modifier le mot de passe oublie ------------------
	@FXML
	public void ForgetPassword(ActionEvent event) throws SQLException{
		String Email=email.getText();
		String pass =Pass.getText();
		String confpass=ConfPass.getText();
		
		    if(Email.isEmpty()||pass.isEmpty()||confpass.isEmpty()) {
		    	Alert alert= new Alert(AlertType.ERROR);
				alert.setTitle("Erreur");
				alert.setHeaderText(null);
				alert.setContentText("If faut remplir tous les champ !");
				alert.showAndWait();
			}else if(isValidEmail(Email)==false){
		    	Alert alert= new Alert(AlertType.ERROR);
				alert.setTitle("Erreur");
				alert.setHeaderText(null);
				alert.setContentText(" Votre email est incorrect !");
				alert.showAndWait();
		    }else if(isValidPassword(pass)==false) {
		    	Alert alert= new Alert(AlertType.ERROR);
				alert.setTitle("Erreur");
				alert.setHeaderText(null);
				alert.setContentText("Votre mot de passe est incorrect!");
				alert.showAndWait();
		    }else if(pass.equals(confpass)==false) {
		    	Alert alert= new Alert(AlertType.ERROR);
				alert.setTitle("Erreur");
				alert.setHeaderText(null);
				alert.setContentText("Votre mot de passe n'est pas confirmé");
				alert.showAndWait();
		    }else{
		    	  String cryptedPass=getEncodedString(pass);
		    	try {
				    Connection conn=DB.connect();
					/*
					 * Cette requête permet de modifier le mot de passe.
					 */
					String sql ="UPDATE etudiant SET motDePass=? WHERE email =?";
					DB.ps =conn.prepareStatement(sql);
					DB.ps.setString(1,cryptedPass);
					DB.ps.setString(2,Email);
					 int x = DB.ps.executeUpdate();
					 if(x>0) {
						    Alert alert= new Alert(AlertType.INFORMATION);
							alert.setTitle("Information");
							alert.setHeaderText(null);
							alert.setContentText("Votre changement est fait par succès ");
							alert.showAndWait();
					 }else {
						    System.out.println("Votre changement n'est pas fait par succès ");}
					        DB.conn.close();
				}catch(Exception e) {
					e.printStackTrace();
				}
	     }
	} 
	  
      public void vider() {
    	  email.clear();
    	  Pass.clear();
    	  ConfPass.clear();
      }
	
	 @Override
		public void initialize(URL arg0, ResourceBundle arg1) {
		
		}
}
