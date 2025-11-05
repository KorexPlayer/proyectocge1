package com.carlosgrandabastiannunezmartinparada.proyectocge.shared.dominio

import org.jetbrains.skiko.hostId

abstract class Medidor(
    id: String,
    createdAt: Date,
    updatedAt: Date,
    private var codigo: String,
    private var direccionSuministro: String,
    private var activo: Boolean,
    private val cliente: Cliente
) : EntidadBase(
    id = id,
    createdAt = createdAt,
    updatedAt = updatedAt
){
    open public fun tipo(): String{
        return ""
    }
}