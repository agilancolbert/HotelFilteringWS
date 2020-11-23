package tps.filtering.ws;

public class ExposedHotel {
	private int idHotel;
    private String nom;
    private String addresse;
    
	public ExposedHotel(int idHotel, String nom, String addresse) {
		super();
		this.idHotel = idHotel;
		this.nom = nom;
		this.addresse = addresse;
	}

	public ExposedHotel() {
		super();
	}

	public int getIdHotel() {
		return idHotel;
	}

	public void setIdHotel(int idHotel) {
		this.idHotel = idHotel;
	}

	public String getNom() {
		return nom;
	}

	public void setNom(String nom) {
		this.nom = nom;
	}

	public String getAddresse() {
		return addresse;
	}

	public void setAddresse(String addresse) {
		this.addresse = addresse;
	}  

}
