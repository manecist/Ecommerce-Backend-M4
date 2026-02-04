package ecommerce.carrito;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Orden {

    private final int idTicket;
    private final LocalDateTime fecha;
    private final double subtotal;
    private final double descuento;
    private final double iva;
    private final double totalFinal;

    // constructor
    public Orden(double subtotal, double descuento, double iva, double totalFinal) {
        this.idTicket = (int) (Math.random() * 10000); // Folio aleatorio
        this.fecha = LocalDateTime.now();
        this.subtotal = subtotal;
        this.descuento = descuento;
        this.iva = iva;
        this.totalFinal = totalFinal;
    }

    // GETTERS: Necesarios
    public double getSubtotal() {
        return subtotal;
    }
    public double getDescuento() {
        return descuento;
    }
    public double getIva() {
        return iva;
    }
    public double getTotalFinal() {
        return totalFinal;
    }
    public int getIdTicket() {
        return idTicket;
    }

    // se reescribe la cadena de texto para creacion de ticket random con fecha y hora chilena
    @Override
    public String toString() {
        // Definimos el patrón: dd (día), MM (mes), yyyy (año)
        DateTimeFormatter formatoChile = DateTimeFormatter.ofPattern("dd - MM - yyyy   HH:mm");

        return String.format("Ticket N°: %d | Fecha: %s | TOTAL: $%,.2f",
                idTicket,
                fecha.format(formatoChile), // Aplicamos el formato aquí
                totalFinal);
    }
}

