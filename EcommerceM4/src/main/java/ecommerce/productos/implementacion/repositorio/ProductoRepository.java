package ecommerce.productos.implementacion.repositorio;

import ecommerce.productos.enunciados.Subcategoria;
import ecommerce.productos.implementacion.Producto;
import ecommerce.productos.interfaces.IProductoRepository;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class ProductoRepository implements IProductoRepository {
    // Declaro mi atributo de lista de productos
    private final List<Producto> productos = new ArrayList<>();

    // Constructor de mi productorepository donde digo que usaremos cargarcatalogoinicial
    public ProductoRepository() {
        cargarCatalogoInicial();
    }

    // Declaro mi catagolo de productos segun mi pagina web
    private void cargarCatalogoInicial() {
        // *** CATEGORÍA ACCESORIOS ***
        // --- SUBCATEGORIA ANILLOS ---
        productos.add(new Producto(1, "Anillo Magic", Subcategoria.ANILLOS, 1800000));
        productos.add(new Producto(2, "Anillo Sailor", Subcategoria.ANILLOS, 800000));
        productos.add(new Producto(3, "Anillos Ali", Subcategoria.ANILLOS, 200000));
        productos.add(new Producto(4, "Anillo Moon", Subcategoria.ANILLOS, 450000));

        // --- SUBCATEGORIA AROS ---
        productos.add(new Producto(5, "Aros Sailor", Subcategoria.AROS, 15000));
        productos.add(new Producto(6, "Aros Saku", Subcategoria.AROS, 22000));
        productos.add(new Producto(7, "Diges Sailor", Subcategoria.AROS, 10000 ));
        productos.add(new Producto(8, "Diges Saku", Subcategoria.AROS, 12000 ));

        // --- SUBCATEGORIA CARTERAS ---
        productos.add(new Producto(9, "Cartera Sailor", Subcategoria.CARTERAS, 32000));
        productos.add(new Producto(10, "Cartera Magic", Subcategoria.CARTERAS, 41000));
        productos.add(new Producto(11, "Cartera Cel", Subcategoria.CARTERAS, 37000));
        productos.add(new Producto(12, "Cartera Moon", Subcategoria.CARTERAS, 28000));

        // --- SUBCATEGORIA COSMETIQUEROS ---
        productos.add(new Producto(13, "Cosmetiqueros 01", Subcategoria.COSMETIQUEROS,16000));
        productos.add(new Producto(14, "Cosmetiqueros 02", Subcategoria.COSMETIQUEROS,10000));
        productos.add(new Producto(15, "Cosmetiqueros 03", Subcategoria.COSMETIQUEROS,10000));
        productos.add(new Producto(16, "Cosmetiqueros 04", Subcategoria.COSMETIQUEROS,9000));

        // --- SUBCATEGORIA PERFUMES ---
        productos.add(new Producto(17, "Perfume R", Subcategoria.PERFUMES,18000));
        productos.add(new Producto(18, "Perfume S", Subcategoria.PERFUMES,20000));
        productos.add(new Producto(19, "Perfume M", Subcategoria.PERFUMES,27000));
        productos.add(new Producto(20, "Perfume C", Subcategoria.PERFUMES,15000));

        // --- SUBCATEGORIA RELOJES ---
        productos.add(new Producto(21, "Reloj Mag", Subcategoria.RELOJES,45000));
        productos.add(new Producto(22, "Reloj Hom", Subcategoria.RELOJES,45000));
        productos.add(new Producto(23, "Reloj Sak", Subcategoria.RELOJES,33000));
        productos.add(new Producto(24, "Reloj Dar", Subcategoria.RELOJES,33000));


        // *** CATEGORÍA COSMÉTICOS ***
        // --- SUBCATEGORIA ROSTROS ---
        productos.add(new Producto(25, "Bases Col", Subcategoria.ROSTROS, 18750));
        productos.add(new Producto(26, "Conturn Mag", Subcategoria.ROSTROS, 11990));
        productos.add(new Producto(27, "Corrector M", Subcategoria.ROSTROS, 10870));
        productos.add(new Producto(28, "Polvo Mag", Subcategoria.ROSTROS, 14550));

        // --- SUBCATEGORIA OJOS ---
        productos.add(new Producto(29, "Delineadores", Subcategoria.OJOS, 8430));
        productos.add(new Producto(30, "Rimel Mag", Subcategoria.OJOS, 10990));
        productos.add(new Producto(31, "Sombra Ojos", Subcategoria.OJOS, 29380));
        productos.add(new Producto(32, "Sombra Gel", Subcategoria.OJOS, 9000));

        // --- SUBCATEGORIA LABIOS ---
        productos.add(new Producto(33, "Labial Magic", Subcategoria.LABIOS, 13000));
        productos.add(new Producto(34, "Labial Sail", Subcategoria.LABIOS, 7000));
        productos.add(new Producto(35, "Labial Sak", Subcategoria.LABIOS, 7000));
        productos.add(new Producto(36, "Labial Lapiz", Subcategoria.LABIOS, 11000));

        // --- SUBCATEGORIA LABIOS ---
        productos.add(new Producto(37, "Cepillo Mag", Subcategoria.HERRAMIENTAS, 11670));
        productos.add(new Producto(38, "Mascarilla Fac", Subcategoria.HERRAMIENTAS, 1000));
        productos.add(new Producto(39, "Secador Pelo", Subcategoria.HERRAMIENTAS, 28990));
        productos.add(new Producto(40, "Rizador Mag", Subcategoria.HERRAMIENTAS, 4800));


        // *** CATEGORÍA VESTUARIO ***
        // --- SUBCATEGORIA POLERAS ---
        productos.add(new Producto(41, "Blusa corta", Subcategoria.POLERAS, 19900));
        productos.add(new Producto(42, "Top", Subcategoria.POLERAS, 12650));
        productos.add(new Producto(43, "Corset top", Subcategoria.POLERAS, 17420));
        productos.add(new Producto(44, "Blusa larga", Subcategoria.POLERAS, 21900));

        // --- SUBCATEGORIA PANTALONES ---
        productos.add(new Producto(45, "Jeans estampado", Subcategoria.PANTALONES, 22740));
        productos.add(new Producto(46, "Faldas Magical", Subcategoria.PANTALONES, 17000));
        productos.add(new Producto(47, "Pantalones de tela", Subcategoria.PANTALONES, 33760));
        productos.add(new Producto(48, "Jeans flare", Subcategoria.PANTALONES, 22400));

        // --- SUBCATEGORIA CONJUNTOS ---
        productos.add(new Producto(49, "Conjunto lila", Subcategoria.CONJUNTOS, 41300));
        productos.add(new Producto(50, "Conjunto Magical", Subcategoria.CONJUNTOS, 72400));
        productos.add(new Producto(51, "Pijama", Subcategoria.CONJUNTOS, 22900));
        productos.add(new Producto(52, "Traje de baño", Subcategoria.CONJUNTOS, 22000));

        // --- SUBCATEGORIA VESTIDOS ---
        productos.add(new Producto(53, "Vestido ajustado", Subcategoria.VESTIDOS, 17900));
        productos.add(new Producto(54, "Vestido blangro", Subcategoria.VESTIDOS, 23000));
        productos.add(new Producto(55, "Vestido lilrose", Subcategoria.VESTIDOS, 25900));
        productos.add(new Producto(56, "Vestido Magical", Subcategoria.VESTIDOS, 28000));

        // --- SUBCATEGORIA MATRIMONIO ---
        productos.add(new Producto(57, "Traje de Hombre", Subcategoria.MATRIMONIO, 118000));
        productos.add(new Producto(58, "Vestido lilMagic", Subcategoria.MATRIMONIO, 142000));
        productos.add(new Producto(59, "Vestido Princes", Subcategoria.MATRIMONIO, 345000));
        productos.add(new Producto(60, "Hombre Princes", Subcategoria.MATRIMONIO, 345000));

        // --- SUBCATEGORIA ABRIGOS ---
        productos.add(new Producto(61, "Chaqueta", Subcategoria.ABRIGOS, 31400));
        productos.add(new Producto(62, "Blazer", Subcategoria.ABRIGOS, 44700));
        productos.add(new Producto(63, "Abrigos", Subcategoria.ABRIGOS, 73490));
        productos.add(new Producto(64, "Abrigo Largo", Subcategoria.ABRIGOS, 73490));

        // --- SUBCATEGORIA CALZADO ---
        productos.add(new Producto(65, "Zap Magic", Subcategoria.CALZADO, 82600));
        productos.add(new Producto(66, "Zap Sailor", Subcategoria.CALZADO, 79000));
        productos.add(new Producto(67, "Zap Magical H", Subcategoria.CALZADO, 81990));
        productos.add(new Producto(68, "Zapatillas", Subcategoria.CALZADO, 41580));

    }

    // *** Establezco la relacion contrato de la implementacion de interfaz
    @Override
    public void crear(Producto producto) {
        productos.add(producto); // creo nuevos productos
    }

    @Override
    public List<Producto> listarTodos() {
        // Devuelvo una copia para proteger la lista original (Encapsulamiento)
        return new ArrayList<>(productos);
    }

    //busca ID
    @Override
    public Optional<Producto> buscarPorId(int id) {
        return productos.stream() // transformo mi lista en flujo de datos
                .filter(p -> p.getId() == id) // hace filtro por id con expresion lambda, donde mi p es de producto
                .findFirst(); // se detiene la busqueda en el numero filtrado
    }

    // busca para admin id, nombre, catg o subcat
    @Override
    public List<Producto> buscarPorCriterio(String criterio) {
        String query = criterio.toLowerCase(); // convierte minusculas

        return productos.stream() // flujo datos
                .filter(p -> {
                    // Validación exacta para ID (Evita que el 5 traiga al 15 o 50)
                    boolean coincideId = String.valueOf(p.getId()).equals(query); // id

                    // Validación parcial para textos (Nombre, Cat, SubCat)
                    boolean coincideTexto = p.getNombre().toLowerCase().contains(query) || // nombre
                            p.getCategoria().getNombre().toLowerCase().contains(query) || // cat
                            p.getSubcategoria().getNombre().toLowerCase().contains(query); // subcat

                    return coincideId || coincideTexto; // muestra si coincide id o texto la busqueda
                })
                .collect(Collectors.toList()); // crea listado
    }

    // busca por nombre, cat, sucat
    @Override
    public List<Producto> buscarParaUsuario(String criterio) {
        String query = criterio.toLowerCase(); // convierte a minuscula
        return productos.stream() // flujo de datos
                .filter(p -> p.getNombre().toLowerCase().contains(query) || // nombre
                        p.getCategoria().getNombre().toLowerCase().contains(query) || // cat
                        p.getSubcategoria().getNombre().toLowerCase().contains(query)) // subcat
                .collect(Collectors.toList()); // crea listado
    }

    // ordena por nombre (A-Z)
    @Override
    public List<Producto> listarOrdenadoPorNombre() {
        List<Producto> lista = listarTodos(); // Obtenemos la copia de la lista
        lista.sort(Comparator.comparing(Producto::getNombre));
        return lista;
    }

    // Ordenar por precio (Menor a Mayor)
    @Override
    public List<Producto> listarOrdenadoPorPrecioMenorAMayor() {
        List<Producto> lista = listarTodos();
        lista.sort(Comparator.comparingDouble(Producto::getPrecio));
        return lista;
    }

    // Ordenar por precio (Mayor a Menor)
    @Override
    public List<Producto> listarOrdenadoPorPrecioMayorAMenor() {
        List<Producto> lista = listarTodos();
        // Agregamos .reversed() para invertir el orden natural
        lista.sort(Comparator.comparingDouble(Producto::getPrecio).reversed());
        return lista;
    }

    // elimina productos por id
    @Override
    public boolean eliminar(int id) {
        return productos.removeIf(p -> p.getId() == id); // removeIf elimina cualquier elemento en este caso id
    }

    // actualiza los datos
    @Override
    public void actualizar(Producto pEditado) {
        buscarPorId(pEditado.getId()).ifPresent(p -> { // el código llama al metodo que ya creamos antes y lo trae si es que existe
            p.setNombre(pEditado.getNombre()); // Toma el nuevo nombre y lo guarda en el objeto original.
            p.setPrecio(pEditado.getPrecio()); // Toma el nuevo precio y lo actualiza.
        }); // ID: No se actualiza porque es la identidad única del objeto por eso anteriormente lo eliminamos
    }


}
