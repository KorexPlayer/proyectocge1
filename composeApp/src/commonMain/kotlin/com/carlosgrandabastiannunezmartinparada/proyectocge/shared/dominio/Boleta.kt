package com.carlosgrandabastiannunezmartinparada.proyectocge.shared.dominio

import com.carlosgrandabastiannunezmartinparada.proyectocge.shared.servicios.PdfTable

/**
 * Representa una boleta o factura de un cliente para un período específico.
 *
 * Esta clase contiene la información del cliente, el período de facturación (mes/año),
 * el consumo total, el detalle de los cargos (tarifa) y el estado de la boleta.
 *
 * Hereda de [EntidadBase] (para ID y timestamps) e implementa [ExportablePDF]
 * para generar una representación de tabla para un PDF.
 *
 * @param id El identificador único de la entidad (heredado).
 * @param createdAt La fecha de creación de la entidad (heredado).
 * @param updatedAt La fecha de última modificación de la entidad (heredado).
 * @param cliente La instancia completa del objeto [Cliente] asociado a esta boleta.
 * @param idCliente El identificador [String] del cliente (usado para referencia rápida).
 * @param anio El año del período de facturación.
 * @param mes El mes del período de facturación (1-12).
 * @param kwhTotal El consumo total de energía en Kilowatts-hora.
 * @param detalle El objeto [TarifaDetalle] que contiene el desglose de costos (subtotal, iva, total).
 * @param estado El estado actual de la boleta (ej. PENDIENTE, PAGADA) usando el enum [EstadoBoleta].
 */
class Boleta(
    id: String,
    createdAt: Date,
    updatedAt: Date,
    private var cliente: Cliente,
    private var idCliente: String,
    private var anio: Int,
    private var mes: Int,
    private var kwhTotal: Double,
    private var detalle: TarifaDetalle,
    private var estado: EstadoBoleta
) : EntidadBase(
    id = id,
    createdAt = createdAt,
    updatedAt = updatedAt
), ExportablePDF {
    //Getters y Setters
    /** Obtiene la instancia del [Cliente] asociado. */
    fun getCliente(): Cliente = cliente
    /** Obtiene el ID [String] del cliente. */
    fun getIdCliente(): String = idCliente
    /** Obtiene el año de facturación. */
    fun getAnio(): Int = anio
    /** Obtiene el mes de facturación. */
    fun getMes(): Int = mes
    /** Obtiene el consumo total en KWh. */
    fun getKwhTotal(): Double = kwhTotal
    /** Obtiene el [TarifaDetalle] con el desglose de costos. */
    fun getDetalle(): TarifaDetalle = detalle
    /** Obtiene el [EstadoBoleta] actual (ej. PENDIENTE). */
    fun getEstado(): EstadoBoleta = estado
    /** Actualiza el [Cliente] asociado. */
    fun setCliente(nuevoCliente: Cliente){cliente = nuevoCliente }
    /** Actualiza el ID del cliente. */
    fun setIdCliente(nuevoIdCliente: String){ idCliente = nuevoIdCliente }
    /** Actualiza el año de facturación. */
    fun setAnio(nuevoAnio: Int){ anio = nuevoAnio }
    /** Actualiza el mes de facturación. */
    fun setMes(nuevoMes: Int){ mes = nuevoMes }
    /** Actualiza el consumo total en KWh. */
    fun setKwhTotal(nuevoKwhTotal: Double){ kwhTotal = nuevoKwhTotal }
    /** Actualiza el [TarifaDetalle] (desglose de costos). */
    fun setDetalles(nuevoDetalle: TarifaDetalle){ detalle = nuevoDetalle }
    /** Actualiza el [EstadoBoleta] (ej. a PAGADA). */
    fun setEstado(nuevoEstado: EstadoBoleta){ estado = nuevoEstado }
    //Getters y Setters

    /**
     * Genera una estructura de tabla [PdfTable] con los datos de la boleta.
     *
     * Este métodq implementa la interfaz [ExportablePDF] y formatea los
     * datos principales de la boleta (periodo, consumo, totales) en una
     * lista de cabeceras y filas para ser usados por un generador de PDF.
     *
     * @return Un objeto [PdfTable] listo para ser renderizado.
     */
    override fun toPdfString(): PdfTable {
        // Implementacion del toPdfString entregando una cabecera con dos valores y columnas que tendran los datos para agregar a este
        val headers = listOf("Datos", "Valor")
        val rows = listOf(
            listOf("Cliente ID", getIdCliente()),
            listOf("Periodo", "${getMes()}/${getAnio()}"),
            listOf("Consumo (kWh)", getKwhTotal().toString()),
            listOf("Subtotal", "${getDetalle().subtotal}"),
            listOf("Cargos", "${getDetalle().cargos}"),
            listOf("IVA (19%)", "${getDetalle().iva}"),
            listOf("Total a pagar: ", "${getDetalle().total}"))
        return PdfTable(headers, rows)
    }

    /**
     * Devuelve una representación en [String] de la instancia [Boleta].
     * Utilizado principalmente para depuración y logging.
     *
     * @return Un [String] con los valores de las propiedades de la boleta.
     */
    override fun toString(): String {
        // toString para devolver los datos que contiene la boleta.
        return "Boleta(cliente=$cliente, idCliente='$idCliente', anio=$anio, mes=$mes, kwhTotal=$kwhTotal, detalle=$detalle, estado=$estado)"
    }


}