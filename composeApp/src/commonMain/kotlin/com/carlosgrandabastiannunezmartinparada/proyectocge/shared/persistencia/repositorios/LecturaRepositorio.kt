package com.carlosgrandabastiannunezmartinparada.proyectocge.shared.persistencia.repositorios

import com.carlosgrandabastiannunezmartinparada.proyectocge.shared.dominio.LecturaConsumo

/**
 * Define el contrato para un repositorio que gestiona [LecturaConsumo].
 *
 * Establece las operaciones básicas para registrar nuevas lecturas y
 * consultarlas por medidor o período.
 */
interface LecturaRepositorio {

    /**
     * Registra una nueva [LecturaConsumo] en el sistema.
     *
     * @param l La [LecturaConsumo] a guardar.
     * @return La [LecturaConsumo] registrada (puede ser la misma instancia o una actualizada).
     */
    fun registrar(l: LecturaConsumo): LecturaConsumo

    /**
     * Devuelve una lista de [LecturaConsumo] para un medidor específico,
     * filtrada por un período (año y mes).
     *
     * @param idMedidor El ID del medidor cuyas lecturas se buscan.
     * @param anio El año del período.
     * @param mes El mes del período.
     * @return Una [List] de [LecturaConsumo] (puede estar vacía si no hay coincidencias).
     */
    fun listarPorMedidor(idMedidor: String, anio: Int, mes: Int): List<LecturaConsumo>

    /**
     * Obtiene la última (más reciente) [LecturaConsumo] registrada para un medidor específico.
     *
     * @param idMedidor El ID del medidor del cual se busca la última lectura.
     * @return La [LecturaConsumo] más reciente, o `null` si el medidor no tiene lecturas.
     */
    fun ultimaLectura(idMedidor: String): LecturaConsumo?
}