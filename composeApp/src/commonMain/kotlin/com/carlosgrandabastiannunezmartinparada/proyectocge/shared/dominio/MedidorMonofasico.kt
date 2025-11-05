package com.carlosgrandabastiannunezmartinparada.proyectocge.shared.dominio

class MedidorMonofasico(
    id: String,
    createdAt: Date,
    updatedAt: Date,
    codigo: String,
    direccionSuministro: String,
    activo: Boolean,
    cliente: Cliente,
    private var potenciaMaxKw: Double
) : Medidor(
    id = id,
    createdAt = createdAt,
    updatedAt = updatedAt,
    codigo = codigo,
    direccionSuministro = direccionSuministro,
    activo = activo,
    cliente = cliente,
){
    // Getters y Setters
    fun getPotenciaMaxKw(): Double = potenciaMaxKw
    fun setPotenciaMaxKw(nuevaPotenciaMaxKw: Double) {potenciaMaxKw = nuevaPotenciaMaxKw}
    // Getters y Setters
    override fun tipo(): String {
        return "Medidor Monofasico"
    }
}