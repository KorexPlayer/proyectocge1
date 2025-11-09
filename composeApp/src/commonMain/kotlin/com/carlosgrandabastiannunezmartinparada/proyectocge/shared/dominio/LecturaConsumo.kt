package com.carlosgrandabastiannunezmartinparada.proyectocge.shared.dominio

class LecturaConsumo(
    id: String,
    createdAt: Date,
    updatedAt: Date,
    private var idMedidor: String,
    private var anio: Int,
    private var mes: Int,
    private var kwhLeidos: Double,
): EntidadBase(
    id = id,
    createdAt = createdAt,
    updatedAt = updatedAt
) {
    //Getters y Setters
    fun getIdMedidor(): String = idMedidor
    fun getAnio(): Int = anio
    fun getMes(): Int = mes
    fun getKwhLeidos(): Double = kwhLeidos
    fun setIdMedidor(nuevoIdMedidor: String) {idMedidor = nuevoIdMedidor}
    fun setAnio(nuevoAnio: Int) {anio = nuevoAnio}
    fun setMes(nuevoMes: Int) {mes = nuevoMes}
    fun setKwhLeidos(nuevoKwhLeidos: Double) {kwhLeidos = nuevoKwhLeidos}
    // Getters y Setters
    override fun toString(): String {
        return "Lectura Consumo (idMedidor='${getIdMedidor()}', anio=${getAnio()}, mes=${getMes()}, kwhLeidos=${getKwhLeidos()})"
    }
}