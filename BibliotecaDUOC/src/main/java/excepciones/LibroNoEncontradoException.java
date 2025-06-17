package excepciones;

// Excepción que se lanza cuando no se encuentra un libro.
public class LibroNoEncontradoException extends Exception {

    // Constructor
    public LibroNoEncontradoException(String mensaje) {
        super(mensaje); // Se lo pasa al constructor de Exception
    }
}
