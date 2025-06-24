package logica;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.TreeSet;
import modelo.Libro;
import modelo.Usuario;
import excepciones.LibroNoEncontradoException;
import excepciones.LibroYaPrestadoException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.BufferedReader;
import java.io.FileReader;

// Clase que administra los libros y usuarios de una biblioteca
// Clase principal que administra libros y usuarios de una biblioteca.
public class Biblioteca {

    private ArrayList<Libro> libros = new ArrayList<>();
    private ArrayList<Libro> librosPrestados = new ArrayList<>();
    private HashMap<String, ArrayList<Libro>> prestamosPorUsuario = new HashMap<>();
    private HashMap<String, Usuario> usuarios = new HashMap<>();

    // Constructor. Carga libros iniciales al crear una nueva biblioteca.
    public Biblioteca() {
        cargarLibrosIniciales();
    }

    // Carga un conjunto de libros variados para simular una biblioteca.
    private void cargarLibrosIniciales() {
        libros.add(new Libro("L001", "Java: Cómo Programar", "Paul Deitel", true));
        libros.add(new Libro("L002", "Clean Code", "Robert C. Martin", true));
        libros.add(new Libro("L003", "Estructuras de Datos en Java", "Robert Lafore", true));
        libros.add(new Libro("L004", "Programación Orientada a Objetos", "Luis Joyanes Aguilar", true));
        libros.add(new Libro("L005", "Fundamentos de Bases de Datos", "Abraham Silberschatz", true));
        libros.add(new Libro("L006", "Redes de Computadores", "Andrew S. Tanenbaum", true));
        libros.add(new Libro("L007", "Sistemas Operativos", "Andrew S. Tanenbaum", true));
        libros.add(new Libro("L008", "Inteligencia Artificial", "Stuart Russell", true));
        libros.add(new Libro("L009", "Python para Todos", "Charles Severance", true));
        libros.add(new Libro("L010", "The Pragmatic Programmer", "Andrew Hunt", true));
        libros.add(new Libro("L011", "Diseño de Bases de Datos", "Carlos Coronel", true));
        libros.add(new Libro("L012", "Desarrollo Web con HTML, CSS y JS", "Jon Duckett", true));
        libros.add(new Libro("L013", "Fullstack JavaScript", "Eric Elliott", true));
        libros.add(new Libro("L014", "Ciberseguridad: Principios", "Mark Ciampa", true));
        libros.add(new Libro("L015", "Machine Learning", "Tom M. Mitchell", true));
        libros.add(new Libro("L016", "Data Science desde cero", "Joel Grus", true));
        libros.add(new Libro("L017", "Arquitectura de Software", "Len Bass", true));
        libros.add(new Libro("L018", "DevOps Handbook", "Gene Kim", true));
        libros.add(new Libro("L019", "Ética para Ingenieros", "Martin W. Angler", true));
        libros.add(new Libro("L020", "Tecnología y Sociedad", "Manuel Castells", true));
    }

    // Muestra todos los libros disponibles en la biblioteca.
    public void listarLibros() {
        if (libros.isEmpty()) {
            System.out.println("No hay libros en la biblioteca.");
        } else {
            System.out.println("\nLibros disponibles:");
            for (Libro libro : libros) {
                System.out.println(libro);
            }
        }
    }

    // Busca un libro por su código.
    public Libro buscarLibroPorCodigo(String codigo) throws LibroNoEncontradoException {
        for (Libro libro : libros) {
            if (libro.getCodigo().equalsIgnoreCase(codigo)) {
                return libro;
            }
        }
        throw new LibroNoEncontradoException("El libro con código \"" + codigo + "\" no se encuentra en la biblioteca.");
    }

    // Permite prestar un libro si está disponible.
    public void prestarLibroPorCodigo(String rut, String codigo) throws LibroNoEncontradoException, LibroYaPrestadoException {
        if (!usuarios.containsKey(rut)) {
            System.out.println("El usuario con RUT " + rut + " no está registrado.");
            return;
        }

        Libro libro = buscarLibroPorCodigo(codigo);

        if (!libro.isDisponible()) {
            throw new LibroYaPrestadoException("El libro \"" + libro.getTitulo() + "\" ya está prestado.");
        }

        libro.setDisponible(false);

        prestamosPorUsuario.putIfAbsent(rut, new ArrayList<>());
        prestamosPorUsuario.get(rut).add(libro);

        System.out.println("Has prestado el libro: " + libro.getTitulo());
    }

    // Permite devolver un libro.
    public void devolverLibroPorCodigo(String codigo) throws LibroNoEncontradoException {
        Libro libro = buscarLibroPorCodigo(codigo);

        if (libro.isDisponible()) {
            System.out.println("El libro \"" + libro.getTitulo() + "\" ya está disponible.");
        } else {
            libro.setDisponible(true);
            System.out.println("Has devuelto el libro: " + libro.getTitulo());
        }
    }

    // Muestra todos los libros que están actualmente prestados.
    public void listarLibrosPrestados() {
        boolean hayPrestados = false;
        System.out.println("Libros actualmente prestados:");
        for (Libro libro : libros) {
            if (!libro.isDisponible()) {
                System.out.println(libro);
                hayPrestados = true;
            }
        }
        if (!hayPrestados) {
            System.out.println("No hay libros prestados en este momento.");
        }
    }

    // Permite registrar un usuario.
    public void registrarUsuario(String rut, String nombre, String apellido) {
        if (usuarios.containsKey(rut)) {
            System.out.println("El usuario con RUT " + rut + " ya está registrado.");
        } else {
            usuarios.put(rut, new Usuario(rut, nombre, apellido));
            System.out.println("Usuario registrado correctamente.");
        }
    }

    // Exporta usuarios ordenados alfabéticamente a un archivo.
    public void exportarUsuariosOrdenados(String nombreArchivo) {
        try (FileWriter escritor = new FileWriter(nombreArchivo)) {
            TreeSet<Usuario> usuariosOrdenados = new TreeSet<>(usuarios.values());
            for (Usuario usuario : usuariosOrdenados) {
                escritor.write(usuario.getRut() + "," + usuario.getNombre() + "," + usuario.getApellido() + "\n");
            }
            System.out.println("Usuarios exportados ordenadamente al archivo " + nombreArchivo);
        } catch (IOException e) {
            System.out.println("Error al exportar usuarios: " + e.getMessage());
        }
    }

    // Carga los usuarios desde archivo.
    public void cargarUsuariosDesdeArchivo(String nombreArchivo) {
        try (BufferedReader lector = new BufferedReader(new FileReader(nombreArchivo))) {
            String linea;
            while ((linea = lector.readLine()) != null) {
                String[] partes = linea.split(",");
                if (partes.length == 3) {
                    String rut = partes[0];
                    String nombre = partes[1];
                    String apellido = partes[2];
                    if (!usuarios.containsKey(rut)) {
                        usuarios.put(rut, new Usuario(rut, nombre, apellido));
                    }
                }
            }
            System.out.println("Usuarios cargados desde el archivo " + nombreArchivo);
        } catch (IOException e) {
            System.out.println("Error al cargar usuarios: " + e.getMessage());
        }
    }

    // Guarda los libros en archivo.
    public void guardarLibrosEnArchivo(String nombreArchivo) {
        try (FileWriter escritor = new FileWriter(nombreArchivo)) {
            for (Libro libro : libros) {
                escritor.write(libro.getCodigo() + "," + libro.getTitulo() + "," + libro.getAutor() + "," + libro.isDisponible() + "\n");
            }
            System.out.println("Libros guardados en el archivo " + nombreArchivo);
        } catch (IOException e) {
            System.out.println("Error al guardar libros: " + e.getMessage());
        }
    }

    // Carga los libros desde archivo.
    public void cargarLibrosDesdeArchivo(String nombreArchivo) {
        try (BufferedReader lector = new BufferedReader(new FileReader(nombreArchivo))) {
            String linea;
            libros.clear();
            while ((linea = lector.readLine()) != null) {
                String[] partes = linea.split(",");
                if (partes.length == 4) {
                    String codigo = partes[0];
                    String titulo = partes[1];
                    String autor = partes[2];
                    boolean disponible = Boolean.parseBoolean(partes[3]);
                    libros.add(new Libro(codigo, titulo, autor, disponible));
                }
            }
            System.out.println("Libros cargados desde el archivo " + nombreArchivo);
        } catch (IOException e) {
            System.out.println("Error al cargar libros: " + e.getMessage());
        }
    }

    // Exporta los libros prestados por un usuario a un archivo.
    public void exportarLibrosPrestadosPorUsuario(String rut, String nombreArchivo) {
        if (!usuarios.containsKey(rut)) {
            System.out.println("El usuario no está registrado.");
            return;
        }

        ArrayList<Libro> librosPrestados = prestamosPorUsuario.get(rut);

        if (librosPrestados == null || librosPrestados.isEmpty()) {
            System.out.println("Este usuario no tiene libros prestados.");
            return;
        }

        try (FileWriter escritor = new FileWriter(nombreArchivo)) {
            for (Libro libro : librosPrestados) {
                escritor.write(libro.getCodigo() + "," + libro.getTitulo() + "," + libro.getAutor() + "\n");
            }
            System.out.println("Libros prestados exportados al archivo " + nombreArchivo);
        } catch (IOException e) {
            System.out.println("Error al exportar libros: " + e.getMessage());
        }
    }
}
