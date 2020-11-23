/*
 * Copyright (c) 2018, CGI.
 */
package tps.filtering.ws;

import java.util.ArrayList;

/**
 * @author agilan.colbert
 */
public class Hotel {
    private int idHotel;
    private String nom;
    private String addresse;
    private ArrayList<Chambre> chambres;

    /**
     * 
     */
    public Hotel() {
        super();
        chambres = new ArrayList<Chambre>();
    }



	public Hotel(int idHotel, String nom, String addresse, ArrayList<Chambre> chambres) {
		super();
		this.idHotel = idHotel;
		this.nom = nom;
		this.addresse = addresse;
		this.chambres = chambres;
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
     * Getter for nom.
     * 
     * @return the nom
     */
    public String getNom() {
        return nom;
    }

    /**
     * Setter for nom.
     * 
     * @param nom the nom to set
     */
    public void setNom(final String nom) {
        this.nom = nom;
    }

    /**
     * Getter for addresse.
     * 
     * @return the addresse
     */
    public String getAddresse() {
        return addresse;
    }

    /**
     * Setter for addresse.
     * 
     * @param addresse the addresse to set
     */
    public void setAddresse(final String addresse) {
        this.addresse = addresse;
    }
    
    public ArrayList<Chambre> getChambres() {
		return chambres;
	}

	public void setChambres(ArrayList<Chambre> chambres) {
		this.chambres = chambres;
	}

}
