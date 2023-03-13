package pl.polsl.tomasz.michalik.utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * Creates the database
 *
 * @author Tomasz Michalik
 * @version 1.0
 */
public class BootstrapDatabase {

    public BootstrapDatabase() {
        try {
            // loading the JDBC driver
            Class.forName("org.apache.derby.jdbc.ClientDriver");
        } catch (ClassNotFoundException cnfe) {
            System.out.println("coś się tu zwaliło");
            System.err.println(cnfe.getMessage());
            return;
        }

        // make a connection to DB
        try (Connection con = DriverManager.getConnection("jdbc:derby://localhost:1527/turingMachine", "tm", "tm")) {
            Statement statement = con.createStatement();
            statement.executeUpdate("CREATE TABLE tms "
                    + "(id INTEGER not null primary key) ");

            statement.executeUpdate("CREATE TABLE configurations "
                    + "(id INTEGER not null primary key,"
                    + "current_state char(10),"
                    + "no_moves integer,"
                    + "turing_machine integer,"
                    + "constraint c1 foreign key(turing_machine) references tms(id) on delete cascade )");

            statement.executeUpdate("CREATE TABLE tapes "
                    + "(id INTEGER not null,"
                    + "contents char(100),"
                    + "config integer,"
                    + "primary key( id, config), "
                    + "constraint c2 foreign key (config) references configurations(id) on delete cascade )");

            statement.executeUpdate("CREATE TABLE transitions "
                    + "(id INTEGER not null primary key ,"
                    + "contents char(100),"
                    + "turing_machine integer,"
                    + "constraint c3 foreign key(turing_machine) references tms(id) on delete cascade)");

            System.out.println("Table created");
        } catch (SQLException sqle) {
            System.err.println(sqle.getMessage());
        }
    }

    public static void main(String[] args) {
        BootstrapDatabase bdb = new BootstrapDatabase();

    }

}
