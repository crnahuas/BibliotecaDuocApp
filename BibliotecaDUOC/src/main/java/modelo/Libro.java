package modelo;

// Representa un libro con código único, título, autor y disponibilidad.
public class Libro implements Comparable <Libro> {

    private String codigo;       // Código único del libro (ej: L001)
    private String titulo;       // Título del libro
    private String autor;        // Autor del libro
    private boolean disponible;  // Estado del libro: disponible o prestado

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
    
    // Permite ordenar libros por título (TreeSet)
    @Override
    public int compareTo(Libro otro) {
        return this.titulo.compareToIgnoreCase(otro.titulo);
    }
    
    // Dos libros son iguales si tienen el mismo código y título
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (!(obj instanceof Libro)) return false;

        Libro otro = (Libro) obj;
        return codigo.equalsIgnoreCase(otro.codigo) && titulo.equalsIgnoreCase(otro.titulo);
    }
    
    // Ignora mayúsculas
    @Override
    public int hashCode() {
        return (codigo + titulo).toLowerCase().hashCode();
    }

}
