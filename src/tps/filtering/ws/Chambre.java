package tps.filtering.ws;

import java.util.ArrayList;

public class Chambre {
	private int idChambre;
	private ArrayList<Reservation> reservations;
	
	public Chambre() {
		super();
	}

	public Chambre(int idChambre, ArrayList<Reservation> reservations) {
		super();
		this.idChambre = idChambre;
		this.reservations = reservations;
	}

	public int getIdChambre() {
		return idChambre;
	}

	public void setIdChambre(int idChambre) {
		this.idChambre = idChambre;
	}

	public ArrayList<Reservation> getReservations() {
		return reservations;
	}

	public void setReservations(ArrayList<Reservation> reservations) {
		this.reservations = reservations;
	}

	
	
}
