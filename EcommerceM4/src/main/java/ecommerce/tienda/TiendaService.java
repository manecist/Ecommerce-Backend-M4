package ecommerce.tienda;

import ecommerce.carrito.CarritoItem;
import ecommerce.carrito.CarritoService;
import ecommerce.carrito.Orden;
import ecommerce.productos.implementacion.Producto;
import ecommerce.productos.implementacion.descuentos.DescuentoManager;
import ecommerce.productos.implementacion.descuentos.DescuentoPorMonto;
import ecommerce.productos.interfaces.IProductoRepository;
import ecommerce.productos.interfaces.IReglaDescuento;

import java.util.ArrayList;
import java.util.List;

public class TiendaService {
    private final IProductoRepository repo;
    private final DescuentoManager descManager;
    private final List<Orden> historialVentas = new ArrayList<>();

    // Constructor para inyectar dependencias
    public TiendaService(IProductoRepository repo, DescuentoManager descManager) {
        this.repo = repo;
        this.descManager = descManager;
    }

    // metodo de registrar boleta
    public void registrarVenta(Orden boleta) {
        historialVentas.add(boleta);
    }

    // metodo para ver historial de ventas
    public List<Orden> getHistorialVentas() {
        return new ArrayList<>(historialVentas); // Copia de seguridad
    }

    // --- NUEVO MÉTODO PARA BUSQUEDA (Requerido para Tests Mockito) ---
    public List<Producto> buscarProductosParaUsuario(String criterio) {
        return repo.buscarParaUsuario(criterio);
    }

    // generador de relacion dsctos por monto, cat y subcat
    public Orden generarOrden(CarritoService carrito) {
        double totalBase = carrito.calcularTotalBase();
        double totalAhorro = 0;

        // Obtener las reglas que el ADMIN activó
        List<IReglaDescuento> reglasActivas = descManager.getReglasActivas();

        for (IReglaDescuento regla : reglasActivas) {
            // Si la regla es de Monto Mínimo (Global)
            if (regla instanceof DescuentoPorMonto rMonto) {
                if (rMonto.aplicaAlTotal(totalBase)) {
                    totalAhorro += totalBase * rMonto.getPorcentaje();
                }
            }
            // Si la regla es de Categoría o Subcategoría (Por Producto)
            else {
                for (CarritoItem item : carrito.getItems()) {
                    if (regla.aplica(item.getProducto())) {
                        totalAhorro += item.getSubtotal() * regla.getPorcentaje();
                    }
                }
            }
        }

        double neto = totalBase - totalAhorro;
        double iva = neto * 0.19;
        double totalFinal = neto + iva;

        // resolucion final lo que devuelve
        return new Orden(totalBase, totalAhorro, iva, totalFinal);
    }
}
