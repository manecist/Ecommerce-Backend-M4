package ecommerce.carrito.repositorio;

import ecommerce.carrito.Orden;
import ecommerce.carrito.interfaces.IOrdenRepository;

import java.util.ArrayList;
import java.util.List;

public class OrdenRepository implements IOrdenRepository {
    // atributo lista
    private final List<Orden> historial = new ArrayList<>();

    @Override
    public void guardar(Orden orden) { historial.add(orden); }

    @Override
    public List<Orden> obtenerHistorial() { return new ArrayList<>(historial); }
}

