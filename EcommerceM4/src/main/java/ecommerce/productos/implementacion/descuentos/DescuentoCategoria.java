package ecommerce.productos.implementacion.descuentos;

import ecommerce.productos.enunciados.Categoria;
import ecommerce.productos.implementacion.Producto;
import ecommerce.productos.interfaces.IReglaDescuento;

public class DescuentoCategoria implements IReglaDescuento {
    // Declaro atributos usando objeto categoria
    private Categoria categoria;
    private double porcentaje;

    // Constructor
    public DescuentoCategoria(Categoria cat, double porc) {
        this.categoria = cat;
        this.porcentaje = porc;
    }

    // Devuelve si mi producto corresponde a la categoria
    @Override
    public boolean aplica(Producto p) {
        return p.getSubcategoria().getPadre() == this.categoria;
    }
    // Devuelve el numero decimal que corresponde al porcentaje
    @Override public double getPorcentaje() {
        return porcentaje;
    }
    // Devuelve una frase de promocion con el nombre categoria y el porcentaje aplicado en numero entero no decimal
    @Override public String getDescripcion() {
        return "Promoción " + categoria.getNombre() + " (" + (porcentaje*100) + "%)";
    }
}
