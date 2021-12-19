package model;

import java.util.Date;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;

public class Reservation extends Livre{

	private ObjectProperty<Date> dateReservation;

	public Reservation(Integer idLiv, String titre, String auteur, String theme, Date dateReservation) {
		super(idLiv, titre, auteur, theme);
		this.dateReservation = new SimpleObjectProperty<Date>(dateReservation);
	}


	public Date getDateReservation() {
		return dateReservation.get();
	}

}
