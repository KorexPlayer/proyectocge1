package com.carlosgrandabastiannunezmartinparada.proyectocge.shared.dominio
/**
 * Clase personalizada para almacenar y representar una fecha simple (día, mes, año).
 *
 * Esta clase se utiliza para encapsular los componentes de una fecha (ej. createdAt, updatedAt)
 * y proporciona un método [toString] para formatearla como DD/MM/AAAA.
 *
 * @param anio El componente del año de la fecha.
 * @param mes El componente del mes de la fecha.
 * @param dia El componente del día de la fecha.
 */
class Date(
    private var anio: Int,
    private var mes: Int,
    private var dia: Int,
) {
    //Getters y Setters
    /** Obtiene el año. */
    fun anioGet(): Int = anio
    /** Obtiene el mes. */
    fun mesGet(): Int = mes
    /** Obtiene el día. */
    fun diaGet(): Int = dia

    /** Actualiza el año. */
    fun anioSet(nuevoAnio: Int) { anio = nuevoAnio }
    /** Actualiza el mes. */
    fun mesSet(nuevoMes: Int) { mes = nuevoMes }
    /** Actualiza el día. */
    fun diaSet(nuevoDia: Int) { dia = nuevoDia }
    //Getters y Setters

    /**
     * Devuelve una representación en [String] de la fecha en formato DD/MM/AAAA.
     *
     * @return El [String] formateado (ej. "25/12/2025").
     */
    override fun toString(): String {
        return "${diaGet()}/${mesGet()}/${anioGet()}"
    }
}