package ecommerce.productos.enunciados;

public enum Subcategoria {
    // Declaro mis subcategorias provenientes de categorias padre y el nombre para mostrar
    // ACCESORIOS
    ANILLOS(Categoria.ACCESORIOS, "Anillos"),
    AROS(Categoria.ACCESORIOS, "Aros"),
    CARTERAS(Categoria.ACCESORIOS, "Carteras"),
    COSMETIQUEROS(Categoria.ACCESORIOS, "Cosmetiqueros"),
    PERFUMES(Categoria.ACCESORIOS, "Perfumes"),
    RELOJES(Categoria.ACCESORIOS, "Relojes"),

    // COSMETICOS
    ROSTROS(Categoria.COSMETICOS, "Rostros"),
    OJOS(Categoria.COSMETICOS, "Ojos"),
    LABIOS(Categoria.COSMETICOS, "Labios"),
    HERRAMIENTAS(Categoria.COSMETICOS, "Herramientas"),

    // VESTUARIO
    POLERAS(Categoria.VESTUARIO, "Poleras"),
    PANTALONES(Categoria.VESTUARIO, "Pantalones"),
    CONJUNTOS(Categoria.VESTUARIO, "Conjuntos"),
    VESTIDOS(Categoria.VESTUARIO, "Vestidos"),
    MATRIMONIO(Categoria.VESTUARIO, "Matrimonio"),
    ABRIGOS(Categoria.VESTUARIO, "Abrigos"),
    CALZADO(Categoria.VESTUARIO, "Calzado");

    // Declaro mis atributos donde tengo objeto enum padre de categoria y uno propio de subcategoria ambos inmutables
    private final Categoria padre;
    private final String nombre;

    // El constructor recibe dos parámetros
    Subcategoria(Categoria padre, String nombre) {
        this.padre = padre;
        this.nombre = nombre;
    }

    // Devuelve nombre de padre de categoria
    public Categoria getPadre() {
        return padre;
    }

    // Devuelve el nombre d emi subcategoria
    public String getNombre() {
        return nombre;
    }
}

