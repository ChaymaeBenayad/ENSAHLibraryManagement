package controller;

import java.net.URL;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Date;
import java.util.ResourceBundle;

import database.DB;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import model.ReservationAdmin;

public class LivresReserves implements Initializable {

	@FXML
	private TableView<ReservationAdmin> livre;
	@FXML
	private TableColumn<ReservationAdmin, Integer> IdUserCol;
	@FXML
	private TableColumn<ReservationAdmin, Integer> idCol;
	@FXML
	private TableColumn<ReservationAdmin, String> titreColumne;
	@FXML
	private TableColumn<ReservationAdmin, String> auteurCol;
	@FXML
	private TableColumn<ReservationAdmin, String> themeColumne;
	@FXML
	private TableColumn<ReservationAdmin, Date> dateReservationCol;

	public ObservableList<ReservationAdmin> data = FXCollections.observableArrayList();

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		try {
			DB.connect();
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
		initialiserCol();
		LivresReservés();
	}

	private void initialiserCol() {
		IdUserCol.setCellValueFactory(new PropertyValueFactory<ReservationAdmin, Integer>("idEtud"));
		idCol.setCellValueFactory(new PropertyValueFactory<ReservationAdmin, Integer>("idLiv"));
		titreColumne.setCellValueFactory(new PropertyValueFactory<ReservationAdmin, String>("titre"));
		auteurCol.setCellValueFactory(new PropertyValueFactory<ReservationAdmin, String>("auteur"));
		themeColumne.setCellValueFactory(new PropertyValueFactory<ReservationAdmin, String>("theme"));
		dateReservationCol.setCellValueFactory(new PropertyValueFactory<ReservationAdmin, Date>("dateReservation"));

	}
//On va afficher tous les livres réserver par chaque utilisateur
	private void LivresReservés() {
		try {
			Connection conn = DB.connect();
			String sql = "SELECT * FROM réservation";//selectionner tous les champs provenant  du tableau réservation
			DB.ps = conn.prepareStatement(sql);
			DB.rs = DB.ps.executeQuery();

			while (DB.rs.next()) {
				int idUser = DB.rs.getInt("IdUser");//id utilisateur
				int idLivre = DB.rs.getInt("IdLivre");//
				String Titre = DB.rs.getString("titre");
				String Auteur = DB.rs.getString("auteur");
				String Theme = DB.rs.getString("theme");
				Date DateReservation = DB.rs.getTimestamp("dateReservation");

				data.add(new ReservationAdmin(idUser, idLivre, Titre, Auteur, Theme, DateReservation));

			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		livre.setItems(data);//remplissage du tableau
	}

}
