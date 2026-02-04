package ecommerce.productos.interfaces;

import ecommerce.productos.implementacion.Producto;

public interface IReglaDescuento {
    boolean aplica(Producto p);
    double getPorcentaje();
    String getDescripcion();
}
