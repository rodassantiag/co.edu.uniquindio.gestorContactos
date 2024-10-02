package co.edu.uniquindio.gestorContactos.modelo;

import javafx.scene.image.Image;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@Builder
@AllArgsConstructor
public class Contacto {
    private Image imagen;
    private String id, nombre, apellido, email, telefono;
    private LocalDate fechaNacimiento;
}
