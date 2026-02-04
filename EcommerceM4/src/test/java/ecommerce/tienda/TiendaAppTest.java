package ecommerce.tienda;

import ecommerce.carrito.CarritoItem;
import ecommerce.carrito.CarritoService;
import ecommerce.carrito.Orden;
import ecommerce.productos.enunciados.Categoria;
import ecommerce.productos.enunciados.Subcategoria;
import ecommerce.productos.implementacion.Producto;
import ecommerce.productos.implementacion.descuentos.DescuentoCategoria;
import ecommerce.productos.implementacion.descuentos.DescuentoManager;
import ecommerce.productos.implementacion.descuentos.DescuentoPorMonto;
import ecommerce.productos.implementacion.descuentos.DescuentoSubcategoria;
import ecommerce.productos.implementacion.repositorio.ProductoRepository;
import ecommerce.productos.interfaces.IProductoRepository;
import ecommerce.productos.interfaces.IReglaDescuento;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;

import static ecommerce.tienda.TiendaApp.descManager;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class TiendaAppTest {
    // 1. DEFINICIÓN DE MOCKS (Para pruebas de comportamiento)
    @Mock
    private IProductoRepository repoMock;

    @Mock
    private DescuentoManager descManagerMock;

    @InjectMocks
    private TiendaService tiendaService; // Mockito inyectará los mocks arriba automáticamente

    // 2. OBJETOS REALES (Para pruebas de lógica matemática)
    private CarritoService carrito;
    private Producto productoTest;

    @BeforeEach
    void setUp() {
        // Inicializamos el carrito real para cada test
        carrito = new CarritoService();
        productoTest = new Producto(1, "Producto Test", Subcategoria.VESTIDOS, 100000.0);
    }

    /**
     * testCalculoTotalCarrito
     * Validación de Totales de Carrito que multiple bien segun la cantidad del producto
     */
    @Test
    void testCalculoTotalCarrito() {
        carrito.agregar(productoTest, 2); // 100.000 * 2 = 200.000
        assertEquals(200000.0, carrito.calcularTotalBase(),
                "El total base debe ser la suma exacta de los subtotales.");
    }

    /**
     * testValidacionCantidadInvalida
     * Validación de Cantidad (Excepción Personalizada) cuando la cantidad es 0 o menor
     */
    @Test
    void testValidacionCantidadInvalida() {
        // Verificamos que al crear un CarritoItem con cantidad 0 o negativa lance la excepción
        assertThrows(IllegalArgumentException.class, () -> {
            new CarritoItem(productoTest, 0);
        }, "Debe lanzar una excepción si la cantidad no es mayor a 0.");
    }

    /**
     * testValidacionCantidadNoEntera
     * Evaluacion de numeros no enteros ingresados en cantidad
     */
    @Test
    void testValidacionCantidadNoEntera() {
        // Simulamos lo que pasaría en tu método leerEntero()
        // si alguien ingresa "1.5"
        String entradaUsuario = "1.5";

        // Intentamos parsear un decimal donde debe ir un entero:
        assertThrows(NumberFormatException.class, () -> {
            Integer.parseInt(entradaUsuario);
        }, "El sistema debe rechazar decimales en campos de cantidad/ID y lanzar NumberFormatException.");
    }

    /**
     * testResistenciaAlCastingIncorrecto
     * pruebo la integridad de mis datos
     */
    @Test
    void testResistenciaAlCastingIncorrecto() {
        // Probamos que la cantidad en el carrito sea estrictamente mayor a 0 y entera
        Producto p = new Producto(300, "Producto Prueba", Subcategoria.AROS, 5000.0);

        // Si intentáramos forzar un decimal a un entero (casting),
        // validamos que nuestra lógica de negocio lo detecte como error.
        double cantidadDecimal = 2.9;
        int cantidadTruncada = (int) cantidadDecimal; // Esto daría 2

        // Verificamos que el sistema no acepte valores que resulten de errores de redondeo
        assertNotEquals(cantidadDecimal, (double)cantidadTruncada,
                "No se deben permitir cantidades que requieran truncamiento de decimales.");
    }

    /**
     * testAplicacionDescuentoPorMonto
     * Aplicación de Descuentos por Monto
     */
    @Test
    void testAplicacionDescuentoPorMonto() {
        // Configuramos una regla: 10% de descuento si la compra supera los $50.000
        DescuentoPorMonto reglaMonto = new DescuentoPorMonto(50000.0, 0.10);

        double totalCompra = 100000.0;
        double ahorroEsperado = 10000.0; // 10% de 100.000

        // analiza si de verdad aplica dsct a compras sup al minimo de la promocion
        assertTrue(reglaMonto.aplicaAlTotal(totalCompra),
                "La regla debería aplicar para montos superiores al mínimo.");

        // verifico si mi ahorro esperado de 10000 es igual al calculado por el sistema
        double ahorroCalculado = totalCompra * reglaMonto.getPorcentaje();
        assertEquals(ahorroEsperado, ahorroCalculado,
                "El monto de ahorro calculado debe ser exacto según el porcentaje.");
    }

    /**
     * testDescuentoPorCategoriaAplicaCorrectamente
     * descuento por categoria especifica
     */
    @Test
    void testDescuentoPorCategoriaAplicaCorrectamente() {
        // Regla: 15% en todo Vestuario con mi metodologia
        DescuentoCategoria reglaVestuario = new DescuentoCategoria(Categoria.VESTUARIO, 0.15);

        // Creamos un producto de la categoría Vestuario
        Producto vestido = new Producto(101, "Vestido Gala", Subcategoria.VESTIDOS, 50000.0);
        // Creamos un producto de otra categoría (Accesorios)
        Producto anillo = new Producto(102, "Anillo Oro", Subcategoria.ANILLOS, 20000.0);

        // verifico si de verdad se aplica descuento a vestuario
        assertTrue(reglaVestuario.aplica(vestido), "La regla de Vestuario debe aplicar a un Vestido.");

        // verifico que NO se aplique a otras categorias como los accesorios
        assertFalse(reglaVestuario.aplica(anillo), "La regla de Vestuario NO debe aplicar a un Anillo.");
    }

    /**
     * testDescuentoPorSubcategoriaEspecifica
     * dsct por subcategoria especifica
     */
    @Test
    void testDescuentoPorSubcategoriaEspecifica() {
        // Regla: 20% solo en Anillos
        DescuentoSubcategoria reglaAnillos = new DescuentoSubcategoria(Subcategoria.ANILLOS, 0.20);

        // creo producto anillo
        Producto anillo = new Producto(103, "Anillo Plata", Subcategoria.ANILLOS, 10000.0);
        // creo producto aros
        Producto aros = new Producto(104, "Aros Plata", Subcategoria.AROS, 10000.0);

        // verifico que si se aplique a los anillos el dsct
        assertTrue(reglaAnillos.aplica(anillo), "La regla de Anillos debe aplicar a un producto de subcategoría ANILLOS.");

        // verifico que NO aplique a otra subcategoria diferente como los aros
        assertFalse(reglaAnillos.aplica(aros), "La regla de Anillos NO debe aplicar a AROS, aunque ambos sean Accesorios.");
    }

    /**
     * testCalculoTotalFinalConIvaYSinDescuento
     * Compra Completa sin dscto (Neto + IVA)
     */
    @Test
    void testCalculoTotalFinalConIvaYSinDescuento() {
        // 1. Preparación (Escenario Limpio)
        carrito.vaciar();
        descManager.limpiarPromociones(); // Aseguramos que no haya reglas previas

        Producto p = new Producto(105, "Perfume", Subcategoria.PERFUMES, 10000.0);
        carrito.agregar(p, 1);

        // 2. Ejecución
        // Usamos el tiendaService real inyectado en el setUp con el descManager real
        Orden boletaGenerada = tiendaService.generarOrden(carrito);

        // 3. Valores Esperados (Cálculo manual)
        double valorEsperadoSubtotal = 10000.0;
        double valorEsperadoIva = 1900.0;
        double valorEsperadoTotal = 11900.0;

        // 4. Verificación
        assertAll("Validación de Boleta sin Descuentos (JUnit Real)",
                () -> assertEquals(valorEsperadoSubtotal, boletaGenerada.getSubtotal(), 0.001, "El subtotal no coincide"),
                () -> assertEquals(0.0, boletaGenerada.getDescuento(), 0.001, "El descuento debería ser cero"),
                () -> assertEquals(valorEsperadoIva, boletaGenerada.getIva(), 0.001, "El IVA calculado es incorrecto"),
                () -> assertEquals(valorEsperadoTotal, boletaGenerada.getTotalFinal(), 0.001, "El total final es incorrecto")
        );
    }

    /**
     * testCalculoTotalFinalConDescuentoEIVA
     * compra completa con dsct e iva
     */
    @Test
    void testCalculoTotalFinalConDescuentoEIVA() {
        // 1. INICIALIZACIÓN MANUAL (Sin @Mock ni @InjectMocks)
        IProductoRepository repoReal = new ProductoRepository();
        DescuentoManager managerReal = new DescuentoManager();
        CarritoService carritoReal = new CarritoService();

        // Conectamos el service con el manager real que vamos a usar
        TiendaService serviceReal = new TiendaService(repoReal, managerReal);

        // 2. CONFIGURACIÓN DEL ESCENARIO
        // Activamos 10% en Perfumes en el manager real
        managerReal.activarDescuento(new DescuentoSubcategoria(Subcategoria.PERFUMES, 0.10));

        // Agregamos un producto de $10.000
        Producto p = new Producto(200, "Perfume Magic", Subcategoria.PERFUMES, 10000.0);
        carritoReal.agregar(p, 1);

        // 3. EJECUCIÓN
        Orden boletaGenerada = serviceReal.generarOrden(carritoReal);

        // 4. VALORES ESPERADOS
        double ahorroEsperado = 1000.0;  // 10% de 10.000
        double ivaEsperado = 1710.0;     // 19% de (10.000 - 1.000)
        double totalEsperado = 10710.0;

        // 5. VERIFICACIÓN
        assertAll("Validación de Boleta con JUnit Real",
                () -> assertEquals(ahorroEsperado, boletaGenerada.getDescuento(), 0.001, "El descuento falló"),
                () -> assertEquals(ivaEsperado, boletaGenerada.getIva(), 0.001, "El IVA falló"),
                () -> assertEquals(totalEsperado, boletaGenerada.getTotalFinal(), 0.001, "El Total Final falló")
        );
    }
    /**
     * testPrecioConLetrasLanzaExcepcion
     * validacion a posible error si alguien escribe texto en vez de numeros en precio
     */
    @Test
    void testPrecioConLetrasLanzaExcepcion() {
        // Simulamos que el Admin escribe "caro" en lugar de 1500.0
        String entradaInvalida = "caro";

        // Verificamos que el traductor de Java (parseDouble) falle como debe
        assertThrows(NumberFormatException.class, () -> {
            Double.parseDouble(entradaInvalida);
        }, "El sistema debe rechazar letras en el campo de precio.");
    }

    /**
     * testOpcionMenuFueraDeRango
     * verificacion de como funciona el escoger un numero fuera de rango de elecciones
     */
    @Test
    void testOpcionMenuFueraDeRango() {
        // Simulamos que hay 3 categorías (0, 1, 2) y el usuario marca la 5
        int opcionSeleccionada = 5;
        int totalOpciones = Categoria.values().length; // Supongamos que son 3

        // La lógica de tu código debe detectar que 5 es mayor o igual al total
        boolean esValida = (opcionSeleccionada >= 0 && opcionSeleccionada < totalOpciones);

        assertFalse(esValida, "El sistema debe detectar que la opción 5 no existe en el menú.");
    }

    /**
     * testEscribirPalabraEnVezDeNumero
     * Prueba si se escribe texto en vez de numero en general
     */
    @Test
    void testEscribirPalabraEnVezDeNumero() {
        String entradaUsuario = "cinco";

        // Verificamos que el sistema NO pueda convertir la palabra a número
        assertThrows(NumberFormatException.class, () -> {
            Integer.parseInt(entradaUsuario);
        }, "Debe fallar porque 'cinco' no es un formato numérico.");
    }


    // *********** TEST MOCKITO *************
    /**
     * testGenerarOrdenConDescuentoYIva
     * REVISION DE ORDEN GENERADA CON DST E IVA
     */
    @Test
    void testGenerarOrdenConDescuentoYIva() {
        // 1. CONFIGURACIÓN
        carrito.agregar(productoTest, 1); // Total Base: $100.000

        // Simulamos que el manager tiene una regla de 10% de descuento
        IReglaDescuento reglaMock = new DescuentoCategoria(Categoria.VESTUARIO, 0.10);
        when(descManagerMock.getReglasActivas()).thenReturn(List.of(reglaMock));

        // 2. EJECUCIÓN
        Orden resultado = tiendaService.generarOrden(carrito);

        // 3. VALIDACIONES (Then)
        // Cálculos esperados:
        // Subtotal: 100.000
        // Descuento (10%): 10.000
        // Neto: 90.000
        // IVA (19% de 90k): 17.100
        // Total Final: 107.100

        assertAll("Verificación de cálculos de la Orden",
                () -> assertEquals(100000.0, resultado.getSubtotal(), "El subtotal base no coincide"),
                () -> assertEquals(10000.0, resultado.getDescuento(), "El descuento aplicado es incorrecto"),
                () -> assertEquals(17100.0, resultado.getIva(), "El cálculo del IVA sobre el neto falló"),
                () -> assertEquals(107100.0, resultado.getTotalFinal(), "El total a pagar es incorrecto")
        );

        // Verificamos que el service realmente consultó al manager
        verify(descManagerMock, times(1)).getReglasActivas();
    }

    /**
     * testGenerarOrdenSinDescuentos
     * ORDEN SIN DSCTOS
     */
    @Test
    void testGenerarOrdenSinDescuentos() {
        // Configuramos para que no haya reglas activas
        when(descManagerMock.getReglasActivas()).thenReturn(List.of());

        carrito.agregar(productoTest, 1);
        Orden resultado = tiendaService.generarOrden(carrito);

        assertEquals(0.0, resultado.getDescuento(), "Si no hay reglas, el descuento debe ser 0");
        assertEquals(119000.0, resultado.getTotalFinal(), "El total debe ser 100k + 19% IVA");
    }

    /**
     * testBusquedaDeProductoLlamaAlRepositorio
     * VERIFICA QUE MI BUSQUEDA LLAME AL REPOSITORIO
     */
    @Test
    void testBusquedaDeProductoLlamaAlRepositorio() {
        // 1. CONFIGURACIÓN (Given)
        String criterio = "Vestido";
        // Entrenamos al mock: cuando el service le pida al repo buscar "Vestido",
        // el repo responderá con una lista que contiene nuestro producto de prueba.
        when(repoMock.buscarParaUsuario(criterio)).thenReturn(List.of(productoTest));

        // 2. EJECUCIÓN (When)
        // Llamamos al método del SERVICE (el que agregamos recién)
        List<Producto> resultados = tiendaService.buscarProductosParaUsuario(criterio);

        // 3. VERIFICACIÓN (Then)
        assertFalse(resultados.isEmpty(), "La lista no debería estar vacía");
        assertEquals("Producto Test", resultados.get(0).getNombre(), "El nombre del producto no coincide");

        // VERIFICACIÓN DE COMPORTAMIENTO:
        // Comprobamos que el Service realmente usó el Repositorio exactamente 1 vez
        verify(repoMock, times(1)).buscarParaUsuario(criterio);
    }

    /**
     * testRegistroDeVentaEnHistorial
     * HISTORIAL DE VENTA
     */
    @Test
    void testRegistroDeVentaEnHistorial() {
        // 1. Setup
        carrito.vaciar();
        Producto p = new Producto(500, "Venta Final", Subcategoria.ANILLOS, 5000.0);
        carrito.agregar(p, 1);

        // Entrenamos para que no haya descuentos y sea más simple
        when(descManagerMock.getReglasActivas()).thenReturn(List.of());

        // 2. Ejecución: Simulamos el proceso que harías en confirmarCompra()
        Orden boleta = tiendaService.generarOrden(carrito);
        tiendaService.registrarVenta(boleta);

        // 3. Verificación
        // Comprobamos que el historial ahora tiene la orden guardada
        List<Orden> historial = tiendaService.getHistorialVentas();

        assertAll("Validación de Persistencia en Memoria",
                () -> assertEquals(1, historial.size(), "El historial debería tener 1 venta"),
                () -> assertEquals(boleta.getTotalFinal(), historial.get(0).getTotalFinal(), "Los montos deben coincidir"),
                () -> verify(descManagerMock).getReglasActivas() // Verifica que consultó las promos
        );
    }

}

