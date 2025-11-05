package com.carlosgrandabastiannunezmartinparada.proyectocge.shared.dominio

class MedidorTrifasico(
    id: String,
    createdAt: Date,
    updatedAt: Date,
    codigo: String,
    direccionSuministro: String,
    activo: Boolean,
    cliente: Cliente,
    private var potenciaMaxKw: Double,
    private var factorPotencia: Double
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
    fun getFactorPotencia(): Double = factorPotencia
    fun setFactorPotencia(nuevaFactorPotencia: Double) {factorPotencia = nuevaFactorPotencia}
    // Getters y Setters
    override fun tipo(): String {
        return "Medidor Trifasico"
    }
}