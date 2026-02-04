package ecommerce.productos.implementacion.descuentos;

import ecommerce.productos.implementacion.Producto;
import ecommerce.productos.interfaces.IReglaDescuento;

public class DescuentoPorMonto implements IReglaDescuento {
    // creo atributos
    private double montoMinimo;
    private double porcentaje;

    public DescuentoPorMonto(double min, double porc) {
        this.montoMinimo = min;
        this.porcentaje = porc;
    }

    @Override
    public boolean aplica(Producto p) {
        return false;
    } // No aplica a productos individuales

    // Metodo para evaluar el total del carrito si es mayor al monto minitmo
    public boolean aplicaAlTotal(double totalBase) {
        return totalBase >= montoMinimo;
    }
    // devuelve porcentaje
    @Override public double getPorcentaje() {
        return porcentaje;
    }
    // devuelve una descripcion
    @Override public String getDescripcion() {
        return "Descuento por Gran Compra (Min: $" + montoMinimo + ") - " + (porcentaje*100) + "%";
    }
}
