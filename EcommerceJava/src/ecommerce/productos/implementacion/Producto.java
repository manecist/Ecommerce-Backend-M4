package ecommerce.productos.implementacion;

import ecommerce.productos.interfaces.IProducto;
import ecommerce.productos.enunciados.Categoria;
import ecommerce.productos.enunciados.Subcategoria;

public class Producto implements IProducto {
    private final int id; // Inmutable según requerimiento de ID único
    private String nombre; // puede cambiar
    private Subcategoria subcategoria; // puede cambiar
    private double precio; // puede variar

    // Creo constructor sin categoria, porque se extrae desde subcategoria
    public Producto(int id, String nombre, Subcategoria subcategoria, double precio) {
        // Validaciones con excepciones para evitar errores
        // si el precio es menor o igual a 0 manda la excepcion
        if (precio <= 0) {
            throw new IllegalArgumentException("El precio debe ser mayor a 0");
        }
        // si nombre es nulo o esta en blanco envia excepcion de que no puede estar vacio
        if (nombre == null || nombre.isBlank()) {
            throw new IllegalArgumentException("El nombre no puede estar vacío");
        }

        this.id = id;
        this.nombre = nombre;
        this.subcategoria = subcategoria;
        this.precio = precio;
    }

    // Implementación de la Interfaz
    @Override public int getId(){
        return id; // devuelve mi ID producto
    }
    @Override public String getNombre(){
        return nombre; // devuelve el nombre del producto
    }
    @Override public double getPrecio(){
        return precio; // devuelve el precio del producto
    }
    @Override public Subcategoria getSubcategoria(){
        return subcategoria; // devuelve la subcategoria
    }
    @Override public Categoria getCategoria(){
        return subcategoria.getPadre(); // devuelve la categoria
    }

    // Setters para el flujo ADMIN (quien puede editar)
    public void setNombre(String nombre){
        this.nombre = nombre;
    }
    public void setPrecio(double precio){
        if (precio <= 0) {
            throw new IllegalArgumentException("Precio inválido");
        }
        this.precio = precio;
    }
    public void setSubcategoria(Subcategoria subcategoria) {
        this.subcategoria = subcategoria;
    }

    @Override
    public String toString() {
        return String.format("[%d] %-18s | %-12s | %-12s | $%,10.2f",
                id, nombre, getCategoria().getNombre(), subcategoria.getNombre(), precio);
    }

}

