/*
 * Copyright (c) 2018, CGI.
 */
package tps.filtering.ws;

/**
 * @author agilan.colbert
 */
public class Reservation {
    private int idReservation;
    private String arrivalDate;
    private int duration;
    private String departureDate;

	public Reservation() {
		super();
	}
	
	
	public Reservation(int idReservation, String arrivalDate, int duration, String departureDate) {
		super();
		this.idReservation = idReservation;
		this.arrivalDate = arrivalDate;
		this.duration = duration;
		this.departureDate = departureDate;
	}


	public int getIdReservation() {
		return idReservation;
	}
	public void setIdReservation(int idReservation) {
		this.idReservation = idReservation;
	}
    
	public String getArrivalDate() {
		return arrivalDate;
	}

	public void setArrivalDate(String arrivalDate) {
		this.arrivalDate = arrivalDate;
	}

	public int getDuration() {
		return duration;
	}

	public void setDuration(int duration) {
		this.duration = duration;
	}

	public String getDepartureDate() {
		return departureDate;
	}

	public void setDepartureDate(String departureDate) {
		this.departureDate = departureDate;
	}

    
}
