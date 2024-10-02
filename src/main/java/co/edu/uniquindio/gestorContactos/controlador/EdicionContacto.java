package co.edu.uniquindio.gestorContactos.controlador;

import co.edu.uniquindio.gestorContactos.modelo.Contacto;
import co.edu.uniquindio.gestorContactos.observador.Obsevable;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.time.LocalDate;

public class EdicionContacto {

    private final ControladorPrincipal controladorPrincipal = ControladorPrincipal.getInstancia();
    @FXML
    private ImageView imageView;
    @FXML
    private TextField txtId, txtNombre, txtApellido, txtEmail, txtTelefono;
    @FXML
    private DatePicker fechaNacimiento;

    private Obsevable observable;


    public void cargarDatosContacto(Contacto contacto) {
        if (contacto != null) {
            txtId.setText(String.valueOf(contacto.getId()));
            txtId.setVisible(false);
            txtId.setDisable(true);
            imageView.setImage(contacto.getImagen());
            txtNombre.setText(contacto.getNombre());
            txtApellido.setText(contacto.getApellido());
            txtEmail.setText(contacto.getEmail());
            txtTelefono.setText(contacto.getTelefono());
            fechaNacimiento.setValue(contacto.getFechaNacimiento());
        }
    }

    public void actualizarContacto(ActionEvent actionEvent)throws Exception{
        try {
            String id = txtId.getText();
            Image imagen = imageView.getImage();
            String nombre = txtNombre.getText();
            String apellido = txtApellido.getText();
            String correo = txtEmail.getText();
            String telefono = txtTelefono.getText();
            LocalDate fecha = fechaNacimiento.getValue();

            controladorPrincipal.actualizarContacto(id, imagen, nombre, apellido, correo, telefono, fecha);
            controladorPrincipal.crearAlerta("El contacto se ha actualizado exitosamente", Alert.AlertType.INFORMATION);
            observable.notificar();
            cerrarVentana();

        }catch (Exception e){
            controladorPrincipal.crearAlerta(e.getMessage(), Alert.AlertType.ERROR );
        }

    }

    public void eliminarContacto(ActionEvent actionEvent)throws Exception{
        try {
            String id = txtId.getText();
            controladorPrincipal.crearAlerta("El contacto se ha eliminado exitosamente", Alert.AlertType.INFORMATION);
            controladorPrincipal.eliminarContacto(id);
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

