package co.edu.uniquindio.gestorContactos.controlador;

import co.edu.uniquindio.gestorContactos.modelo.Contacto;
import co.edu.uniquindio.gestorContactos.modelo.enums.Filtro;
import co.edu.uniquindio.gestorContactos.observador.Obsevable;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import java.net.URL;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;

public class PanelPrincipal implements Initializable, Obsevable {

    private final ControladorPrincipal controladorPrincipal = ControladorPrincipal.getInstancia();
    @FXML
    private ComboBox<Filtro> filtroComboBox;
    @FXML
    private TextField txtBusqueda;
    @FXML
    private TextField txtApellido;
    @FXML
    private Button btnBuscar;
    @FXML
    private Button btnAtras;
    @FXML
    private Button btnCrear;
    @FXML
    private TableColumn<Contacto, String> nombreCol;
    @FXML
    private TableColumn<Contacto, String> apellidoCol;
    @FXML
    private TableColumn<Contacto, String> telefonoCol;
    @FXML
    private TableColumn<Contacto, String> emailCol;
    @FXML
    private TableColumn<Contacto, String> cumpleanosCol;
    @FXML
    private TableView<Contacto> tablaContactos;


    public void irCreacionContacto() throws Exception {
        FXMLLoader loader = controladorPrincipal.navegarVentana("/creacionContacto.fxml", "Crear nuevo contacto");
        CreacionContacto creacionContacto = loader.getController();
        creacionContacto.inicializarObservable(this);
    }


    public void editarContactoSeleccionado() throws Exception {
        Contacto contactoSeleccionado = tablaContactos.getSelectionModel().getSelectedItem();
        if (contactoSeleccionado != null){
            irEdicionContacto(contactoSeleccionado);
        }else {
            controladorPrincipal.crearAlerta("Seleccione un Contacto", Alert.AlertType.INFORMATION);
        }
    }

    public void irEdicionContacto(Contacto contactoSeleccionado)throws Exception{
        FXMLLoader loader = controladorPrincipal.navegarVentana("/edicionContacto.fxml", "Editar contacto");
        EdicionContacto edicionContacto = loader.getController();
        edicionContacto.inicializarObservable(this);
        edicionContacto.cargarDatosContacto(contactoSeleccionado);
        btnAtras.setDisable(true);
        btnAtras.setVisible(false);
        btnCrear.setDisable(false);
        btnCrear.setVisible(true);
    }


    public void filtroBusqueda() throws Exception {

        try {

            Filtro filtro = filtroComboBox.getValue();

            if (filtro == Filtro.NOMBRE){

                txtApellido.setVisible(true);
                txtApellido.setDisable(false);
                txtBusqueda.setVisible(true);
                txtBusqueda.setDisable(false);
                txtBusqueda.setPromptText("Nombre");
                btnBuscar.setDisable(false);
                btnBuscar.setVisible(true);

            }else {
                txtApellido.setVisible(false);
                txtApellido.setDisable(true);
                txtBusqueda.setPromptText("Teléfono");
                txtBusqueda.setVisible(true);
                txtBusqueda.setDisable(false);
                btnBuscar.setDisable(false);
                btnBuscar.setVisible(true);
            }

        }catch (Exception e){
            controladorPrincipal.crearAlerta(e.getMessage(), Alert.AlertType.ERROR);
        }
    }

    public void buscarContacto() throws Exception {
        try {
            Filtro filtro = filtroComboBox.getValue();

            if (filtro == Filtro.NOMBRE) {
                String nombre = txtBusqueda.getText();
                String apellido = txtApellido.getText();
                var contactosFiltrados = controladorPrincipal.busquedaContactoPorNombre(nombre, apellido);
                limpiarCampos();
                btnAtras.setVisible(true);
                btnAtras.setDisable(false);
                btnCrear.setVisible(false);
                btnCrear.setDisable(true);
                tablaContactos.setItems(FXCollections.observableArrayList(contactosFiltrados));
            } else {
                String telefono = txtBusqueda.getText();
                var contactosFiltrados = controladorPrincipal.busquedaContactoPorTelefono(telefono);
                limpiarCampos();
                btnAtras.setVisible(true);
                btnAtras.setDisable(false);
                btnCrear.setVisible(false);
                btnCrear.setDisable(true);
                tablaContactos.setItems(FXCollections.observableArrayList(contactosFiltrados));
            }
        } catch (Exception e) {
            limpiarCampos();
            controladorPrincipal.crearAlerta(e.getMessage(), Alert.AlertType.ERROR);
        }
    }

    public void restablecerTabla() {
        try {
            tablaContactos.setItems(FXCollections.observableArrayList(controladorPrincipal.getGestorContactos().getContactos()));
            btnAtras.setVisible(false);
            btnAtras.setDisable(true);
            btnCrear.setVisible(true);
            btnCrear.setDisable(false);
        } catch (Exception e) {
            controladorPrincipal.crearAlerta(e.getMessage(), Alert.AlertType.ERROR);
        }
    }


    public void limpiarCampos(){
        txtBusqueda.clear();
        txtApellido.clear();
    }




    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        Contacto contacto = controladorPrincipal.getContacto();
        inicializarValores(contacto);

        nombreCol.setCellValueFactory(cellData ->
                new SimpleStringProperty(cellData.getValue().getNombre()));
        apellidoCol.setCellValueFactory(cellData ->
                new SimpleStringProperty(cellData.getValue().getApellido()));
        telefonoCol.setCellValueFactory(cellData ->
                new SimpleStringProperty(cellData.getValue().getTelefono()));
        emailCol.setCellValueFactory(cellData ->
                new SimpleStringProperty(cellData.getValue().getEmail()));

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");

        cumpleanosCol.setCellValueFactory(cellData ->
                new SimpleStringProperty(cellData.getValue().getFechaNacimiento().format(formatter))
        );
        btnAtras.setDisable(true);
        btnAtras.setVisible(false);
        btnBuscar.setVisible(false);
        btnBuscar.setDisable(true);
        txtBusqueda.setVisible(false);
        txtApellido.setDisable(true);
        txtApellido.setVisible(false);
        txtApellido.setDisable(true);
        filtroComboBox.setItems(FXCollections.observableArrayList(Filtro.values()));


    }



    public void inicializarValores(Contacto contacto){
        try {
            if (contacto != null){
                consultarContactos();
            }
        }catch (Exception e){
            controladorPrincipal.crearAlerta(e.getMessage(), Alert.AlertType.ERROR);
        }
    }

    public void consultarContactos(){
        tablaContactos.setItems(FXCollections.observableArrayList(controladorPrincipal.getGestorContactos().getContactos()));
    }

    @Override
    public void notificar() {
        consultarContactos();
    }
}
