package tps.filtering.ws;

public class FilterRequestData {
	private String arrivalDate;
	private String departureDate;
	private int nbRoom;
	private int nbNight;
	
	public FilterRequestData() {
		super();
	}
	
	public FilterRequestData(String arrivalDate, String departureDate, int nbNight, int nbRoom) {
		super();
		this.arrivalDate = arrivalDate;
		this.departureDate = departureDate;
		this.nbRoom = nbRoom;
		this.nbNight = nbNight;
	}
	
	public int getNbRoom() {
		return nbRoom;
	}
	public void setNbRoom(int nbRoom) {
		this.nbRoom = nbRoom;
	}
	public int getNbNight() {
		return nbNight;
	}
	public void setNbNight(int nbNight) {
		this.nbNight = nbNight;
	}
	
	public String getArrivalDate() {
		return arrivalDate;
	}

	public void setArrivalDate(String arrivalDate) {
		this.arrivalDate = arrivalDate;
	}

	public String getDepartureDate() {
		return departureDate;
	}

	public void setDepartureDate(String departureDate) {
		this.departureDate = departureDate;
	}
	
	

}
