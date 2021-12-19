package controller;

import java.net.URL;
import java.sql.*;
import java.util.ResourceBundle;

import database.DB;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import model.CompteId;
import model.Utilisateur;
import model.EtatEtudiant;

public class ControllerProfil implements Initializable {
	@FXML
	private Label nom;
	@FXML
	private Label prenom;
	@FXML
	private Label cne;
	@FXML
	private Label email;
	@FXML
	private Label numTel;
	@FXML
	private Label etat;

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		// ----------------Affichage les informations du compte------------------

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
				String CNE = DB.rs.getString("CNE");
				String Email = DB.rs.getString("email");
				String NumTel = DB.rs.getString("numTel");
				EtatEtudiant Etat = EtatEtudiant.valueOf(DB.rs.getString("etat"));
				String EtatEtu = Etat.toString();
				nom.setText(Nom);
				prenom.setText(Prenom);
				cne.setText(CNE);
				email.setText(Email);
				numTel.setText("+212 " + NumTel);
				if (EtatEtu.equals("ACTIF") == true) {
					etat.setText("Actif");
				} else {
					etat.setText("Bloqué(e)");
				}
			}
			DB.conn.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
