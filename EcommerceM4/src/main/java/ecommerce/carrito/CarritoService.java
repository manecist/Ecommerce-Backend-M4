package ecommerce.carrito;

import ecommerce.productos.implementacion.Producto;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class CarritoService {
    // Almacenamos el ID del producto como llave para un acceso rápido
    private final Map<Integer, CarritoItem> storage = new HashMap<>();

    // Agrega un producto o suma la cantidad si ya existe
    public void agregar(Producto p, int cantidad) {
        if (storage.containsKey(p.getId())) { // pregunta si ya esta el id en carrito
            storage.get(p.getId()).añadirCantidad(cantidad); // si ya existe el id y desea el mismo se agrega uno mas al mismo
        } else {
            storage.put(p.getId(), new CarritoItem(p, cantidad)); // pero si no esta en carrito, lo agrega nuevo
        }
    }

    // Quitar del carrito (id)
    public void quitar(int id) {
        storage.remove(id);
    }

    // Ver ítems
    public Collection<CarritoItem> getItems() {
        return storage.values();
    }

    // TOTAL base (sin descuentos todavía)
    public double calcularTotalBase() {
        return storage.values().stream()
                .mapToDouble(CarritoItem::getSubtotal)
                .sum();
    }

    // Vaciar el carrito tras la compra
    public void vaciar() {
        storage.clear();
    }
}
