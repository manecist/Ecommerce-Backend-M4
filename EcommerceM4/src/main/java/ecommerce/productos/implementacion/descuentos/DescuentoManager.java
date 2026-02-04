package ecommerce.productos.implementacion.descuentos;

import ecommerce.productos.implementacion.Producto;
import ecommerce.productos.interfaces.IReglaDescuento;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class DescuentoManager {
    // Centralizamos el nombre a 'reglasActivas' para mayor claridad
    private final List<IReglaDescuento> reglasActivas = new ArrayList<>();

    // --- USADO POR ADMIN ---

    public void activarDescuento(IReglaDescuento regla) {
        reglasActivas.add(regla); // agrega nuevo dsct
    }

    public void limpiarPromociones() {
        reglasActivas.clear(); // borra los dsct
        System.out.println("Sistema de promociones reiniciado.");
    }

    // --- USADO POR USUARIO (Visualización) ---

    public void listarDescuentosVigentes() {
        System.out.println("--- DESCUENTOS VIGENTES ---");
        if (reglasActivas.isEmpty()) { // si esta vacio dice:
            System.out.println("No hay promociones activas en este momento.");
        } else { // devuelve lista de dscto activo
            reglasActivas.forEach(r -> System.out.println("- " + r.getDescripcion()));
        }
    }

    // --- USADO POR EL SISTEMA  ---

    // Busca reglas que aplican a un producto
    public List<IReglaDescuento> buscarReglasParaProducto(Producto p) {
        return reglasActivas.stream() // lista en flujo de datos
                .filter(regla -> regla.aplica(p)) // filtra dsctos
                .collect(Collectors.toList()); // crea una coleccion
    }

    // Retorna todas las reglas para evaluar descuentos globales (como DescuentoPorMonto)
    public List<IReglaDescuento> getReglasActivas() {
        // Retornamos una copia para proteger la lista original (Encapsulamiento)
        return new ArrayList<>(reglasActivas);
    }
}