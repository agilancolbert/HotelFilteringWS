/*
 * Copyright (c) 2018, CGI.
 */
package tps.filtering.ws;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.Statement;

public class JdbcConnectionDemo {
    public static void main(final String[] args) {
        final String JdbcURL = "jdbc:mysql://localhost:3306/hotel_booking?useSSL=false";
        final String Username = "root";
        final String password = "root";
        Connection con = null;

        ResultSet résultats = null;
        final String requete = "SELECT * FROM hotel";

        try {
            System.out.println("Connecting to database..............." + JdbcURL);
            con = DriverManager.getConnection(JdbcURL, Username, password);
            System.out.println("Connection is successful!!!!!!");

            final Statement stmt = con.createStatement();
            résultats = stmt.executeQuery(requete);
            final ResultSetMetaData rsmd = résultats.getMetaData();
            final int nbCols = rsmd.getColumnCount();
            while (résultats.next()) {
                for (int i = 1; i <= nbCols; i++)
                    System.out.print(résultats.getString(i) + " ");
                System.out.println();
            }
            résultats.close();

        } catch (final Exception e) {
            e.printStackTrace();
        }
    }
}