package ecommerce.productos.enunciados;

public enum Categoria {
    // declaro mis categorias
    ACCESORIOS("Accesorios"),
    COSMETICOS("Cosméticos"),
    VESTUARIO("Vestuario");

    // creo un atributo String
    private final String nombre;

    // realizo mi constructor
    Categoria(String nombre){
        this.nombre = nombre;
    }

    // getter para objeter el nombre en los filtros o busquedas
    public String getNombre(){
        return nombre;
    }

}
