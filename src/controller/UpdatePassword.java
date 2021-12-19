package controller;
import java.net.URL;
import java.sql.*;
import java.util.ResourceBundle;
import com.jfoenix.controls.JFXPasswordField;
import database.DB;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.Alert.AlertType;
import model.CompteId;
import model.Utilisateur;

public class UpdatePassword extends Home implements Initializable{
	@FXML
    private JFXPasswordField lastPass;

    @FXML
    private JFXPasswordField NewPass;

    @FXML
    private JFXPasswordField ConfPass;

	// ------------------Fonction pour modifier le mot de passe ------------------

	public void update() {
		String lastpass = lastPass.getText();
		String newpass = NewPass.getText();
		String confpass = ConfPass.getText();

		Utilisateur etu = CompteId.getUser();
		int ID = etu.getId();

		if (lastpass.isEmpty() || newpass.isEmpty() || confpass.isEmpty()) {
			Alert alert = new Alert(AlertType.ERROR);
			alert.setTitle("Erreur");
			alert.setHeaderText(null);
			alert.setContentText("If faut remplir tous les champs !");
			alert.showAndWait();
		} else if (getMypass().equals(lastpass) == false) {
			Alert alert = new Alert(AlertType.ERROR);
			alert.setTitle("Erreur");
			alert.setHeaderText(null);
			alert.setContentText("Verifier votre ancien mot de passe !");
			alert.showAndWait();
		} else if (isValidPassword(newpass) == false) {
			Alert alert = new Alert(AlertType.ERROR);
			alert.setTitle("Erreur");
			alert.setHeaderText(null);
			alert.setContentText("Votre nouveau mot de passe n'est pas valide");
			alert.showAndWait();
		} else if (newpass.equals(confpass) == false) {
			Alert alert = new Alert(AlertType.ERROR);
			alert.setTitle("Erreur");
			alert.setHeaderText(null);
			alert.setContentText("votre nouveau mot de passe n'est pas confirmé ");
			alert.showAndWait();
		} else {
			 String cryptedPass=getEncodedString(newpass);
			try {
				Connection conn = DB.connect();
				/*
				 * Cette requête permet de modifier le mot de passe.
				 */
				
				String sql2 = "UPDATE etudiant SET motDePass=? WHERE id =?";
				DB.ps = conn.prepareStatement(sql2);
				DB.ps.setString(1, cryptedPass);
				DB.ps.setInt(2, ID);
				int x = DB.ps.executeUpdate();
				if (x > 0) {
					Alert alert = new Alert(AlertType.INFORMATION);
					alert.setTitle("Information");
					alert.setHeaderText(null);
					alert.setContentText("Votre changement de mot de passe est fait par succès ");
					alert.showAndWait();
				} else {
					System.out.println("Votre changement n'est pas fait par succès ");
				}

			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
	
	// Permet de retourner le mot de passe d'etudiant
		public String getMypass() {
			  Utilisateur etu =CompteId.getUser();
			  int ID= etu.getId();
			  
			  String decryptedPass=null;
			try{
				 PreparedStatement ps=null;
			     ResultSet rs=null;
			     Connection conn=DB.connect();
				 String sql ="SELECT motDePass FROM etudiant WHERE id=?";
				 ps =conn.prepareStatement(sql);
				 ps.setInt(1,ID);
				 rs =ps.executeQuery();
				 if(rs.next()){
		         decryptedPass=getDecodedString(rs.getString("motDePass"));
				 }else {
					 System.out.println("Mot de passe n'est pas retourné");
				 }
			}catch(Exception e) {
				e.printStackTrace();
			} 
			return decryptedPass;
		}
		

		  
	// Vider les champs saisis.
          public void vider() {
        	  lastPass.clear();
        	  NewPass.clear();
        	  ConfPass.clear();
          }
   	@Override
 	public void initialize(URL arg0, ResourceBundle arg1) {
 		
 	}
}
