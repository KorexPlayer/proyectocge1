package com.carlosgrandabastiannunezmartinparada.proyectocge.shared.dominio

abstract class EntidadBase(
    public val id: String,
    public val createdAt: Date,
    public var updatedAt: Date,
) {
}