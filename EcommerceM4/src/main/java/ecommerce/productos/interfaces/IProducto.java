package ecommerce.productos.interfaces;

import ecommerce.productos.enunciados.Categoria;
import ecommerce.productos.enunciados.Subcategoria;

public interface IProducto {
    // establezco la relacion con mi clase producto o mi contrato para mejor mantencion
    int getId();
    String getNombre();
    double getPrecio();
    Subcategoria getSubcategoria();
    Categoria getCategoria(); // Se obtiene a través de la subcategoría
}

