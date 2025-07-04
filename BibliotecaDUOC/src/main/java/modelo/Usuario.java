package modelo;

// Clase que representa a un usuario de la biblioteca
public class Usuario implements Comparable<Usuario> {

    private String rut;
    private String nombre;
    private String apellido;

    // Constructor principal del usuario
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

    // Representación de usuario
    @Override
    public String toString() {
        return nombre + " " + apellido + " (RUT: " + rut + ")";
    }

    // Dos usuarios son iguales si tienen el mismo RUT
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (!(obj instanceof Usuario)) return false;
        Usuario otro = (Usuario) obj;
        return this.rut.equalsIgnoreCase(otro.rut);
    }

    // Ignora mayúsculas
    @Override
    public int hashCode() {
        return rut.toLowerCase().hashCode();
    }

    // Permite ordenar usuarios, Apellido primero, luego nombre
    @Override
    public int compareTo(Usuario otro) {
        int comparacionApellido = this.apellido.compareToIgnoreCase(otro.apellido);
        if (comparacionApellido != 0) {
            return comparacionApellido;
        }
        return this.nombre.compareToIgnoreCase(otro.nombre);
    }
} 
