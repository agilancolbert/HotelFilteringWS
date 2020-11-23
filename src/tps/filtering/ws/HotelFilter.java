package tps.filtering.ws;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class HotelFilter {
	private static final String JdbcURL = "jdbc:mysql://localhost:3306/hotel_booking?useSSL=false";
    private static final String Username = "root";
    private static final String password = "root";
    private ArrayList<Hotel> hotels;
    private ArrayList<Hotel> lastFilterAvailableHotel;
    private FilterRequestData filterRequestData;
    private ArrayList<Chambre> lastFilterAvailableRooms;
    
    static Connection con;
    Statement stmt;
    PreparedStatement preparedStmt;
    
	public HotelFilter() {
		super();
		this.hotels = new ArrayList<Hotel>();
		this.lastFilterAvailableHotel = new ArrayList<Hotel>();
		filterRequestData = null;
		this.lastFilterAvailableRooms = new ArrayList<Chambre>();
	}
	
	public boolean reserveHotel(int idHotel, String date, int nbNight, int nbRoom) throws ParseException{
		
		Hotel hotelConcerne = null;
		ArrayList<Integer> listeAllIdChambreHotelConcerne = new ArrayList<>();
		ArrayList<Integer> listeChambreDisponibleHotelConcerne = new ArrayList<>(); 
		
		ArrayList<ExposedHotel> exposedAvailableHotels = filterHotel(date,nbNight,nbRoom);
		
		if(lastFilterAvailableHotel.isEmpty())
			return false;
		
		for(Hotel hotel : hotels){
			if(hotel.getIdHotel() == idHotel)
				hotelConcerne = hotel;
		}
		
		if(hotelConcerne == null)
			return false;
		
		for(Chambre c : hotelConcerne.getChambres()){
			listeAllIdChambreHotelConcerne.add(c.getIdChambre());
		}
		
		if(listeAllIdChambreHotelConcerne.isEmpty())
			return false;
		
		for(Chambre allChambreDispo : lastFilterAvailableRooms){
			for(Integer idChambreHotelConcerne : listeAllIdChambreHotelConcerne){
				if(idChambreHotelConcerne==allChambreDispo.getIdChambre())
					listeChambreDisponibleHotelConcerne.add(idChambreHotelConcerne);
			}
		}
		
		if(listeChambreDisponibleHotelConcerne.size()<filterRequestData.getNbRoom())
			return false;
		
        for (Hotel h: lastFilterAvailableHotel){
        	System.out.println("hotel last research disponible : " + h.getNom());
        	//System.out.println("hotel last research data : " + filterRequestData.getDate());
        }

				// RESERVATION
				String requeteReserveHotel = " INSERT INTO reservation "
						+ "(idChambre, arrivalDate, duration, departureDate) VALUES "
						+ "(?, ?, ?, ?)";
				
				
				try {
					Class.forName("com.mysql.jdbc.Driver");
		            con = DriverManager.getConnection(JdbcURL, Username, password);
		            
		            //for(Integer idChambreHotelConcerne : listeAllIdChambreHotelConcerne){
		            for(int i = 0; i < filterRequestData.getNbRoom(); i++){
		            // BOUCLE POUR TOUTES LES CHAMBRES
		            PreparedStatement stmt = con.prepareStatement(requeteReserveHotel);
		            //stmt.setInt(1, idChambreHotelConcerne);
		            stmt.setInt(1, listeAllIdChambreHotelConcerne.get(i));
		            stmt.setString(2, filterRequestData.getArrivalDate());
		            stmt.setInt(3, filterRequestData.getNbNight());
		            stmt.setString(4, filterRequestData.getDepartureDate());
		            
		            System.out.println(stmt.toString());
		            
		            stmt.executeUpdate();
		            }

		        } catch (final Exception e) {
		            e.printStackTrace();
		        }
				return true;
		
	}
	
	public ArrayList<ExposedHotel> filterHotel(String date, int nbNight, int nbRoom) throws ParseException{

		
		getAllHotel();
		int cptAvalaibleRooms;
		ArrayList<Hotel> availableHotels = new ArrayList<Hotel>();
		
		Date arrivalDateClient = new SimpleDateFormat("yyyy-MM-dd").parse(date);
		Calendar calendar = Calendar.getInstance(); 
		calendar.setTime(arrivalDateClient);
		calendar.add(Calendar.DATE, nbNight);
		
		Date departureDateClient = calendar.getTime();
		
		filterRequestData = new FilterRequestData(date,new SimpleDateFormat("yyyy-MM-dd").format(departureDateClient),nbNight,nbRoom);
		
        for(Hotel h : hotels){
        	cptAvalaibleRooms = 0;
        	ArrayList<Chambre> chambres = h.getChambres();
        	for(Chambre c : chambres){
        		ArrayList<Reservation> reservations = c.getReservations();
        		for(Reservation r : reservations){
        			Date dateReservationDebut;
        			Date dateReservationFin;
					try {
						dateReservationDebut = new SimpleDateFormat("yyyy-MM-dd").parse(r.getArrivalDate());
						dateReservationFin = new SimpleDateFormat("yyyy-MM-dd").parse(r.getDepartureDate());
						
	        			if(arrivalDateClient.compareTo(dateReservationDebut)<0 
	        					&& departureDateClient.compareTo(dateReservationDebut)<=0){
	        				cptAvalaibleRooms++;
	        				lastFilterAvailableRooms.add(c);
	        			}
	        			else if(arrivalDateClient.compareTo(dateReservationFin)>=0){
	        				cptAvalaibleRooms++;
	        				lastFilterAvailableRooms.add(c);
	        			}
	        			
					} catch (ParseException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}        			
        		}
        	}
        	if(cptAvalaibleRooms >= nbNight)
        		availableHotels.add(h);
        }
        lastFilterAvailableHotel.clear();
        lastFilterAvailableHotel.addAll(availableHotels);
        /*
        for (Hotel h: availableHotels){
        	System.out.println("hotel dispo : " + h.getNom());
        }
        */
        ArrayList<ExposedHotel> listeExposedHotel = new ArrayList<ExposedHotel>();
        for(Hotel h : availableHotels){
        	listeExposedHotel.add(new ExposedHotel(h.getIdHotel(), h.getNom(), h.getAddresse()));
        }
        return listeExposedHotel;
        
	}
	
	private void getAllHotel(){
		String requeteAllHotel = "SELECT * FROM hotel";
		ResultSet rsGetAllHotel;
		hotels.clear();
		
		try {
			Class.forName("com.mysql.jdbc.Driver");
            con = DriverManager.getConnection(JdbcURL, Username, password);

            final Statement stmt = con.createStatement();
            rsGetAllHotel = stmt.executeQuery(requeteAllHotel);

            while (rsGetAllHotel.next()) {
            	hotels.add(
            			new Hotel(
            					rsGetAllHotel.getInt("idHotel"),
            					rsGetAllHotel.getString("nom"),
            					rsGetAllHotel.getString("addresse"),
            					getAllChambres(rsGetAllHotel.getInt("idHotel"))));
            }
            rsGetAllHotel.close();
        } catch (final Exception e) {
            e.printStackTrace();
        }
	}
	
	private ArrayList<Chambre> getAllChambres(int idHotel){
		String requeteAllChambres = "SELECT * FROM chambre WHERE idHotel = ?";
		ArrayList<Chambre> chambres = new ArrayList<Chambre>();
		ResultSet rsAllChambres;
		
		try {
			Class.forName("com.mysql.jdbc.Driver");
            con = DriverManager.getConnection(JdbcURL, Username, password);

            PreparedStatement stmt = con.prepareStatement(requeteAllChambres);
            stmt.setInt(1, idHotel);
            rsAllChambres = stmt.executeQuery();

            while (rsAllChambres.next()) {
            	chambres.add(
            			new Chambre(
            					rsAllChambres.getInt("idChambre"),
            					getAllReservation(rsAllChambres.getInt("idChambre"))));
            }
            rsAllChambres.close();            

        } catch (final Exception e) {
            e.printStackTrace();
        }
		
		return chambres;
	}
	
	private ArrayList<Reservation> getAllReservation(int idChambre){
		String requeteAllReservation= "SELECT * FROM reservation WHERE idChambre = ?";
		ArrayList<Reservation> reservations = new ArrayList<Reservation>();
		ResultSet rsGetAllReservation;
		
		try {
			Class.forName("com.mysql.jdbc.Driver");
            con = DriverManager.getConnection(JdbcURL, Username, password);

            PreparedStatement stmt = con.prepareStatement(requeteAllReservation);
            stmt.setInt(1, idChambre);
            rsGetAllReservation = stmt.executeQuery();

            while (rsGetAllReservation.next()) {
            	reservations.add(
            			new Reservation(
            					rsGetAllReservation.getInt("idChambre"),
            					rsGetAllReservation.getString("arrivalDate"),
            					rsGetAllReservation.getInt("duration"),
            					rsGetAllReservation.getString("departureDate")));
            }
            rsGetAllReservation.close();            

        } catch (final Exception e) {
            e.printStackTrace();
        }
		
		return reservations;
	}
	
	
	

}
