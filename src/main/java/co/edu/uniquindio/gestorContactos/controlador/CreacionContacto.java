package co.edu.uniquindio.gestorContactos.controlador;

import co.edu.uniquindio.gestorContactos.modelo.Contacto;
import co.edu.uniquindio.gestorContactos.observador.Obsevable;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;

public class CreacionContacto {

    private final ControladorPrincipal controladorPrincipal = ControladorPrincipal.getInstancia();
    @FXML
    private ImageView imageView;
    @FXML
    private TextField txtNombre;
    @FXML
    private TextField txtApellido;
    @FXML
    private TextField txtEmail;
    @FXML
    private TextField txtTelefono;
    @FXML
    private DatePicker fechaNacimiento;

    private Obsevable observable;


    public void crearContacto(ActionEvent actionEvent)throws Exception{

        try {
            Image imagen = imageView.getImage();
            String nombre = txtNombre.getText();
            String apellido = txtApellido.getText();
            String email = txtEmail.getText();
            String telefono = txtTelefono.getText();
            LocalDate fechaCumpleanos = fechaNacimiento.getValue();


            controladorPrincipal.agregarContacto(imagen, nombre, apellido, email, telefono, fechaCumpleanos);
            controladorPrincipal.crearAlerta("El contacto se ha creado exitosamente", Alert.AlertType.INFORMATION);
            observable.notificar();
            cerrarVentana();


        }catch (Exception e){
            controladorPrincipal.crearAlerta(e.getMessage(), Alert.AlertType.ERROR);
        }
    }


    public void seleccionarImagen(){
        controladorPrincipal.abrirFileChooser(imageView);
    }


    public void cerrarVentana(){
        controladorPrincipal.cerrarVentana(fechaNacimiento);
    }

    public void inicializarObservable(Obsevable observable){
        this.observable = observable;

    }

}
