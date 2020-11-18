/*
 * Copyright (c) 2018, CGI.
 */
package tps.filtering.ws;

/**
 * @author agilan.colbert
 */
public class Disponibilite {
    private int idDisponibilite;
    private int idHotel;
    private String date;
    private int nbRoom;
    private int nbNight;

    /**
     * 
     */
    public Disponibilite() {
        super();
    }

    /**
     * @param idDisponibilite
     * @param idHotel
     * @param date
     * @param nbRoom
     * @param nbNight
     */
    public Disponibilite(final int idDisponibilite, final int idHotel, final String date, final int nbRoom, final int nbNight) {
        super();
        this.idDisponibilite = idDisponibilite;
        this.idHotel = idHotel;
        this.date = date;
        this.nbRoom = nbRoom;
        this.nbNight = nbNight;
    }

    /**
     * Getter for idDisponibilite.
     * 
     * @return the idDisponibilite
     */
    public int getIdDisponibilite() {
        return idDisponibilite;
    }

    /**
     * Setter for idDisponibilite.
     * 
     * @param idDisponibilite the idDisponibilite to set
     */
    public void setIdDisponibilite(final int idDisponibilite) {
        this.idDisponibilite = idDisponibilite;
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
