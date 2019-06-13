package sample;

//esta clase obtiene los datos del cliente y los devuelve

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Cliente {
    private  IntegerProperty clienteId;
    private StringProperty nombre;
    private StringProperty apellidos;
    private StringProperty direccion;

    public Cliente(int clienteId, String nombre, String apellidos, String direccion) {
        this.clienteId = new SimpleIntegerProperty(clienteId);
        this.nombre = new SimpleStringProperty(nombre);
        this.apellidos = new SimpleStringProperty(apellidos);
        this.direccion = new SimpleStringProperty(direccion);
    }



    public int getClienteId() {  return clienteId.get();  }

    public IntegerProperty clienteIdProperty() {  return clienteId;  }

    public void setClienteId(int clienteId) {  this.clienteId.set(clienteId);  }

    public String getNombre() {  return nombre.get();  }

    public StringProperty nombreProperty() {  return nombre;  }

    public void setNombre(String nombre) {  this.nombre.set(nombre);  }

    public String getApellidos() {  return apellidos.get();  }

    public StringProperty apellidosProperty() {  return apellidos; }

    public void setApellidos(String apellidos) {  this.apellidos.set(apellidos); }

    public String getDireccion() {  return direccion.get();  }

    public StringProperty direccionProperty() {  return direccion;  }

    public void setDireccion(String direccion) { this.direccion.set(direccion);  }

    @Override
    public String toString() {
        return "Cliente{" +
                "clienteId=" + clienteId +
                ", nombre='" + nombre + '\'' +
                ", apellidos='" + apellidos + '\'' +
                ", direccion='" + direccion + '\'' +
                '}';
    }

   /*public String getNombre() { return nombre; }

    public void setNombre(String nombre) { this.nombre =nombre; }

    public String getApellidos() { return apellidos; }

    public void setApellidos(String apellidos) { this.apellidos = apellidos; }

    public String getDireccion() { return direccion; }

    public void setDireccion(String direccion) { this.direccion = direccion; }*/




}
