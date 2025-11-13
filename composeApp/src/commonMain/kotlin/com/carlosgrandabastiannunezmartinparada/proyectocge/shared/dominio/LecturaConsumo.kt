package com.carlosgrandabastiannunezmartinparada.proyectocge.shared.dominio

/**
 * Representa una lectura de consumo eléctrico registrada para un medidor específico
 * en un período determinado (mes/año).
 *
 * Esta clase hereda de [EntidadBase] (ID y Fechas) y almacena el total
 * de KWh leídos en ese período.
 *
 * @param id El identificador único de la entidad (heredado).
 * @param createdAt La fecha de creación de la entidad (heredado).
 * @param updatedAt La fecha de última modificación de la entidad (heredado).
 * @param idMedidor El identificador [String] del [Medidor] al que pertenece esta lectura.
 * @param anio El año en que se realizó la lectura.
 * @param mes El mes en que se realizó la lectura (1-12).
 * @param kwhLeidos La cantidad de Kilowatts-hora registrados en la lectura.
 */
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
    /** Obtiene el ID del [Medidor] asociado. */
    fun getIdMedidor(): String = idMedidor
    /** Obtiene el año de la lectura. */
    fun getAnio(): Int = anio
    /** Obtiene el mes de la lectura. */
    fun getMes(): Int = mes
    /** Obtiene la cantidad de KWh leídos. */
    fun getKwhLeidos(): Double = kwhLeidos

    /** Actualiza el ID del [Medidor] asociado. */
    fun setIdMedidor(nuevoIdMedidor: String) {idMedidor = nuevoIdMedidor}
    /** Actualiza el año de la lectura. */
    fun setAnio(nuevoAnio: Int) {anio = nuevoAnio}
    /** Actualiza el mes de la lectura. */
    fun setMes(nuevoMes: Int) {mes = nuevoMes}
    /** Actualiza la cantidad de KWh leídos. */
    fun setKwhLeidos(nuevoKwhLeidos: Double) {kwhLeidos = nuevoKwhLeidos}
    // Getters y Setters

    /**
     * Devuelve una representación en [String] de la [LecturaConsumo].
     *
     * @return Un [String] con los detalles principales de la lectura.
     */
    override fun toString(): String {
        return "Lectura Consumo (idMedidor='${getIdMedidor()}', anio=${getAnio()}, mes=${getMes()}, kwhLeidos=${getKwhLeidos()})"
    }
}