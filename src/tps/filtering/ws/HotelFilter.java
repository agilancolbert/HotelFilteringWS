package tps.filtering.ws;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class HotelFilter {
    private static String JdbcURL = "jdbc:mysql://localhost:3306/hotel_booking?useSSL=false";
    private static String Username = "root";
    private static String password = "root";
    private  ArrayList<Hotel> hotels;
    private  ArrayList<Hotel> lastFilterAvailableHotel;
    private FilterRequestData filterRequestData;
    private  ArrayList<Chambre> lastFilterAvailableRooms;

    static Connection con;
    Statement stmt;
    PreparedStatement preparedStmt;

    public HotelFilter() {
        super();
        this.hotels = new ArrayList<>();
        this.lastFilterAvailableHotel = new ArrayList<>();
        filterRequestData = null;
        this.lastFilterAvailableRooms = new ArrayList<>();
    }

    public boolean reserveHotel( int idHotel,  String date,  int nbNight,  int nbRoom) throws ParseException, SQLException, ClassNotFoundException {

        Hotel hotelConcerne = null;
         ArrayList<Integer> listeAllIdChambreHotelConcerne = new ArrayList<>();
         ArrayList<Integer> listeChambreDisponibleHotelConcerne = new ArrayList<>();

        filterHotel(date, nbNight, nbRoom);

        // renvoi false si il n'y a pas de chambre disponible
        if (lastFilterAvailableHotel.isEmpty())
            return false;

        // recupère l'hotel concerné passé en param
        for ( Hotel hotel : hotels) {
            if (hotel.getIdHotel() == idHotel)
                hotelConcerne = hotel;
        }

        if (hotelConcerne == null)
            return false;

        // recupere toutes les idChambre de l'hotel concerné
        for ( Chambre c : hotelConcerne.getChambres()) {
            listeAllIdChambreHotelConcerne.add(c.getIdChambre());
        }
           
        //peut etre inutile ** A SUPPRIMER **
        if (listeAllIdChambreHotelConcerne.isEmpty())
            return false;

        // recupere seulement les idChambres DISPO de l'hotel concerné
        for ( Chambre allChambreDispo : lastFilterAvailableRooms) {
            for ( Integer idChambreHotelConcerne : listeAllIdChambreHotelConcerne) {
                if (idChambreHotelConcerne == allChambreDispo.getIdChambre())
                    listeChambreDisponibleHotelConcerne.add(idChambreHotelConcerne);
            }
        }

        if (listeChambreDisponibleHotelConcerne.size() < nbRoom)
            return false;

        // RESERVATION
         String requeteReserveHotel = " INSERT INTO reservation "
                + "(idChambre, arrivalDate, duration, departureDate) VALUES "
                + "(?, ?, ?, ?)";

            Class.forName("com.mysql.jdbc.Driver");
            con = DriverManager.getConnection(JdbcURL, Username, password);

            for (int i = 0; i < nbRoom; i++) {
                // BOUCLE POUR TOUTES LES CHAMBRES
                 PreparedStatement stmt = con.prepareStatement(requeteReserveHotel);
                stmt.setInt(1, listeChambreDisponibleHotelConcerne.get(i));
                stmt.setString(2, date);
                stmt.setInt(3, nbNight);
                stmt.setString(4, filterRequestData.getDepartureDate());

                System.out.println(stmt.toString());

                stmt.executeUpdate();
            }
        return true;

    }

    public String[] filterHotel( String date,  int nbNight,  int nbRoom) throws ParseException {

        getAllHotel();
        int cptAvalaibleRooms;

         Date arrivalDateClient = new SimpleDateFormat("yyyy-MM-dd").parse(date);
         Calendar calendar = Calendar.getInstance();
        calendar.setTime(arrivalDateClient);
        calendar.add(Calendar.DATE, nbNight);

         Date departureDateClient = calendar.getTime();

        filterRequestData = new FilterRequestData(date, new SimpleDateFormat("yyyy-MM-dd").format(departureDateClient), nbNight, nbRoom);
         ArrayList<Chambre> listTmpChambresDisponibles = new ArrayList<>();
        for ( Hotel h : hotels) {
            cptAvalaibleRooms = 0;
             ArrayList<Chambre> chambres = h.getChambres();
            for ( Chambre c : chambres) {
                boolean chambreDispo = true;
                 ArrayList<Reservation> reservations = c.getReservations();
                for ( Reservation r : reservations) {
                    chambreDispo = true;
                    Date dateReservationDebut;
                    Date dateReservationFin;
                    dateReservationDebut = new SimpleDateFormat("yyyy-MM-dd").parse(r.getArrivalDate());
                    dateReservationFin = new SimpleDateFormat("yyyy-MM-dd").parse(r.getDepartureDate());

                    if (arrivalDateClient.compareTo(dateReservationDebut) >= 0
                            && (arrivalDateClient.compareTo(dateReservationFin) < 0)) {
                        chambreDispo = false;
                        break;
                    } else if (departureDateClient.compareTo(dateReservationDebut) > 0
                            && (departureDateClient.compareTo(dateReservationFin) <= 0)) {
                        chambreDispo = false;
                        break;
                    }
                }
                if (chambreDispo) {
                    cptAvalaibleRooms++;
                    listTmpChambresDisponibles.add(c);
                }
            }
            if (cptAvalaibleRooms >= nbRoom) {
                lastFilterAvailableHotel.add(h);
                lastFilterAvailableRooms.addAll(listTmpChambresDisponibles);
                listTmpChambresDisponibles.clear();
            }
        }
        /////////////////////////////////////////////////////////////////////
        // lastFilterAvailableHotel.clear();
        /////////////////////////////////////////////////////////////////////
         List<String> listeHotelsDispo = new ArrayList<>();
        for ( Hotel h : lastFilterAvailableHotel) {
            // listeHotelsDispo.add("Id de l'hotel : " + h.getIdHotel() + " Nom " + h.getNom() + " Adresse : " + h.getAddresse());
            listeHotelsDispo.add(h.getIdHotel() + " " + h.getNom());
        }
        return listeHotelsDispo.toArray(new String[listeHotelsDispo.size()]);

    }

    private void getAllHotel() {
         String requeteAllHotel = "SELECT * FROM hotel";
        ResultSet rsGetAllHotel;
        hotels.clear();

        try {
            Class.forName("com.mysql.jdbc.Driver");
            con = DriverManager.getConnection(JdbcURL, Username, password);

             Statement stmt = con.createStatement();
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
        } catch ( Exception e) {
            e.printStackTrace();
        }
    }

    private ArrayList<Chambre> getAllChambres( int idHotel) {
         String requeteAllChambres = "SELECT * FROM chambre WHERE idHotel = ?";
         ArrayList<Chambre> chambres = new ArrayList<>();
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

        } catch ( Exception e) {
            e.printStackTrace();
        }

        return chambres;
    }

    private ArrayList<Reservation> getAllReservation( int idChambre) {
         String requeteAllReservation = "SELECT * FROM reservation WHERE idChambre = ?";
         ArrayList<Reservation> reservations = new ArrayList<>();
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

        } catch ( Exception e) {
            e.printStackTrace();
        }

        return reservations;
    }

}
