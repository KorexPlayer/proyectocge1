package com.carlosgrandabastiannunezmartinparada.proyectocge.shared.dominio

class MedidorMonofasico(
    id: String,
    createdAt: Date,
    updatedAt: Date,
    codigo: String,
    direccionSuministro: String,
    activo: Boolean,
    cliente: Cliente,
    private val potenciaMaxKw: Double
) : Medidor(
    id = id,
    createdAt = createdAt,
    updatedAt = updatedAt,
    codigo = codigo,
    direccionSuministro = direccionSuministro,
    activo = activo,
    cliente = cliente
){
    override public fun tipo(): String {
        return ""
    }
}