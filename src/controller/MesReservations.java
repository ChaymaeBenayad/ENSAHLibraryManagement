package controller;

import java.net.URL;
import java.sql.Connection;
import java.util.Date;
import java.sql.SQLException;
import java.util.ResourceBundle;

import com.jfoenix.controls.JFXButton;

import database.DB;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.util.Callback;

import model.CompteId;
import model.Livre;
import model.Reservation;

public class MesReservations implements Initializable {
	@FXML
	private TableView<Reservation> livre;
	@FXML
	private TableColumn<Livre, Integer> idCol;
	@FXML
	private TableColumn<Livre, String> titreColumne;
	@FXML
	private TableColumn<Livre, String> auteurCol;
	@FXML
	private TableColumn<Livre, String> themeColumne;
	@FXML
	private TableColumn<Reservation, Date> dateReservationCol;

	public ObservableList<Reservation> data = FXCollections.observableArrayList();

	int ID = CompteId.getUser().getId();

	private void rafraichirTable() {
		try {
			data.clear();
			Connection conn = DB.connect();
			String sql = "SELECT * FROM réservation Where IdUser=" + ID + ""; //selectionné tous les champs provenant du table réservation selon l'utilisateur connecter
			DB.ps = conn.prepareStatement(sql);
			DB.rs = DB.ps.executeQuery();

			while (DB.rs.next()) {
				int idLivre = DB.rs.getInt("IdLivre");
				String Titre = DB.rs.getString("titre");
				String Auteur = DB.rs.getString("auteur");
				String Theme = DB.rs.getString("theme");
				Date DateReservation = DB.rs.getTimestamp("dateReservation");

				data.add(new Reservation(idLivre, Titre, Auteur, Theme, DateReservation));

			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		livre.setItems(data);
	}

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		consulterMesReservations();
	}

	private void consulterMesReservations() {//Chaque utilisateur peut consulter les réservations qu'il a deja fait et il peut faire l'annulation d'une réservation

		rafraichirTable();
		idCol.setCellValueFactory(new PropertyValueFactory<Livre, Integer>("id"));
		titreColumne.setCellValueFactory(new PropertyValueFactory<Livre, String>("titre"));
		auteurCol.setCellValueFactory(new PropertyValueFactory<Livre, String>("auteur"));
		themeColumne.setCellValueFactory(new PropertyValueFactory<Livre, String>("theme"));
		dateReservationCol.setCellValueFactory(new PropertyValueFactory<Reservation, Date>("dateReservation"));
    //sur chaque ligne de tableau on va ajouter  button "Annuler" pour faire l'annulation d'une réservation
		TableColumn<Reservation, String> colBtnAnnuler = new TableColumn<Reservation, String>("Annuler");
		Callback<TableColumn<Reservation, String>, TableCell<Reservation, String>> cellFactory = new Callback<TableColumn<Reservation, String>, TableCell<Reservation, String>>() {
			@Override
			public TableCell<Reservation, String> call(final TableColumn<Reservation, String> param) {
				final TableCell<Reservation, String> cell = new TableCell<Reservation, String>() {
                    
					private final JFXButton btn = new JFXButton("Annuler");

					{
						btn.setOnAction((ActionEvent event) -> {//L'action produite si l'utilisateur a cliqué sur le button "Annuler"
							Livre l = getTableView().getItems().get(getIndex());
							int id = l.getId();//retourner l'id de livre qui se trouve dans  la ligne de tableau  d'où l'utilisateur a cliqué button"Annuler"

							try {
								Connection conn = DB.connect();
								String sql = "select IdExemplaire from réservation where IdLivre =" + id
										+ " and IdUser=" + ID + "";//Selectionné idUser , IdExemplaire provenant du table "réservation" dont IdLivre =id								                                   // a cliqué button"Annuler"
								DB.ps = conn.prepareStatement(sql);
								DB.rs = DB.ps.executeQuery();
								while (DB.rs.next()) {
									int IDExemp = DB.rs.getInt("IdExemplaire");
									String sql1 = "update exemplaire set etat = 'DISPONIBLE' where idExemplaire = ?";//modifier l'etat de livre de RESERVER à DISPONIBLE 
									DB.ps = conn.prepareStatement(sql1);
									DB.ps.setInt(1, IDExemp);
									int i = DB.ps.executeUpdate();
									if (i > 0) {
										String sql2 = "delete from réservation where IdLivre =" + id + " and IdUser="
												+ ID + "";//supprimer dans  la table "réservation" la ligne dont IdLivre=id
										DB.ps = conn.prepareStatement(sql2);
										int j = DB.ps.executeUpdate();
										if (j > 0) {
											Alert alert = new Alert(Alert.AlertType.INFORMATION);
											alert.setHeaderText(null);
											alert.setContentText("Votre réservation est annulée avec succés!");
											alert.showAndWait();
											rafraichirTable();//faire appel à cette methode pour afficher la nouvelle table
										} else {
											Alert alert = new Alert(Alert.AlertType.ERROR);
											alert.setHeaderText(null);
											alert.setContentText("Erreur lors de l'annulation de la réservation!");
											alert.showAndWait();
										}

									}

								}
							} catch (SQLException e) {
								e.printStackTrace();
							}
						});

					}

					@Override
					public void updateItem(String item, boolean empty) {
						super.updateItem(item, empty);
						if (empty) {
							setGraphic(null);
						} else {
							setGraphic(btn);
						}
					}

				};

				return cell;

			}

		};

		colBtnAnnuler.setCellFactory(cellFactory);

		livre.getColumns().add(colBtnAnnuler);
		livre.setItems(data);

	}
}
