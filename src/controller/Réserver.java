package controller;

import java.sql.SQLException;
import java.net.URL;
import java.util.ResourceBundle;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;

import database.DB;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import model.CompteId;
import model.Livre;

public class R�server implements Initializable {

	@FXML
	private JFXTextField labelCode;
	@FXML
	private JFXTextField labelTitre;
	@FXML
	private JFXComboBox<String> Theme;
	@FXML
	private TextField LabelRechercher;

	@FXML
	private TableView<Livre> livre;
	@FXML
	private TableColumn<Livre, Integer> IdLivre;
	@FXML
	private TableColumn<Livre, String> titre;
	@FXML
	private TableColumn<Livre, String> auteur;
	@FXML
	private TableColumn<Livre, String> editeur;
	@FXML
	private TableColumn<Livre, String> theme;
	@FXML
	private TableColumn<Livre, String> langue;
	@FXML
	private TableColumn<Livre, String> ISBN;
	@FXML
	private TableColumn<Livre, Integer> nbPages;
	@FXML
	private JFXButton btnvider;
	@FXML
	private JFXButton btnR�server;
	@FXML
	private TextField filterField;

	public ObservableList<Livre> data = FXCollections.observableArrayList();

	public ObservableList<String> themeList = FXCollections.observableArrayList(
			"Algorithmique et D�veloppement Informatique", "Biologie", "E-Commerce", "�nerg�tique", "Environnement",
			"Gestion de projet", "Hydrologie et hydraulique", "Probabilit�s et Statistiques", "RDM et MMC",
			"Topographie et SIG", "S�curit� et Cryptographie", "Urbanisme");

	final int NombreReserv = 3;

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		try {
			DB.connect();
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
		Theme.setItems(themeList);
		initialiserCol();
		afficherLivres();
		afficherLivreSelectionne();

	}

	private void initialiserCol() {
		IdLivre.setCellValueFactory(new PropertyValueFactory<Livre, Integer>("id"));
		titre.setCellValueFactory(new PropertyValueFactory<Livre, String>("titre"));
		auteur.setCellValueFactory(new PropertyValueFactory<Livre, String>("auteur"));
		editeur.setCellValueFactory(new PropertyValueFactory<Livre, String>("editeur"));
		theme.setCellValueFactory(new PropertyValueFactory<Livre, String>("theme"));
		langue.setCellValueFactory(new PropertyValueFactory<Livre, String>("langue"));
		ISBN.setCellValueFactory(new PropertyValueFactory<Livre, String>("ISBN"));
		nbPages.setCellValueFactory(new PropertyValueFactory<Livre, Integer>("nbPages"));

	}

	private void afficherLivres() {
		try {
			String sql = "SELECT * FROM livre ORDER BY theme";

			DB.ps = DB.conn.prepareStatement(sql);
			DB.rs = DB.ps.executeQuery();

			while (DB.rs.next()) {
				data.add(new Livre(DB.rs.getInt(1), DB.rs.getString(2), DB.rs.getString(3), DB.rs.getString(4),
						DB.rs.getString(5), DB.rs.getString(6), DB.rs.getString(7), DB.rs.getInt(8)));

				// mettre la ObservableList dans une FilteredList (affichez initialement toutes les donn�es).
				FilteredList<Livre> dataFiltree = new FilteredList<>(data, b -> true);

				// 2. D�finissez le filtre Pr�dicat chaque fois que le filtre change.
				filterField.textProperty().addListener((observable, ancVal, nouvVal) -> {
					dataFiltree.setPredicate(livre -> {
						// Si le texte du filtre est vide, affichez tous les livres

						if (nouvVal == null || nouvVal.isEmpty()) {
							return true;
						}
						// Comparez le titre, le th�me, l'identifiant de chaque livre avec le texte du filtre.
						String lowerCaseFilter = nouvVal.toLowerCase();

						if (livre.getTitre().toLowerCase().indexOf(lowerCaseFilter) != -1) {
							return true; // Le filtre correspond au titre.
						} else if (livre.getTheme().toLowerCase().indexOf(lowerCaseFilter) != -1) {
							return true; //Le filtre correspond au theme
						} else if (String.valueOf(livre.getId()).indexOf(lowerCaseFilter) != -1)
							return true; // Le filtre correspond au id
						else
							return false; //
					});
				});

				// 3. Mettre la FilteredList dans une SortedList.
				SortedList<Livre> dataTriee = new SortedList<>(dataFiltree);

				// 4. Liez le comparateur SortedList au comparateur TableView.
				// Otherwise, sorting the TableView would have no effect.
				dataTriee.comparatorProperty().bind(livre.comparatorProperty());
				// 5. Ajoutez des donn�es tri�es (et filtr�es) � la table.
				livre.setItems(dataTriee);

			}

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	private void afficherLivreSelectionne() {//cette m�thode permet d'afficher sur les labels(code,titre,theme) les informations de  la ligne de tableau  selectionn�e par l'utilisateur
		livre.setOnMouseClicked(e -> {
			try {
				Livre livre1 = (Livre) livre.getSelectionModel().getSelectedItem();

				String sql = "SELECT * FROM livre  where idLivre=?";//selectionner tous les champs provenant  du tableau livre dont idLivre �gale l'id_livre de la ligne s�lectionn�e
				DB.ps = DB.conn.prepareStatement(sql);
				DB.ps.setInt(1, livre1.getId());
				DB.rs = DB.ps.executeQuery();
				while (DB.rs.next()) {
					labelCode.setText(DB.rs.getString("idLivre"));
					labelTitre.setText(DB.rs.getString("titre"));
					Theme.setValue(DB.rs.getString("theme"));
				}

			} catch (Exception en) {
				en.printStackTrace();
			}
		});
	}

	// *****************************vider*****************************************************
	@FXML
	public void vider(ActionEvent event) { //permet de vider tous les labels
		labelCode.clear();
		labelTitre.clear();
		Theme.setValue(null);
		LabelRechercher.clear();
	}

	// ************************************Reserver*****************************************
	@FXML
	public void reserver(ActionEvent event) {
		int IdLiv = Integer.parseInt(labelCode.getText());
		String titre = labelTitre.getText();
		String theme = Theme.getSelectionModel().getSelectedItem();
		int ID = CompteId.getUser().getId();
		try {
			if (titre.equals("")) {//1-l'utilisateut doit remplir tous les champs -> La v�rification de remplissage 
				Alert alert = new Alert(Alert.AlertType.ERROR);
				alert.setTitle("Erreur");
				alert.setHeaderText(null);
				alert.setContentText("Vous devez remplir tous les champs !");
				alert.showAndWait();
			} else {//2-V�rifier si les informations remplis sont correctes 
				String sql8 = "Select * FROM livre WHERE  idLivre=" + IdLiv + "";//selections tous les champs provenant  du tableau livre qui ont comme idLivre �gale � l'id remplit par l'utilisateur                                                                 
				DB.ps = DB.conn.prepareStatement(sql8);
				DB.rs = DB.ps.executeQuery();
				if (DB.rs.next()) {
					String tit = DB.rs.getString("titre");
					String tm = DB.rs.getString("theme");
					if (!tit.equals(titre) || !tm.equals(theme)) {//Si le titre ou le th�me remplit est diff�rent de cel selectionn� par la requete;un message d'erreur sera produit
						Alert alert = new Alert(Alert.AlertType.ERROR);
						alert.setTitle("Erreur");
						alert.setHeaderText(null);
						alert.setContentText("Ce livre n'existe pas !");
						alert.showAndWait();
					} else {//3-l'utilisateur ne peut reserver q'un nombre pr�cis des livres qu'on a initialis� par 3
						String sql6 = "Select COUNT(idRes) FROM r�servation WHERE  IdUser=" + ID + " ";
						//selectionner le nombre des r�servations  provenant  du table r�servation selon chaque utilisateur
						DB.ps = DB.conn.prepareStatement(sql6);
						DB.rs = DB.ps.executeQuery();
						if (DB.rs.next()) {
							int Nombre = DB.rs.getInt(1);
							if (Nombre == NombreReserv) {//Si le nombre retourner par la requete est 3, dans ce cas l'utilisateur n'a pas le droit de reserver
								Alert alert = new Alert(Alert.AlertType.ERROR);
								alert.setTitle("Erreur");
								alert.setHeaderText(null);
								alert.setContentText("Vous avez d�j� r�serv� 3 livres !");
								alert.showAndWait();
							} else {// 4-faire la r�servation 
								String sql1 = "Select titre FROM r�servation Where IdUser=" + ID + " AND IdLivre="
										+ IdLiv + "";//selectionner le titre de livre provenant du table "r�servation" dont id de livre(idLivre) est celui remplit par l'utilisateur est selon l'utilisateur connect�(idUser)
								DB.ps = DB.conn.prepareStatement(sql1);
								DB.rs = DB.ps.executeQuery();
								if (DB.rs.next()) {//5-s'il existe deja ce titre dans la table, dans ce ca l'utilisateur ne peut pas r�server le livre
									String title = DB.rs.getString("titre");
									Alert alert = new Alert(Alert.AlertType.ERROR);
									alert.setTitle("Erreur");
									alert.setHeaderText(null);
									alert.setContentText("Vous avez d�j� r�serv� le livre " + title + "!");
									alert.showAndWait();

								} else {//6- le titre de livre n'existe pas dans la table "r�servation":
									String sql2 = "select qte from livre where idLivre = ?";//selectionn� la quantit� de livre provenant du table "livre" selon id livre remplit par l'utilisateur 
									DB.ps = DB.conn.prepareStatement(sql2);
									DB.ps.setInt(1, IdLiv);
									DB.rs = DB.ps.executeQuery();
									while (DB.rs.next()) {
										int Qte = DB.rs.getInt("qte");
										if (Qte == 0) {//7-Si la quanit�==0
											DB.ps = DB.conn.prepareStatement(
													"update livre set etat = 'EMPRUNTE' where idLivre = ?");//On modifie l'�tat de livre de DISPONIBLE � RESERVER ,et dans ce cas l'utilisateur ne peut pas  reserver le livre
											DB.ps.setInt(1, IdLiv);
											DB.ps.executeUpdate();
											Alert alert = new Alert(Alert.AlertType.ERROR);
											alert.setHeaderText(null);
											alert.setContentText(
													"Ce livre n'est pas disponible!\nVeuillez choisir un autre livre.");
											alert.showAndWait();
										} else {//8-Si la quantit� est diff�rente �  0,on selectionn�  idExemplaire,titre,auteur provenant du table exemplaire dont idLivre est celui remplit par l'utilisateur et l'�tat de livre  est disponible 
											String sql3 = "select idExemplaire,titre,auteur from exemplaire where idLivre = ?  and etat='DISPONIBLE' limit 1";//limit 1 :la premi�re ligne selectionn�e   
											DB.ps = DB.conn.prepareStatement(sql3);
											DB.ps.setInt(1, IdLiv);
											DB.rs = DB.ps.executeQuery();
											if (DB.rs.next() == false) //9-Pas de livre(selon id livre remplit par l'utilisateur) disponible
											{
												Alert alert = new Alert(Alert.AlertType.ERROR);
												alert.setHeaderText(null);
												alert.setContentText(
														"Ce livre n'est pas disponible!\nVeuillez choisir un autre livre.");
												alert.showAndWait();
											} else {//10-il existe un livre disponible;faire la r�servation
												int IDExemp = DB.rs.getInt("idExemplaire");
												String Title = DB.rs.getString("titre");
												String Auteur = DB.rs.getString("auteur");
												//11-inserer sur la table r�servation les IdUser,IdLivre,IdExemplaire,titre,auteur,theme
												String sql4 = "insert into r�servation(IdUser,IdLivre,IdExemplaire,titre,auteur,theme)values(?,?,?,?,?,?)";
												DB.ps = DB.conn.prepareStatement(sql4);
												DB.ps.setInt(1, CompteId.getUser().getId());
												DB.ps.setInt(2, IdLiv);
												DB.ps.setInt(3, IDExemp);
												DB.ps.setString(4, titre);
												DB.ps.setString(5, Auteur);
												DB.ps.setString(6, theme);
												int i = DB.ps.executeUpdate();
												if (i > 0) {//si l'insertion est faire avec succ� on modifie l'etat de ce livre de DISPONIBLE � RESERVER selon son id
													String sql5 = "update exemplaire set etat = 'RESERVE' where idExemplaire = ?";//modifier l'�tat de exemplaire de DISPONIBLE � RESERVER selon idExemplaire(id de l'exemplaire que l'utilisateur � r�server)
													DB.ps = DB.conn.prepareStatement(sql5);
													DB.ps.setInt(1, IDExemp);
													DB.ps.executeUpdate();
													Alert alert = new Alert(Alert.AlertType.INFORMATION);
													alert.setHeaderText(null);
													alert.setContentText("Le livre " + Title + "  est bien r�serv�!");
													alert.showAndWait();
												} else {
													Alert alert = new Alert(Alert.AlertType.ERROR);
													alert.setHeaderText(null);
													alert.setContentText("Erreur lors de la r�servation du livre!");
													alert.showAndWait();
												}

											}
										}

									}
								}
							}

						}
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}