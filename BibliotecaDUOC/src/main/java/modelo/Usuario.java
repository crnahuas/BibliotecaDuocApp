package modelo;

import java.util.Objects;

// Clase que representa a un usuario de la biblioteca.
public class Usuario implements Comparable <Usuario> {
    private String rut;
    private String nombre;
    private String apellido;

    public Usuario(String rut, String nombre, String apellido) {
        this.rut = rut;
        this.nombre = nombre;
        this.apellido = apellido;
    }
    
    // Getter y Setters
    public String getRut() {
        return rut;
    }

    public String getNombre() {
        return nombre;
    }

    public String getApellido() {
        return apellido;
    }
        
    // Valida que el RUT tenga formato correcto: XX.XXX.XXX-X o XXXXXXXX-X.
    public static boolean validarFormatoRUT(String rut) {
        return rut.matches("^\\d{1,2}\\.\\d{3}\\.\\d{3}-[\\dkK]$")
            || rut.matches("^\\d{7,8}-[\\dkK]$");
    }
    
    @Override
    public String toString() {
        return nombre + " " + apellido + " (RUT: " + rut + ")";
    }
}
