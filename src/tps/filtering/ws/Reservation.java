/*
 * Copyright (c) 2018, CGI.
 */
package tps.filtering.ws;

/**
 * @author agilan.colbert
 */
public class Reservation {
    private int idReservation;
    private int idHotel;
    private String date;
    private int nbRoom;
    private int nbNight;

    /**
     * 
     */
    public Reservation() {
        super();
    }

    /**
     * @param idReservation
     * @param idHotel
     * @param date
     * @param nbRoom
     * @param nbNight
     */
    public Reservation(final int idReservation, final int idHotel, final String date, final int nbRoom, final int nbNight) {
        super();
        this.idReservation = idReservation;
        this.idHotel = idHotel;
        this.date = date;
        this.nbRoom = nbRoom;
        this.nbNight = nbNight;
    }

    /**
     * Getter for idReservation.
     * 
     * @return the idReservation
     */
    public int getIdReservation() {
        return idReservation;
    }

    /**
     * Setter for idReservation.
     * 
     * @param idReservation the idReservation to set
     */
    public void setIdReservation(final int idReservation) {
        this.idReservation = idReservation;
    }

    /**
     * Getter for idHotel.
     * 
     * @return the idHotel
     */
    public int getIdHotel() {
        return idHotel;
    }

    /**
     * Setter for idHotel.
     * 
     * @param idHotel the idHotel to set
     */
    public void setIdHotel(final int idHotel) {
        this.idHotel = idHotel;
    }

    /**
     * Getter for date.
     * 
     * @return the date
     */
    public String getDate() {
        return date;
    }

    /**
     * Setter for date.
     * 
     * @param date the date to set
     */
    public void setDate(final String date) {
        this.date = date;
    }

    /**
     * Getter for nbRoom.
     * 
     * @return the nbRoom
     */
    public int getNbRoom() {
        return nbRoom;
    }

    /**
     * Setter for nbRoom.
     * 
     * @param nbRoom the nbRoom to set
     */
    public void setNbRoom(final int nbRoom) {
        this.nbRoom = nbRoom;
    }

    /**
     * Getter for nbNight.
     * 
     * @return the nbNight
     */
    public int getNbNight() {
        return nbNight;
    }

    /**
     * Setter for nbNight.
     * 
     * @param nbNight the nbNight to set
     */
    public void setNbNight(final int nbNight) {
        this.nbNight = nbNight;
    }

}
