package ecommerce.productos.interfaces;

import ecommerce.productos.implementacion.Producto;

import java.util.List;
import java.util.Optional;

public interface IProductoRepository {
    void crear(Producto producto);
    List<Producto> listarTodos();
    // Da la opcion sin romper codigo de que puede o no exista el producto
    Optional<Producto> buscarPorId(int id);
    List<Producto> buscarPorCriterio(String criterio);
    List<Producto> buscarParaUsuario(String criterio);
    List<Producto> listarOrdenadoPorNombre();
    List<Producto> listarOrdenadoPorPrecioMenorAMayor();
    List<Producto> listarOrdenadoPorPrecioMayorAMenor();
    boolean eliminar(int id);
    void actualizar(Producto producto);
}
