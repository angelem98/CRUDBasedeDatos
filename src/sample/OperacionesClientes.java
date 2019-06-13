package sample;

//esta clase hace las operaciones de agregar, actualizar, eliminar de la base de datos bdBoletaje
import javafx.beans.property.IntegerProperty;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

public class OperacionesClientes {

    Connection connection;

    public OperacionesClientes(Connection conn){
        this.connection = conn;
    }

    public Cliente getCliente(int  id){
        int clienteId = 0;
        String nombre = "", apellidos = "", direccion = "";

        String query = "SELECT clienteid, nombre, apellidos, direccion " +
                "FROM cliente " +
                "WHERE clienteID = " + id;

        try {
            Statement stmt = connection.createStatement();
            ResultSet rs = stmt.executeQuery(query);

            if (rs.next()) {
                clienteId = rs.getInt("clienteid");
                nombre = rs.getString("nombre");
                apellidos = rs.getString("apellidos");
                direccion = rs.getString("direccion");
            }

            //System.out.println(clienteId + ", " + nombre + " " + apellidos + ", " + direccion);

            return new Cliente(clienteId, nombre, apellidos, direccion);
        }
        catch (java.sql.SQLException ex){
            ex.printStackTrace();
            System.out.println("SQLException:␣" + ex.getMessage());
            System.out.println("SQLState:␣" + ex.getSQLState());
            System.out.println("VendorError:␣" + ex.getErrorCode());

            return null;
        }
    }

    public int deleteCliente(int id){
        int clienteId;
        String nombre, apellidos, direccion;

        String query = "delete from cliente where clienteID = " + id;

        int numRegs = 0;
        try {
            Statement stmt = connection.createStatement();
            numRegs = stmt.executeUpdate(query);

            System.out.println("Cantidad de registros afectados: " + numRegs);
        }
        catch (java.sql.SQLException ex){
            ex.printStackTrace();
            System.out.println("SQLException:␣" + ex.getMessage());
            System.out.println("SQLState:␣" + ex.getSQLState());
            System.out.println("VendorError:␣" + ex.getErrorCode());
        }

        return numRegs;
    }

    public int insertCliente(int clienteId,String nombre, String apellidos, String direccion){

        String query = "insert into cliente(clienteId, nombre, apellidos, direccion) " +
                "values (" + clienteId + ", '" + nombre + "', '" + apellidos + "', '" + direccion + "')";
        System.out.println(query);

      /*  insert into cliente(nombre, apellidos, direccion
                values ('Jorge', 'Estrada', 'Lázaro Cárdenas 123')  */

        int numRegs = 0;
        try {
            Statement stmt = connection.createStatement();
            numRegs = stmt.executeUpdate(query);

            System.out.println("Cantidad de registros afectados: " + numRegs);

        }
        catch (java.sql.SQLException ex){
            ex.printStackTrace();
            System.out.println("SQLException:␣" + ex.getMessage());
            System.out.println("SQLState:␣" + ex.getSQLState());
            System.out.println("VendorError:␣" + ex.getErrorCode());
        }

        return numRegs;
    }

       public void update(Cliente cliente){
         try{
             Statement stmt = connection.createStatement();
             stmt.executeUpdate("UPDATE cliente set nombre= '" + cliente.getNombre() + ", apellidos= " + cliente.getApellidos() + ",direccion= " +cliente.getDireccion()
             + "where clienteId = " +cliente.getClienteId());
         }catch(java.sql.SQLException ex){
             ex.printStackTrace();
             System.out.println("SQLException:␣" + ex.getMessage());
             System.out.println("SQLState:␣" + ex.getSQLState());
             System.out.println("VendorError:␣" + ex.getErrorCode());
         }
       }
}
