package co.edu.uniquindio.gestorContactos.modelo;

import co.edu.uniquindio.gestorContactos.servicio.ServiciosGestorContactos;
import javafx.scene.image.Image;
import lombok.Getter;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Random;

@Getter
public class GestorContactos implements ServiciosGestorContactos {

    ArrayList<Contacto> contactos;


    public GestorContactos() {
        contactos = new ArrayList<>();
    }


    @Override
    public Contacto agregarContacto(Image imagen, String nombre, String apellido, String email, String telefono,
                                    LocalDate fechaNacimiento) throws Exception{

        if (nombre.isBlank()){
            throw new Exception("El nombre es obligatorio");
        }

        if (obtenerContactoPorNombre(nombre,apellido) != null){
            throw new Exception("Hay un contacto creado con el mismo nombre");
        }

        if (email.isEmpty()){
            throw new Exception("El email es obligatorio");
        }

        if (telefono.isEmpty()){
            throw new Exception("El telefono es obligatorio");
        }

        if (!telefono.matches("\\d+")) {
            throw new Exception("El número de teléfono es inválido");
        }

        if (obtenerContacto(telefono) != null){
            throw new Exception("Ya ha sido creado un contacto con el mismo número");
        }

        if (fechaNacimiento == null){
            throw new Exception("El fecha de nacimienrto es obligatorio");
        }

        if (fechaNacimiento.isAfter(LocalDate.now())){
            throw new Exception("La fecha de nacimiento no puede ser mayor a la fecha actual");
        }

        Contacto contacto = Contacto.builder()
                .id(generarId())
                .imagen(imagen)
                .nombre(nombre)
                .apellido(apellido)
                .email(email)
                .telefono(telefono)
                .fechaNacimiento(fechaNacimiento)
                .build();

        contactos.add(contacto);
        return contacto;

    }

    public String generarId() {
        StringBuilder id = new StringBuilder();


        Random random = new Random();
        for (int i = 0; i < 6; i++) {
            int numero = random.nextInt(8);
            id.append(numero);
        }

        return id.toString();
    }


    public Contacto obtenerContactoId(String id){
        for (Contacto contacto : contactos) {
            if (contacto.getId().equals(id)) {
                return contacto;
            }
        }
        return null;
    }

    @Override
    public void actualizarContacto(String id, Image imagen, String nombre, String apellido, String email, String telefono,
                                   LocalDate fechaNacimiento) throws Exception {

        if (nombre.isEmpty()) {
            throw new Exception("El nombre es obligatorio");
        }

        if (obtenerContactoId(id) != null){
            Contacto contacto = obtenerContactoId(id);
            if (!contacto.getNombre().equals(nombre) && !contacto.getApellido().equals(apellido)) {
                if (obtenerContactoPorNombre(nombre, apellido) != null) {
                    throw new Exception("Hay un contacto creado con el mismo nombre");
                }
            }
        }

        if (email.isEmpty()) {
            throw new Exception("El email es obligatorio");
        }

        if (telefono.isEmpty()) {
            throw new Exception("El teléfono es obligatorio");
        }

        if (!telefono.matches("\\d+")) {
            throw new Exception("El número de teléfono es inválido");
        }

        if (obtenerContactoId(id) != null) {
            Contacto contacto = obtenerContactoId(id);
            if (!contacto.getTelefono().equals(telefono)) {
                if (obtenerContacto(telefono) != null) {
                    throw new Exception("Ya ha sido creado un contacto con el mismo número");
                }
            }
        }

        if (fechaNacimiento == null) {
            throw new Exception("La fecha de nacimiento es obligatoria");
        }

        if (fechaNacimiento.isAfter(LocalDate.now())) {
            throw new Exception("La fecha de nacimiento no puede ser mayor a la fecha actual");
        }

        for (int i = 0; i < contactos.size(); i++) {
            if (contactos.get(i).getId().equals(id)) {
                Contacto contactoActualizado = Contacto.builder()
                        .id(contactos.get(i).getId())
                        .imagen(imagen)
                        .nombre(nombre)
                        .apellido(apellido)
                        .telefono(telefono)
                        .email(email)
                        .fechaNacimiento(fechaNacimiento)
                        .build();
                contactos.set(i, contactoActualizado);
                break;
            }
        }
    }

    @Override
    public void eliminarContacto(String id)throws Exception{
        Contacto contacto = obtenerContactoId(id);
        contactos.remove(contacto);
    }



    public Contacto obtenerContacto(String telefono){
        for (Contacto contacto : contactos){
            if (contacto.getTelefono().equals(telefono)){
                return contacto;
            }
        }
        return null;
    }


    public Contacto obtenerContactoPorNombre(String nombre, String apellido){
        for (Contacto contacto : contactos){
            if (contacto.getNombre().equals(nombre) && contacto.getApellido().equals(apellido)){
                return contacto;
            }
        }
        return null;
    }

    @Override
    public Contacto busquedaContactoPorNombre(String nombre, String apellido) throws Exception {
        Contacto contacto = obtenerContactoPorNombre(nombre, apellido);

        if (contacto != null){
            return contacto;
        }else {
            throw new Exception("No existe ningún contacto con ese nombre");
        }
    }

    @Override
    public Contacto busquedaContactoPorTelefono(String telefono) throws Exception {
        Contacto contacto = obtenerContacto(telefono);

        if (contacto != null){
            return contacto;
        }else {
            throw new Exception("No existe ningún contacto con ese número de teléfono");
        }
    }
}
