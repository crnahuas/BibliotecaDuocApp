package cl.bibliotecaduoc.main;

import logica.Biblioteca;
import java.util.ArrayList;
import java.util.HashMap;
import modelo.Libro;
import excepciones.LibroNoEncontradoException;
import excepciones.LibroYaPrestadoException;
import java.util.InputMismatchException;
import modelo.Usuario;
import java.util.Scanner;

public class BibliotecaDuoc {

    public static void main(String[] args) {
        Biblioteca biblioteca = new Biblioteca();
        Scanner scanner = new Scanner(System.in);
        boolean salir = false;

        System.out.println("=== Bienvenido a la Biblioteca DuocUC ===");

        while (!salir) {
            System.out.println("\nMenú:");
            System.out.println("1. Registrar usuario");
            System.out.println("2. Lista de libros");
            System.out.println("3. Buscar libro");
            System.out.println("4. Prestar libro");
            System.out.println("5. Devolver libro");
            System.out.println("6. libros prestados");
            System.out.println("7. Exportar libro por usuario");
            System.out.println("8. Exportar lista usuarios");
            System.out.println("9. Cargar lista usuarios");
            System.out.println("10. Exportar lista libros");
            System.out.println("11. Cargar lista libros");
            System.out.println("12. Salir");
            System.out.print("Seleccione una opción: ");

            try {
                int opcion = scanner.nextInt();
                scanner.nextLine(); // Limpiar buffer

                switch (opcion) {
                    case 1:
                        System.out.print("Ingrese RUT del usuario (ej: 12345678-9 o 12.345.678-9): ");
                        String rut = scanner.nextLine();

                        if (!Usuario.validarFormatoRUT(rut)) {
                            System.out.println("El RUT ingresado no tiene un formato válido.");
                            break;
                        }

                        System.out.print("Ingrese nombre del usuario: ");
                        String nombre = scanner.nextLine();

                        System.out.print("Ingrese apellido del usuario: ");
                        String apellido = scanner.nextLine();

                        biblioteca.registrarUsuario(rut, nombre, apellido);
                        break;

                    case 2:
                        biblioteca.listarLibros();
                        break;

                    case 3:
                        System.out.print("Ingrese el código del libro a buscar (ej: L003): ");
                        String codigoBusqueda = scanner.nextLine();

                        try {
                            Libro libro = biblioteca.buscarLibroPorCodigo(codigoBusqueda);
                            System.out.println("Libro encontrado: " + libro);
                        } catch (LibroNoEncontradoException e) {
                            System.out.println("Error: " + e.getMessage());
                        }

                        break;

                    case 4:
                        System.out.print("Ingrese el RUT del usuario que va a prestar el libro: ");
                        String rutPrestamo = scanner.nextLine();

                        System.out.print("Ingrese el código del libro a prestar (ej: L003): ");
                        String codigoPrestar = scanner.nextLine();

                        try {
                            biblioteca.prestarLibroPorCodigo(rutPrestamo, codigoPrestar);
                        } catch (LibroNoEncontradoException e) {
                            System.out.println("Error: " + e.getMessage());
                        } catch (LibroYaPrestadoException e) {
                            System.out.println("Advertencia: " + e.getMessage());
                        }
                        break;

                    case 5:
                        System.out.print("Ingrese el código del libro a devolver (ej: L003): ");
                        String codigoDevolver = scanner.nextLine();

                        try {
                            biblioteca.devolverLibroPorCodigo(codigoDevolver);
                        } catch (LibroNoEncontradoException e) {
                            System.out.println("Error: " + e.getMessage());
                        }

                        break;

                    case 6:
                        biblioteca.listarLibrosPrestados();
                        break;

                    case 7:
                        System.out.print("Ingrese el RUT del usuario: ");
                        String rutExportar = scanner.nextLine();

                        System.out.print("Ingrese el nombre del archivo para exportar: ");
                        String archivoExportar = scanner.nextLine();
                        archivoExportar = asegurarExtensionTxt(archivoExportar);

                        biblioteca.exportarLibrosPrestadosPorUsuario(rutExportar, archivoExportar);
                        break;                        
                        
                    case 8:
                        System.out.print("Ingrese nombre del archivo para guardar usuarios ordenados: ");
                        String archivoGuardar = scanner.nextLine();
                        archivoGuardar = asegurarExtensionTxt(archivoGuardar);
                        biblioteca.exportarUsuariosOrdenados(archivoGuardar);
                        break;

                    case 9:
                        System.out.print("Ingrese nombre del archivo para cargar usuarios: ");
                        String archivoCargar = scanner.nextLine();
                        archivoCargar = asegurarExtensionTxt(archivoCargar);
                        biblioteca.cargarUsuariosDesdeArchivo(archivoCargar);
                        break;

                    case 10:
                        System.out.print("Ingrese nombre del archivo para guardar libros: ");
                        String archivoLibrosGuardar = scanner.nextLine();
                        archivoLibrosGuardar = asegurarExtensionTxt(archivoLibrosGuardar);
                        biblioteca.guardarLibrosEnArchivo(archivoLibrosGuardar);
                        break;

                    case 11:
                        System.out.print("Ingrese nombre del archivo para cargar libros: ");
                        String archivoLibrosCargar = scanner.nextLine();
                        archivoLibrosCargar = asegurarExtensionTxt(archivoLibrosCargar);
                        biblioteca.cargarLibrosDesdeArchivo(archivoLibrosCargar);
                        break;

                    case 12:
                        System.out.println("¡Gracias por usar la biblioteca!");
                        salir = true;
                        break;

                    default:
                        System.out.println("Opción no válida. Intente nuevamente.");
                }

            } catch (InputMismatchException e) {
                System.out.println("Entrada no válida. Intente con un número.");
                scanner.nextLine(); // limpiar entrada errónea
            }
        }

        scanner.close();
    }
    
    // Agrega automáticamente la extensión .txt si el nombre no la tiene
    private static String asegurarExtensionTxt(String nombreArchivo) {
        if (!nombreArchivo.toLowerCase().endsWith(".txt")) {
            return nombreArchivo + ".txt";
        }
        return nombreArchivo;
    }
}
