package ecommerce.carrito.interfaces;

import ecommerce.carrito.Orden;

import java.util.List;

public interface IOrdenRepository {
    void guardar(Orden orden);
    List<Orden> obtenerHistorial();

}
