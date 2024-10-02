package co.edu.uniquindio.gestorContactos.servicio;

import co.edu.uniquindio.gestorContactos.modelo.Contacto;
import co.edu.uniquindio.gestorContactos.modelo.enums.Filtro;
import javafx.scene.image.Image;

import java.time.LocalDate;

public interface ServiciosGestorContactos {


    Contacto agregarContacto(Image imagen, String nombre, String apellido, String email, String telefono,
                             LocalDate fechaNacimiento) throws Exception;


    void actualizarContacto(String id, Image imagen, String nombre, String apellido, String email, String telefono, LocalDate fechaNacimiento) throws Exception;

    void eliminarContacto(String id)throws Exception;


    Contacto busquedaContactoPorNombre(String nombre, String apellido) throws Exception;

    Contacto busquedaContactoPorTelefono(String telefono) throws Exception;
}
