package excepciones;

// Excepción que se lanza cuando se intenta prestar un libro que ya está prestado.
public class LibroYaPrestadoException extends Exception {
    
    // Constructor
    public LibroYaPrestadoException(String mensaje) {
        super(mensaje);
    }
}
