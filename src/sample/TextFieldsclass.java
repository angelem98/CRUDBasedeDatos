package sample;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.control.TableColumn.CellEditEvent;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

import java.sql.*;


public class TextFieldsclass extends VBox {
    OperacionesClientes opCliente;
    private TableView<Cliente> Tablacliente = new TableView<>();
    private TableColumn<Cliente,String> clmnClienteId;
    private TableColumn<Cliente,String>clmnnombre;
    private TableColumn<Cliente,String>clmnapellido;
    private TableColumn<Cliente,String>clmndireccion;
    private ObservableList<Cliente> ObvCliente = FXCollections.observableArrayList();
    private Connection connection;
    private FilteredList<Cliente> filtroCliente = new FilteredList<>(ObvCliente,e->true);

    Text txtId;
    Text txtName;
    Text txtApe;
    Text txtDir;
    TextField TFid;
    TextField TFname;
    TextField TFApe;
    TextField TFdir;
    TextField TConsult;


    public TextFieldsclass() throws SQLException {
         BorderPane root = new BorderPane();

        //cabecera
        Text txtHeader = new Text("¡Bienvenido!");
        txtHeader.setFont(Font.font(20));
        txtHeader.setFill(Color.GREEN);

         //creacion de la columnas
         clmnClienteId = new TableColumn<>("clienteId");
         clmnClienteId.setCellValueFactory(new PropertyValueFactory<Cliente,String>("clienteId"));

         clmnnombre = new TableColumn<>("nombre");
         clmnnombre.setCellValueFactory(new PropertyValueFactory<Cliente,String>("nombre"));

         clmnapellido = new TableColumn<>("apellidos");
         clmnapellido.setCellValueFactory(new PropertyValueFactory<Cliente,String>("apellidos"));

         clmndireccion = new TableColumn<>("direccion");
         clmndireccion.setCellValueFactory(new PropertyValueFactory<Cliente,String>("direccion"));


         //Textfields
         txtId = new Text("ID:");
         txtName = new Text("Nombre:");
         txtApe = new Text("Apellido:");
         txtDir = new Text("Dirección:");

         TFid = new TextField();
         TFname = new TextField();
         TFApe = new TextField();
         TFdir = new TextField();

        //Boton actuaizar Tabla
        Button btnUpdate = new Button("Re-Load Table"); // se dirige al metodo UpdateTabla que se encuentra abajo del todo
        btnUpdate.setMinWidth(85);
        btnUpdate.setOnAction(e->{ UpdateTabla(); });

         //Boton Agregar
         Button btnagregar = new Button("Agregar Usuario");
         btnagregar.setMinWidth(85);
         btnagregar.setOnAction(new EventHandler<ActionEvent>() {
             @Override
             public void handle(ActionEvent event) {
                 if(TFid.getText().isEmpty() || TFname.getText().isEmpty() || TFApe.getText().isEmpty() || TFdir.getText().isEmpty()){
                     Alert alert1 = new Alert(Alert.AlertType.INFORMATION);
                     alert1.setContentText("Por favor, de llenar todas las casillas ");
                     alert1.showAndWait();
                 }else{
                     Cliente cliente = new Cliente(Integer.parseInt(TFid.getText()),TFname.getText(),TFApe.getText(),TFdir.getText());//---------------------------------------------\
                     opCliente = new OperacionesClientes(DBManager.getConnection());                           // llama a la clase                            |
                     opCliente.insertCliente(Integer.parseInt(TFid.getText()),TFname.getText(),TFApe.getText(),TFdir.getText());   //  opcliente   donde hace la operacion insert |---estos añade a la tableviewy obtiene los datos de los textfiel
                     ObvCliente.add(cliente);                                                                                         //---------------------------------------------/
                     TFid.clear();
                     TFname.clear();
                     TFApe.clear();
                     TFdir.clear();

                 }


             }
         });

         //boton borrar
        Button btnBorrar = new Button("Borrar Usuario");
        btnBorrar.setMinWidth(90);
        btnBorrar.setOnAction(e->{BotonBorrar( );});

        //boton para consultar
        TConsult = new TextField("Consultar ");
        TConsult.setMinWidth(85);
        TConsult.setOnKeyReleased(e->{TextConsultar();});

        //boton modificar usuario
      Button btnModificar = new Button("Actualizar Usuario");
      btnModificar.setMinWidth(85);
      btnModificar.setOnAction(e->{EditCliente();});

         //Adicion de las columnas al Tableview y al panel
         Tablacliente.getColumns().addAll(clmnClienteId,clmnnombre,clmnapellido,clmndireccion);
         Tablacliente.setOnKeyReleased(k->{//para seleccionar una columna y con las flechitas de arriba y abajo muestra los datos en textfields
             if(k.getCode() == KeyCode.UP || k.getCode()==KeyCode.DOWN){
              Cliente cl = Tablacliente.getSelectionModel().getSelectedItem();
              TFid.setText(String.valueOf(cl.getClienteId()));
              TFid.setDisable(true);
              TFname.setText(cl.getNombre());
              TFApe.setText(cl.getApellidos());
              TFdir.setText(cl.getDireccion());
             }

         });

         GridPane center = new GridPane();
         center.setHgap(10);
         center.setVgap(10);
         center.setPadding(new Insets(50));

         center.add(txtHeader, 0, 0, 4, 1);
         GridPane.setHalignment(txtHeader, HPos.CENTER);
         center.add(Tablacliente,0,1,5,5);

         //textos adicion
         center.add(txtId, 0, 7);
         center.add(txtName, 1, 7);
         center.add(txtApe, 2, 7);
         center.add(txtDir, 3, 7);

         //textefields adicion
         center.add(TFid,0,8);
         center.add(TFname,1,8);
         center.add(TFApe,2,8);
         center.add(TFdir,3,8);

         //botones adicion
         VBox right = new VBox(15);
         right.setAlignment(Pos.CENTER);
         right.setPadding(new Insets(7, 100,5,5));
         right.getChildren().addAll(TConsult,btnagregar,btnBorrar,btnModificar,btnUpdate);

        root.setCenter(center);
        root.setRight(right);
         getChildren().add(root);
     }


     public void UpdateTabla(){
         ObvCliente.clear();
         try{
             connection = DBManager.getConnection();
             ResultSet rs = connection.createStatement().executeQuery("SELECT * FROM cliente");
             while(rs.next()){
                 ObvCliente.add(new Cliente(rs.getInt("clienteId"),rs.getString("nombre"),rs.getString("apellidos"),rs.getString("direccion")));
             }
         }catch(SQLException ex){
             ex.printStackTrace();
             System.out.println("SQLException:␣" + ex.getMessage());
             System.out.println("SQLState:␣" + ex.getSQLState());
             System.out.println("VendorError:␣" + ex.getErrorCode());
         }
         Tablacliente.setItems(ObvCliente);
         Tablacliente.setMaxSize(600,300);
         Tablacliente.setMinSize(200,200);


     }


     public void BotonBorrar(){
         if(TFid.getText().isEmpty() ){
             Alert alert2 = new Alert(Alert.AlertType.INFORMATION);
             alert2.setContentText("Porfavor llene el campo ID ");
         }else{
             opCliente = new OperacionesClientes(DBManager.getConnection());
             opCliente.deleteCliente(Integer.parseInt(TFid.getText()));
             TFid.clear();
         }
         UpdateTabla();
     }

     public void TextConsultar(){
       TConsult.textProperty().addListener((e,ObvCliente,NValue)->{

           filtroCliente.setPredicate((Cliente c)->{
             //String.valueOf(c.getClienteId).contains(nv);
               try{
                   return c.getClienteId() == Integer.parseInt(NValue) ;
               }catch(NumberFormatException d){
                   String nv = NValue.toLowerCase();
                   return c.getNombre().toLowerCase().contains(nv)
                           || c.getApellidos().toLowerCase().contains(nv);
               }

           });

       });
      Tablacliente.setItems(filtroCliente);
     }

     public void EditCliente(){
     

     }





}
