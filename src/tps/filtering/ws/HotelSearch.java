/*
 * Copyright (c) 2018, CGI.
 */
package tps.filtering.ws;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.Statement;

/**
 * @author agilan.colbert
 */
public class HotelSearch {
	private static final String JdbcURL = "jdbc:mysql://localhost:3306/hotel_booking?useSSL=false";
    private static final String Username = "root";
    private static final String password = "root";

    // methode chercher hotels avec table disponiblite
    // param-in : date, nbNight, nbRoom
    //
	public static String searchHotel(String date, int nbNight, int nbRoom){

        Connection con = null;

        ResultSet resultats = null;
        String requete = "SELECT * FROM disponibilite "
        		+ "WHERE date=?"
        		+ "AND nbNight > ? "
        		+ "AND nbRoom > ? ";
        		//
        
        String searchHotelResult = null;

        try {
            System.out.println("Connecting to database..............." + JdbcURL);
            con = DriverManager.getConnection(JdbcURL, Username, password);
            System.out.println("Connection is successful!!!!!!");

            PreparedStatement stmt = con.prepareStatement(requete);
            stmt.setString(1, date);
            stmt.setInt(2, nbNight);
            stmt.setInt(3, nbRoom);
            
            resultats = stmt.executeQuery();
            final ResultSetMetaData rsmd = resultats.getMetaData();
            final int nbCols = rsmd.getColumnCount();
            while (resultats.next()) {
                for (int i = 1; i <= nbCols; i++)
                    System.out.print(resultats.getString(i) + " ");
                System.out.println();
            }
            resultats.close();

        } catch (final Exception e) {
            e.printStackTrace();
        }
        
        return null;
        
	}
	
	
	
	public boolean reserveHotel(int idHotel, int nbNight, int nbRoom, String date){
        Connection con = null;
        ResultSet resultats = null;
        String requeteReservation = "UPDATE disponibilite"
        		+ "SET"
        		+ "nbRoom=?,"
        		+ "nbNight=?"
        		+ "WHERE date=?"
        		+ "AND idHotel=?";
        
        		//

        try {
            System.out.println("Connecting to database..............." + JdbcURL);
            con = DriverManager.getConnection(JdbcURL, Username, password);
            System.out.println("Connection is successful!!!!!!");

            PreparedStatement stmt = con.prepareStatement(requeteReservation);
            stmt.setInt(1, nbRoom);
            stmt.setInt(2, nbNight);
            stmt.setString(3, date);
            stmt.setInt(4, idHotel);
            
            
            resultats = stmt.executeQuery();
            final ResultSetMetaData rsmd = resultats.getMetaData();
            final int nbCols = rsmd.getColumnCount();
            while (resultats.next()) {
                for (int i = 1; i <= nbCols; i++)
                    System.out.print(resultats.getString(i) + " ");
                System.out.println();
            }
            resultats.close();

        } catch (final Exception e) {
            e.printStackTrace();
        }
		return true;
	}
	public static void main(final String[] args) throws Exception {
		HotelSearch.searchHotel("12/02/2015", 2, 2);
    }

}
