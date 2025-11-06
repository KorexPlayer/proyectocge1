package com.carlosgrandabastiannunezmartinparada.proyectocge.shared.dominio

import com.carlosgrandabastiannunezmartinparada.proyectocge.shared.servicios.PdfTable

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
    fun getCliente(): Cliente = cliente
    fun getIdCliente(): String = idCliente
    fun getAnio(): Int = anio
    fun getMes(): Int = mes
    fun getKwhTotal(): Double = kwhTotal
    fun getDetalle(): TarifaDetalle = detalle
    fun getEstado(): EstadoBoleta = estado
    fun setCliente(nuevoCliente: Cliente){cliente = nuevoCliente }
    fun setIdCliente(nuevoIdCliente: String){ idCliente = nuevoIdCliente }
    fun setAnio(nuevoAnio: Int){ anio = nuevoAnio }
    fun setMes(nuevoMes: Int){ mes = nuevoMes }
    fun setKwhTotal(nuevoKwhTotal: Double){ kwhTotal = nuevoKwhTotal }
    fun setDetalles(nuevoDetalle: TarifaDetalle){ detalle = nuevoDetalle }
    fun setEstado(nuevoEstado: EstadoBoleta){ estado = nuevoEstado }
    //Getters y Setters




    override fun toPdfString(): PdfTable {
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

    override fun toString(): String {
        return "Boleta(cliente=$cliente, idCliente='$idCliente', anio=$anio, mes=$mes, kwhTotal=$kwhTotal, detalle=$detalle, estado=$estado)"
    }


}