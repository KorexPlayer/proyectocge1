package com.carlosgrandabastiannunezmartinparada.proyectocge.shared.dominio
/**
 * Define el contrato para las diferentes estrategias de cálculo de tarifas eléctricas.
 *
 * Las clases que implementen esta interfaz deben proporcionar un nombre
 * identificativo y un método para calcular el costo basado en el consumo.
 */
interface Tarifa {

    /**
     * Devuelve el nombre identificador de la tarifa.
     *
     * @return Un [String] que representa el nombre (ej. "Tarifa Residencial Simple").
     */
    fun nombre(): String

    /**
     * Calcula el desglose de costos basado en una cantidad de KWh consumidos.
     *
     * @param kwh El total de Kilowatts-hora consumidos que se usarán para el cálculo.
     * @return Un objeto [TarifaDetalle] que contiene el subtotal, impuestos, cargos y total.
     */
    fun calcular(kwh: Double): TarifaDetalle

}