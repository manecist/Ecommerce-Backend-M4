package ecommerce.tienda;

import ecommerce.carrito.CantidadInvalidaException;
import ecommerce.carrito.CarritoService;
import ecommerce.carrito.Orden;
import ecommerce.productos.enunciados.Categoria;
import ecommerce.productos.enunciados.Subcategoria;
import ecommerce.productos.implementacion.Producto;
import ecommerce.productos.implementacion.descuentos.DescuentoCategoria;
import ecommerce.productos.implementacion.descuentos.DescuentoManager;
import ecommerce.productos.implementacion.descuentos.DescuentoPorMonto;
import ecommerce.productos.implementacion.descuentos.DescuentoSubcategoria;
import ecommerce.productos.implementacion.repositorio.ProductoRepository;
import ecommerce.productos.interfaces.IProductoRepository;

import java.util.List;
import java.util.Scanner;

public class TiendaApp {
    // Dependencias
    private static final IProductoRepository repo = new ProductoRepository();
    static final DescuentoManager descManager = new DescuentoManager();
    private static final CarritoService carrito = new CarritoService();
    private static final TiendaService tiendaService = new TiendaService(repo, descManager);
    private static final Scanner sc = new Scanner(System.in);

    public static void main(String[] args) {
        int opcion; // funciona como boton de inicio
        do {
            System.out.println("========= MAGICAL STORE =========");
            System.out.println("1) ADMIN"); // si lo escoges veras menuadmin
            System.out.println("2) USUARIO"); // si lo escoges veras menuusuario
            System.out.println("0) SALIR"); // se cierra programa
            System.out.print("Seleccione una opción: ");
            opcion = leerEntero(); // lee opcion
            System.out.println(); // salto linea

            switch (opcion) {
                case 1 -> menuAdmin();
                case 2 -> menuUsuario();
                case 0 -> System.out.println("\nCerrando sistema... ¡Hasta pronto!");
                default -> System.out.println("\nOpción no válida.");
            }
            System.out.println();
        } while (opcion != 0);
    }
    // ================= MENÚ ADMINISTRADOR =================
    private static void menuAdmin() {
        int op;
        do {
            System.out.println("--- PANEL DE CONTROL (ADMIN) ---");
            System.out.println("1. Listar Productos (ID, NOMBRE, PRECIO) ");
            System.out.println("2. Buscar Producto");
            System.out.println("3. Crear Producto");
            System.out.println("4. Editar Producto");
            System.out.println("5. Eliminar Producto");
            System.out.println("6. Gestionar Descuentos (ROTATIVOS)");
            System.out.println("7. Mostrar historial de ventas");
            System.out.println("0. Volver al Menú Principal");
            System.out.print("Seleccione: ");
            op = leerEntero();
            System.out.println();

            switch (op) {
                case 1 -> mostrarCatalogoConOrden(); // muestra en orden de ID, nombre A -> Z, precio mayor/menor o menor/mayor
                case 2 -> buscarProductosAdmin(); // busqueda por id, nombre, cat, subcat
                case 3 -> crearProductoAdmin(); // crea nuevo producto
                case 4 -> editarProductoAdmin(); // editar peoductos
                case 5 -> eliminarProductoAdmin(); // elimina productos
                case 6 -> menuGestionDescuentos(); // gestiona descuentos
                case 7 -> mostrarHistorialVentas(); // muestra historial de ventas

            }
        } while (op != 0);
    }

    // ================= MENÚ USUARIO =================
    private static void menuUsuario() {
        int op;
        do {
            System.out.println("--- PORTAL DE COMPRAS (USUARIO) ---");
            System.out.println("1. Listar Productos (ID/ nombre / precio)"); //  solo para listar
            System.out.println("2. Buscar Productos (por nombre/cat/sub)"); // solo para buscar
            System.out.println("3. Agregar al Carrito"); // agrega al carrito
            System.out.println("4. Ver Carrito y Descuentos Activos"); // ve carrito y desct
            System.out.println("5. Quitar del carrito"); // vacia carrito
            System.out.println("6. CONFIRMAR COMPRA"); // confirma compra
            System.out.println("0. Volver"); // volver menu
            System.out.print("Seleccione: ");
            op = leerEntero();
            System.out.println();

            switch (op) {
                case 1 -> mostrarCatalogoConOrden(); // muestra en orden de ID, nombre A -> Z, precio mayor/menor o menor/mayor
                case 2 -> buscarProductosUsuario(); // busqueda por nombre, cat , subcat
                case 3 -> agregarAlCarrito(); // agrega al carrito
                case 4 -> mostrarCarritoYPromos(); // muestra carrito y dsct
                case 5 -> quitarDelCarrito(); // vacia carrito
                case 6 -> confirmarCompra(); // confirma o no compra
            }
        } while (op != 0);
    }

    // **** GESTION DESCUENTOS ****
    private static void menuGestionDescuentos() {
        System.out.println("--- GESTIÓN DE DESCUENTOS ---");
        System.out.println("1. Nuevo Descuento por CATEGORÍA");
        System.out.println("2. Nuevo Descuento por SUBCATEGORÍA");
        System.out.println("3. Nuevo Descuento por MONTO TOTAL (Mínimo)");
        System.out.println("4. Ver Descuentos Activos");
        System.out.println("5. Limpiar todas las promociones");
        System.out.println("0. Volver");
        System.out.print("Seleccione: ");
        System.out.println();
        int op = leerEntero();
        System.out.println();

        switch (op) {
            case 1 -> { // Descuento por Categoría
                System.out.println("--- Categorías ---");
                for (Categoria cat : Categoria.values()) {
                    System.out.println(cat.ordinal() + ". " + cat.getNombre()); // listado cat
                }
                System.out.println("Seleccione: ");
                int catIdx = leerEntero(); // lee opcion
                System.out.println();
                if (catIdx >= 0 && catIdx < Categoria.values().length) {
                    Categoria seleccionada = Categoria.values()[catIdx];
                    System.out.print("Ingrese porcentaje (ej: 0.15 para 15%): ");
                    double porc = leerDouble();
                    System.out.println();
                    descManager.activarDescuento(new DescuentoCategoria(seleccionada, porc));
                    System.out.println("\n¡Éxito! Descuento aplicado a " + seleccionada.getNombre());
                    System.out.println();
                }
            }
            case 2 -> { // Descuento por Subcategoría
                System.out.println("--- Subcategorías ---");
                for (Subcategoria sub : Subcategoria.values()) {
                    System.out.println(sub.ordinal() + ". " + sub.getNombre()); // listado subcat
                }
                System.out.println("Seleccione: ");
                int subIdx = leerEntero(); // lee opcion
                System.out.println();
                if (subIdx >= 0 && subIdx < Subcategoria.values().length) {
                    Subcategoria seleccionada = Subcategoria.values()[subIdx];
                    System.out.print("Ingrese porcentaje (ej: 0.20 para 20%): ");
                    double porc = leerDouble();
                    System.out.println();
                    descManager.activarDescuento(new DescuentoSubcategoria(seleccionada, porc));
                    System.out.println("\n¡Éxito! Descuento aplicado a " + seleccionada.getNombre());
                    System.out.println();
                }
            }
            case 3 -> { // Descuento por Monto Mínimo
                System.out.print("Ingrese el monto mínimo de compra (ej: 500000): ");
                double min = leerDouble(); // valor lo lee
                System.out.println();
                System.out.print("Ingrese el porcentaje de descuento (ej: 0.05 para 5%): ");
                double porc = leerDouble(); // lee porcentaje
                System.out.println();

                // Usamos la nueva clase que creaste
                descManager.activarDescuento(new DescuentoPorMonto(min, porc));
                System.out.println("\n¡Éxito! Descuento global por monto activado.");
                System.out.println();
            }
            case 4 -> {
                System.out.println("--- PROMOS ACTIVAS ---");
                descManager.listarDescuentosVigentes(); // listado de promociones activas
                System.out.println();
            }
            case 5 -> {
                descManager.limpiarPromociones();
                System.out.println("\nCatálogo de ofertas vaciado."); // elimina el listado completo
                System.out.println();
            }
            case 0 -> {
                System.out.println("\nSaliendo de gestión de descuentos...");
                System.out.println();
            }
            default -> {
                System.out.println("\nOpción no válida.");
                System.out.println();
            }
        }
    }


    // **** AGREGAR CARRITO ****
    private static void agregarAlCarrito() {
        try {
            System.out.print("Ingrese ID del producto: ");
            int id = leerEntero(); // lee numero

            // Validación: ID existente
            Producto p = repo.buscarPorId(id).orElseThrow(() ->
                    new Exception("\nEl ID del producto no existe."));

            System.out.print("Cantidad: ");
            int cant = leerEntero(); // lee cantidad
            System.out.println();

            // Validación: cantidad entera > 0
            if (cant <= 0) {
                throw new CantidadInvalidaException("\nLa cantidad debe ser mayor a cero.");
            }

            carrito.agregar(p, cant); // agrega la cantidad al carrito
            System.out.println("¡Producto añadido!");
            System.out.println();

        } catch (CantidadInvalidaException e) {
            System.out.println("\n[ERROR DE CANTIDAD]: " + e.getMessage());
        } catch (Exception e) {
            System.out.println("\n[ERROR]: " + e.getMessage());
        }
        System.out.println();
    }

    // **** MOSTRAR CARRITO Y DESCUENTOS ****
    private static void mostrarCarritoYPromos() {
        System.out.println("--- TU CARRITO ---");
        carrito.getItems().forEach(item -> System.out.printf("%-20s x%d | Subtotal: $%,.2f%n",
                item.getProducto().getNombre(), item.getCantidad(), item.getSubtotal())); // muestra listado del carrito

        System.out.printf("TOTAL BASE: $%,.2f%n", carrito.calcularTotalBase()); // precio sin descuento

        System.out.println("--- DESCUENTOS ACTIVOS CONFIGURADOS POR ADMIN ---");
        descManager.getReglasActivas().forEach(r -> System.out.println("- " + r.getDescripcion()));
        // muestra lista de dsct activas creada por admin
        System.out.println();
    }

    // **** QUITAR DEL CARRITO ****
    private static void quitarDelCarrito() {
        System.out.print("Ingrese el ID del producto que desea quitar: ");
        int id = leerEntero(); // lee numero
        System.out.println();

        // Verificamos si el producto está en el carrito antes de intentar borrarlo
        boolean existeEnCarrito = carrito.getItems().stream()
                .anyMatch(item -> item.getProducto().getId() == id);

        if (existeEnCarrito) {
            carrito.quitar(id); // Llamo al metodo storage.remove(id) de tu CarritoService
            System.out.println("\n¡Producto retirado del carrito exitosamente!"); // msj de borrado el producto
        } else {
            System.out.println("\nError: El producto con ID " + id + " no está en tu carrito."); // msj de error del producto no en carrito
        }
        System.out.println();
    }

    // **** CONFIRMAR COMPRA ****
    private static void confirmarCompra() {
        try {
            // 1. Validación Obligatoria: Carrito no vacío
            if (carrito.getItems().isEmpty()) {
                System.out.println("\n[AVISO]: El carrito está vacío. Agregue productos antes de comprar.");
                return;
            }

            // El service calcula subtotal, descuentos automáticos e IVA.
            Orden boleta = tiendaService.generarOrden(carrito);

            // 3. UI: Mostrar la Boleta Detallada
            System.out.println("========================================");
            System.out.println("          DETALLE DE SU COMPRA          ");
            System.out.println("========================================");

            // Mostramos desglose de productos para transparencia
            carrito.getItems().forEach(item ->
                    System.out.printf("- %-15s x%d  $%,.2f%n",
                            item.getProducto().getNombre(), item.getCantidad(), item.getSubtotal())
            );

            System.out.println("----------------------------------------");
            System.out.printf("SUBTOTAL BASE:     $%,12.2f%n", boleta.getSubtotal());
            System.out.printf("DESCUENTOS:       -$%,12.2f%n", boleta.getDescuento());
            System.out.printf("IVA (19%%):         $%,12.2f%n", boleta.getIva());
            System.out.println("----------------------------------------");
            System.out.printf("TOTAL A PAGAR:     $%,12.2f%n", boleta.getTotalFinal());

            // 4. Interacción: Confirmación Final
            System.out.print("\n¿Desea confirmar el pago final? (SI/NO): ");
            String respuesta = sc.next().trim().toLowerCase();

            if (respuesta.equals("si") || respuesta.equals("sí") || respuesta.equals("s")) {

                // Simulación de registro de la orden en el historial (Datos en memoria)
                tiendaService.registrarVenta(boleta);

                System.out.println("\nProcesando pago...");
                System.out.println("========================================");
                System.out.println("      " + boleta); // Muestra Ticket N°, Fecha y Total
                System.out.println("========================================");

                carrito.vaciar(); // Requerimiento: Vaciar el carrito
                System.out.println("¡Compra exitosa! Gracias por preferir Magical Store.");

            } else {
                // Si el usuario se arrepiente, lanzamos excepción para no vaciar el carrito
                throw new Exception("\nOperación cancelada por el usuario. No se realizó el cobro.");
            }

        } catch (Exception e) {
            System.out.println("\n[ERROR EN LA COMPRA]: " + e.getMessage());
            System.out.println("\nSus productos permanecen en el carrito.");
            System.out.println();
        }
    }

    private static void mostrarHistorialVentas() {
        System.out.println("\n--- HISTORIAL DE VENTAS ---");

        // Obtenemos los datos desde el Service (Capa de Lógica)
        List<Orden> ventas = tiendaService.getHistorialVentas();

        if (ventas.isEmpty()) { // si esta vacio
            System.out.println("\nNo se han registrado ventas en esta sesión.");
            System.out.println();
        } else {
            // Formato de tabla simple para mayor orden
            System.out.println("------------------------------------------------------------");
            ventas.forEach(System.out::println);
            System.out.println("------------------------------------------------------------");

            // Cálculo de métrica de negocio usando Streams
            double recaudacionTotal = ventas.stream()
                    .mapToDouble(Orden::getTotalFinal)
                    .sum();

            System.out.printf("CANTIDAD DE TRANSACCIONES: %d%n", ventas.size());
            System.out.printf("TOTAL RECAUDADO EN CAJA:   $%,.2f%n", recaudacionTotal);
            System.out.println();
        }
    }

    // ================= UTILIDADES Y VALIDACIONES =================

    // ***** MOSTRAR CATALOGO POR ORDEN DE NOMBRE, PRECIO DE PRODUCTOS *****
    private static void mostrarCatalogoConOrden() {
        System.out.println("--- OPCIONES DE CATÁLOGO ---");
        System.out.println("1. Ver todo (ID)");
        System.out.println("2. Ordenar por Nombre (A-Z)");
        System.out.println("3. Precio: Menor a Mayor");
        System.out.println("4. Precio: Mayor a Menor");
        System.out.print("Seleccione una opción: ");

        int seleccion = leerEntero();
        List<Producto> lista;

        // Aplicamos el metodo del repositorio según la elección
        switch (seleccion) {
            case 2 -> lista = TiendaApp.repo.listarOrdenadoPorNombre();
            case 3 -> lista = TiendaApp.repo.listarOrdenadoPorPrecioMenorAMayor();
            case 4 -> lista = TiendaApp.repo.listarOrdenadoPorPrecioMayorAMenor();
            default -> lista = TiendaApp.repo.listarTodos();
        }

        System.out.println("\n--- LISTADO DE PRODUCTOS ---");
        if (lista.isEmpty()) {
            System.out.println("No hay productos registrados.");
            System.out.println();
        } else {
            lista.forEach(System.out::println);
            System.out.println();
        }
    }

    // **** BUSCAR PRODUCTOS POR ID, NOMBRE, CAT, SUBCAT ****
    private static void buscarProductosAdmin() {
        System.out.print("\nIngrese ID, nombre o categoría: ");
        String criterio = sc.nextLine(); // Usamos nextLine para nombres compuestos

        List<Producto> resultados = repo.buscarPorCriterio(criterio); // utiliza la funcion de mi criterio

        if (resultados.isEmpty()) {
            System.out.println("\nNo se encontraron productos con: " + criterio);
        } else {
            System.out.println("\n--- RESULTADOS (ADMIN) ---");
            resultados.forEach(System.out::println);
        }
        System.out.println();
    }

    // **** BUSCAR PRODUCTOS POR NOMBRE, CAT O SUBCAT ****
    private static void buscarProductosUsuario() {
        System.out.print("\nIngrese nombre, categoría o subcategoría: ");
        String criterio = sc.nextLine();

        if (criterio.isBlank()) {
            System.out.println("\nBúsqueda cancelada.");
            return;
        }

        List<Producto> filtrados = repo.buscarParaUsuario(criterio); // utiliza funcion de mi criterio usuario

        if (filtrados.isEmpty()) {
            System.out.println("\nNo hay coincidencias para: " + criterio);
        } else {
            System.out.println("\n--- RESULTADOS ENCONTRADOS ---");
            filtrados.forEach(System.out::println);
        }
        System.out.println();
    }

    // **** CREAR PRODUCTOS ****
    private static void crearProductoAdmin() {
        try {
            System.out.print("Nuevo ID único: ");
            int id = leerEntero(); // agrega ID
            System.out.println();
            if (repo.buscarPorId(id).isPresent()) throw new Exception("\nEl ID ya existe."); // aviso de id existente

            System.out.print("Nombre: ");
            String nombre = sc.nextLine(); // crea nombre
            System.out.println();
            System.out.print("Precio: ");
            double precio = leerDouble(); // crea precio
            System.out.println();
            if (precio <= 0) throw new Exception("\nEl precio debe ser mayor a cero."); // arroja error excepcion

            // --- LISTAR SUBCATEGORÍAS DINÁMICAMENTE ---
            System.out.println("Seleccione la Subcategoría:");
            Subcategoria[] subs = Subcategoria.values(); // Obtenemos todas las constantes del Enum en un arreglo
            for (int i = 0; i < subs.length; i++) {
                System.out.println(i + ". " + subs[i].getNombre() + " (" + subs[i].getPadre().getNombre() + ")");
            } // muestra listado del arreglo

            System.out.print("Ingrese el número de la subcategoría: ");
            int seleccion = leerEntero(); // se escoge del listado
            System.out.println();

            // Validación de rango
            if (seleccion < 0 || seleccion >= subs.length) {
                throw new Exception("Selección de subcategoría inválida.");
            } // da error si es menor a 0 o no esta dentro del tamaño del arreglo

            Subcategoria subElegida = subs[seleccion];

            // Creación del objeto con la subcategoría real elegida
            repo.crear(new Producto(id, nombre, subElegida, precio));

            System.out.println("\n¡Producto '" + nombre + "' creado exitosamente en " + subElegida.getNombre() + " con precio " + precio + "!");

        } catch (Exception e) {
            System.out.println("\nError al crear: " + e.getMessage());
        }
        System.out.println();
    }

    // **** EDITAR PRODUCTOS ****
    private static void editarProductoAdmin() {
        System.out.print("Ingrese el ID del producto a editar: ");
        int id = leerEntero(); // lee el numero
        System.out.println();

        repo.buscarPorId(id).ifPresentOrElse(p -> {
            System.out.println("Producto encontrado: " + p);
            System.out.println("¿Qué desea editar? 1. Nombre | 2. Precio | 3. Subcategoría | 0. Cancelar");
            int opcion = leerEntero(); // escoge dentro de la lista de opciones
            System.out.println();

            switch (opcion) {
                case 1 -> {
                    System.out.print("Nuevo nombre: ");
                    p.setNombre(sc.nextLine()); // actualiza le nombre producto
                    System.out.println("Nombre actualizado.");
                    System.out.println();
                }
                case 2 -> {
                    System.out.print("Nuevo precio: ");
                    double nuevoPrecio = leerDouble(); // actualiza precio producto
                    if (nuevoPrecio > 0) {
                        p.setPrecio(nuevoPrecio); // si es mayor a 0 toma el precio
                        System.out.println("Precio actualizado.");
                        System.out.println();
                    } else {
                        System.out.println("\nError: El precio debe ser mayor a 0."); // si no da error
                    }
                }
                case 3 -> {
                    System.out.println("Seleccione nueva Subcategoría:");
                    Subcategoria[] subs = Subcategoria.values(); // amnda arreeglo de subcategoria
                    for (int i = 0; i < subs.length; i++) {
                        System.out.println(i + ". " + subs[i].getNombre());
                    }
                    int sel = leerEntero(); // se escoge
                    System.out.println();
                    if (sel >= 0 && sel < subs.length) { // dentro del 0 y el limite del arreglo el numero
                        p.setSubcategoria(subs[sel]);
                        System.out.println("Subcategoría actualizada.");
                    }
                }
            }
            // Llamamos al repo para persistir los cambios (aunque en memoria es automático)
            repo.actualizar(p);
        }, () -> System.out.println("\nError: No se encontró el producto con ID " + id)); // msj de error si no encuentra id
        System.out.println();
    }

    // **** ELIMINAR PRODUCTOS ****
    private static void eliminarProductoAdmin() {
        System.out.print("Ingrese ID a eliminar: ");
        int id = leerEntero(); // lee entero ingresado
        System.out.println();

        repo.buscarPorId(id).ifPresentOrElse( // busca por ID
                p -> {
                    // Cambiamos el mensaje a un español claro (S/N)
                    System.out.print("¿Está SEGURO de que desea eliminar el producto: " + p.getNombre() + "? (Si/No): ");
                    String respuesta = sc.nextLine(); // ingresa respuesta

                    // Validamos que sea 'S', 's', 'Si' o 'si'
                    if (respuesta.equalsIgnoreCase("S") || respuesta.equalsIgnoreCase("Si")) {
                        repo.eliminar(id); // elimina id
                        System.out.println("\n¡Producto eliminado exitosamente!"); // aviso de producto eliminado
                        System.out.println();
                    } else {
                        System.out.println("\nOperación cancelada. El producto no fue eliminado."); // si escoge no envia este msj
                    }
                },
                () -> System.out.println("\nError: No se encontró ningún producto con el ID " + id) // si no encuentra el Id arroja error
        );
    }

    // **** Para numeros enteros como ID o cantidad ****
    private static int leerEntero() {
        try {
            String entrada = sc.next(); // Lee la palabra ingresada
            return Integer.parseInt(entrada); // Intenta convertirla a número entero
        } catch (NumberFormatException e) {
            System.out.println("¡Error! Debe ingresar un número entero válido.");
            return -1; // Retorna un valor que tus switch manejarán como "default"
        } finally {
            sc.nextLine(); // Limpia el búfer para evitar que el siguiente Scanner falle
        }
    }

    // ***** Para Precios, Descuentos e IVA *****
    private static double leerDouble() {
        try {
            String entrada = sc.next();
            // Reemplazamos la coma por punto por si el usuario se equivoca
            // Así Double.parseDouble siempre recibirá el formato que entiende
            String entradaLimpia = entrada.replace(',', '.');

            return Double.parseDouble(entradaLimpia);
            // lo convierte a un double correctamente aceptado independiente de . o ,
        } catch (NumberFormatException e) {
            System.out.println("¡Error! Ingrese un número válido (ejemplo: 0.15 o 0,15).");
            return 0.0;
        } finally {
            sc.nextLine(); // Limpieza de búfer que ya tenías
        }
    }

}
