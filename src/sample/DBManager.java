package sample;
//esta clase es la que hace conexion con la base de datos bdBoletaje

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBManager  {

    private static Connection connection;

   public static Connection getConnection() {
        return connection;
    }

   public DBManager() throws SQLException {
        connect();
    }

    private void   connect() throws SQLException {
        // TODO: program this method
        try {
            String url = "jdbc:mysql://localhost/bdBoletaje";
             connection = DriverManager.getConnection(url,
                    "root", "");
            System.out.println("Conexión exitosa a dbBoletaje ");

        } catch (SQLException ex) {
             connection = null;
            ex.printStackTrace();
            System.out.println("SQLException:␣" + ex.getMessage());
            System.out.println("SQLState:␣" + ex.getSQLState());
            System.out.println("VendorError:␣" + ex.getErrorCode());
        }

    }

    /**
     * Close the connection to the database if it is still open.
     *
     */
    public void close() throws SQLException {
        if (  connection != null) {
            connection.close();
        }
        connection = null;
    }

}