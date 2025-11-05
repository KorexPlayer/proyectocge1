package com.carlosgrandabastiannunezmartinparada.proyectocge.shared.dominio

class LecturaConsumo(
    id: String,
    createdAt: Date,
    updatedAt: Date,
    private val idMedidor: String,
    private val medidor: Medidor,
    private val anio: Int,
    private val mes: Int,
    private val kwhLeidos: Double
): EntidadBase(
    id = id,
    createdAt = createdAt,
    updatedAt = updatedAt
) {
}