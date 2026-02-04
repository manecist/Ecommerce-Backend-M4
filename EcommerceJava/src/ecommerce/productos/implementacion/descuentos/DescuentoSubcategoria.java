package ecommerce.productos.implementacion.descuentos;

import ecommerce.productos.enunciados.Subcategoria;
import ecommerce.productos.implementacion.Producto;
import ecommerce.productos.interfaces.IReglaDescuento;

public class DescuentoSubcategoria implements IReglaDescuento {
    // atributos
    private Subcategoria subcategoria;
    private double porcentaje;

    // constructor
    public DescuentoSubcategoria(Subcategoria sub, double porc) {
        this.subcategoria = sub;
        this.porcentaje = porc;
    }

    // Devuelve si mi producto corresponde a la subcategoria
    @Override public boolean aplica(Producto p) {
        return p.getSubcategoria() == subcategoria;
    }
    // Devuelve el numero decimal que corresponde al porcentaje
    @Override public double getPorcentaje() {
        return porcentaje;
    }
    // Devuelve una frase de Especial con el nombre subcategoria y el porcentaje aplicado en numero entero no decimal
    @Override public String getDescripcion() {
        return "Especial " + subcategoria.getNombre() + " (" + (porcentaje*100) + "%)";
    }
}

