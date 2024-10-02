package co.edu.uniquindio.gestorContactos.controlador;

import co.edu.uniquindio.gestorContactos.modelo.Contacto;
import co.edu.uniquindio.gestorContactos.modelo.GestorContactos;
import co.edu.uniquindio.gestorContactos.modelo.enums.Filtro;
import co.edu.uniquindio.gestorContactos.servicio.ServiciosGestorContactos;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import lombok.Getter;
import lombok.Setter;

import java.io.File;
import java.time.LocalDate;

@Getter
@Setter
public class ControladorPrincipal implements ServiciosGestorContactos {

    private Contacto contacto;

    private final GestorContactos gestorContactos;

    public static ControladorPrincipal INSTANCIA;

    public ControladorPrincipal() {gestorContactos = new GestorContactos();}

    public static ControladorPrincipal getInstancia(){
        if(INSTANCIA == null){
            INSTANCIA = new ControladorPrincipal();
        }
        return INSTANCIA;
    }

    public FXMLLoader navegarVentana(String nombreArchivoFxml, String tituloVentana) throws Exception {

        // Cargar la vista
        FXMLLoader loader = new FXMLLoader(getClass().getResource(nombreArchivoFxml));
        Parent root = loader.load();

        // Crear la escena
        Scene scene = new Scene(root);

        // Crear un nuevo escenario (ventana)
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.setResizable(false);
        stage.setTitle(tituloVentana);

        // Mostrar la nueva ventana
        stage.show();

        return loader;
    }

    public void crearAlerta(String mensaje, Alert.AlertType tipo){
        Alert alert = new Alert(tipo);
        alert.setTitle("Alerta");
        alert.setHeaderText(null);
        alert.setContentText(mensaje);
        alert.showAndWait();
    }


    public void cerrarVentana(Node node) {
        Stage stage = (Stage) node.getScene().getWindow();
        stage.close();
    }

    public void abrirFileChooser(ImageView imageView) {

        // Crear un FileChooser para seleccionar la imagen
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Seleccionar imagen");

        // Filtrar los archivos que se pueden seleccionar
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Imágenes", "*.png", "*.jpg", "*.jpeg")
        );

        // Mostrar el FileChooser y obtener la imagen seleccionada
        File imagen = fileChooser.showOpenDialog(null);

        // Mostrar la imagen seleccionada en la interfaz gráfica
        if (imagen != null) {
            imageView.setImage(new javafx.scene.image.Image(imagen.toURI().toString()));
        }

    }



    @Override
    public Contacto agregarContacto(Image imagen, String nombre, String apellido, String email, String telefono, LocalDate fechaNacimiento) throws Exception {
        return gestorContactos.agregarContacto( imagen, nombre, apellido, email, telefono, fechaNacimiento);
    }


    @Override
    public void actualizarContacto(String id, Image imagen, String nombre, String apellido, String email, String telefono, LocalDate fechaNacimiento) throws Exception {
        gestorContactos.actualizarContacto(id, imagen, nombre, apellido, email, telefono, fechaNacimiento);
    }

    @Override
    public void eliminarContacto(String id) throws Exception {
        gestorContactos.eliminarContacto(id);
    }

    @Override
    public Contacto busquedaContactoPorNombre(String nombre, String apellido) throws Exception {
        return gestorContactos.busquedaContactoPorNombre(nombre, apellido);
    }

    @Override
    public Contacto busquedaContactoPorTelefono(String telefono) throws Exception {
        return gestorContactos.busquedaContactoPorTelefono(telefono);
    }

}
