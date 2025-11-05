package com.carlosgrandabastiannunezmartinparada.proyectocge.shared.dominio

import com.carlosgrandabastiannunezmartinparada.proyectocge.shared.servicios.PdfTable

class Boleta(
    id: String,
    createdAt: Date,
    updatedAt: Date,
    private val cliente: Cliente,
    private val idCliente: String,
    private val anio: Int,
    private val mes: Int,
    private val kwhTotal: Double,
    private val detalle: TarifaDetalle,
    private val estado: EstadoBoleta
) : EntidadBase(
    id = id,
    createdAt = createdAt,
    updatedAt = updatedAt
){
}