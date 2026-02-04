package ecommerce.carrito;

import ecommerce.productos.implementacion.Producto;

public class CarritoItem {
    // atributos
    private final Producto producto;
    private int cantidad;

    // constructor
    public CarritoItem(Producto producto, int cantidad) {
        if (cantidad <= 0) throw new IllegalArgumentException("La cantidad debe ser mayor a 0");
        this.producto = producto;
        this.cantidad = cantidad;
    }

    // Getters
    public Producto getProducto() {
        return producto; // devuelve producto
    }
    public int getCantidad() {
        return cantidad; // devuelve la cantidad de producto
    }

    // calcula el subtotal de esta línea del prcio del producto por cantidad de p
    public double getSubtotal() {
        return producto.getPrecio() * cantidad;
    }

    // Permite actualizar la cantidad si el usuario agrega el mismo producto dos veces
    public void añadirCantidad(int cant) {
        this.cantidad += cant;
    }
}
