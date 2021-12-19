package controller;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

import javafx.animation.TranslateTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.layout.VBox;
import javafx.util.Duration;

public class controllerForgetPass implements Initializable {

	  @FXML
	    private VBox vbox;

	    private Parent fxml;

         //Lancer l'interface se connecter
	    @FXML
	    void openSignin(ActionEvent event) {
	    	TranslateTransition t = new TranslateTransition(Duration.seconds(1), vbox);
	        t.setToX(14);
	        t.play();
	        t.setOnFinished((e) -> {
	            try {
	                fxml = FXMLLoader.load(getClass().getResource("/view/Signin.fxml"));
	                vbox.getChildren().removeAll();
	                vbox.getChildren().setAll(fxml);
	            } catch (IOException ex) {
	                Logger.getLogger(ControllerLogin.class.getName()).log(Level.SEVERE, null, ex);
	            }

	        });

	    }

		// Lancer l'interface mot de passe oublié
		@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		 TranslateTransition t = new TranslateTransition(Duration.seconds(1), vbox);
	        t.setToX(vbox.getLayoutX() * 28);
	        t.play();
	        t.setOnFinished((e) -> {
	        	 try {
	                 fxml = FXMLLoader.load(getClass().getResource("/view/ForgetPss.fxml"));
	                 vbox.getChildren().removeAll();
	                 vbox.getChildren().setAll(fxml);
	             } catch (IOException ex) {
	                 Logger.getLogger(ControllerLogin.class.getName()).log(Level.SEVERE, null, ex);
	             }
	            
	        });
	}
	


}
