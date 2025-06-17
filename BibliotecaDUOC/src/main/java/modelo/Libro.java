package modelo;

// Representa un libro con título, autor y disponibilidad.
public class Libro {

    private String codigo;
    private String titulo;
    private String autor;
    private boolean disponible;

    // Constructor de Libro.
    public Libro(String codigo, String titulo, String autor, boolean disponible) {
        this.codigo = codigo;
        this.titulo = titulo;
        this.autor = autor;
        this.disponible = disponible;
    }

    // Getters y Setters
    public String getCodigo() {
        return codigo;
    }

    public String getTitulo() {
        return titulo;
    }

    public String getAutor() {
        return autor;
    }

    public boolean isDisponible() {
        return disponible;
    }

    public void setDisponible(boolean disponible) {
        this.disponible = disponible;
    }

    @Override
    public String toString() {
        return "[" + codigo + "] " + titulo + " - " + autor + " | " + (disponible ? "Disponible" : "Prestado");
    }
}
