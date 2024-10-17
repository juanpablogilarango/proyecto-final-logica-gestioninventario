package org.example;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

public class Main {

    // Clase Producto
    static class Producto {
        String nombre;
        int stock;

        public Producto(String nombre, int stock) {
            this.nombre = nombre;
            this.stock = stock;
        }

        @Override
        public String toString() {
            return "Producto: " + nombre + " | Stock: " + stock;
        }
    }

    // Usuarios del sistema
    static HashMap<String, String> usuarios = new HashMap<>();
    static String usuarioActual = "";

    public static void main(String[] args) {
        // Lista de productos
        ArrayList<Producto> inventario = new ArrayList<>();
        Scanner scanner = new Scanner(System.in);
        boolean salir = false;

        // Inicializar usuarios por defecto (admin y un usuario normal)
        usuarios.put("admin", "admin123"); // Usuario administrador
        usuarios.put("user", "user123");   // Usuario normal

        // Sistema de login
        if (!login(scanner)) {
            System.out.println("Login fallido. Terminando el programa...");
            return;
        }

        // Bucle principal
        while (!salir) {
            System.out.println("\n--- Menú de Inventario ---");
            if (usuarioActual.equals("admin")) {
                System.out.println("1. Agregar Producto");
                System.out.println("2. Actualizar Stock");
                System.out.println("3. Eliminar Producto");
            }
            System.out.println("4. Mostrar Inventario");
            System.out.println("5. Buscar Producto");
            System.out.println("6. Cerrar Sesión");
            System.out.print("Selecciona una opción: ");
            int opcion = scanner.nextInt();
            scanner.nextLine();  // Limpiar el buffer

            switch (opcion) {
                case 1:
                    if (usuarioActual.equals("admin")) {
                        System.out.print("Nombre del producto: ");
                        String nombre = scanner.nextLine();
                        System.out.print("Cantidad inicial: ");
                        int cantidad = scanner.nextInt();
                        inventario.add(new Producto(nombre, cantidad));
                        System.out.println("Producto agregado!");
                    } else {
                        System.out.println("Permiso denegado. Solo administradores pueden agregar productos.");
                    }
                    break;

                case 2:
                    if (usuarioActual.equals("admin")) {
                        System.out.print("Nombre del producto a actualizar: ");
                        String nombre = scanner.nextLine();
                        boolean encontrado = false;
                        for (Producto producto : inventario) {
                            if (producto.nombre.equals(nombre)) {
                                System.out.print("Nueva cantidad: ");
                                producto.stock = scanner.nextInt();
                                encontrado = true;
                                System.out.println("Stock actualizado!");
                                break;
                            }
                        }
                        if (!encontrado) {
                            System.out.println("Producto no encontrado.");
                        }
                    } else {
                        System.out.println("Permiso denegado. Solo administradores pueden actualizar productos.");
                    }
                    break;

                case 3:
                    if (usuarioActual.equals("admin")) {
                        System.out.print("Nombre del producto a eliminar: ");
                        String nombre = scanner.nextLine();
                        boolean encontrado = false;
                        for (int i = 0; i < inventario.size(); i++) {
                            if (inventario.get(i).nombre.equals(nombre)) {
                                inventario.remove(i);
                                encontrado = true;
                                System.out.println("Producto eliminado!");
                                break;
                            }
                        }
                        if (!encontrado) {
                            System.out.println("Producto no encontrado.");
                        }
                    } else {
                        System.out.println("Permiso denegado. Solo administradores pueden eliminar productos.");
                    }
                    break;

                case 4:
                    System.out.println("--- Inventario ---");
                    for (Producto producto : inventario) {
                        System.out.println(producto);
                    }
                    break;

                case 5:
                    System.out.print("Nombre del producto a buscar: ");
                    String nombre = scanner.nextLine();
                    boolean encontrado = false;
                    for (Producto producto : inventario) {
                        if (producto.nombre.equals(nombre)) {
                            System.out.println(producto);
                            encontrado = true;
                            break;
                        }
                    }
                    if (!encontrado) {
                        System.out.println("Producto no encontrado.");
                    }
                    break;

                case 6:
                    System.out.println("Cerrando sesión...");
                    if (!login(scanner)) {
                        System.out.println("Login fallido. Terminando el programa...");
                        salir = true;
                    }
                    break;

                default:
                    System.out.println("Opción no válida. Inténtalo de nuevo.");
                    break;
            }
        }
        scanner.close();
    }

    // Método de login
    private static boolean login(Scanner scanner) {
        System.out.println("\n--- Sistema de Login ---");
        System.out.print("Usuario: ");
        String usuario = scanner.nextLine();
        System.out.print("Contraseña: ");
        String password = scanner.nextLine();

        // Validar login
        if (usuarios.containsKey(usuario) && usuarios.get(usuario).equals(password)) {
            usuarioActual = usuario;
            System.out.println("Login exitoso! Bienvenido " + usuarioActual);
            return true;
        } else {
            System.out.println("Usuario o contraseña incorrectos.");
            return false;
        }
    }
}