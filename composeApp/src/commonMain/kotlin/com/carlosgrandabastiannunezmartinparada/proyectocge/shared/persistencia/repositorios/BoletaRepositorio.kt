package com.carlosgrandabastiannunezmartinparada.proyectocge.shared.persistencia.repositorios

import com.carlosgrandabastiannunezmartinparada.proyectocge.shared.dominio.Boleta

/**
 * Define el contrato para un repositorio que gestiona las [Boleta].
 *
 * Establece las operaciones básicas requeridas para guardar, obtener y listar
 * boletas, abstrayendo la implementación concreta (ej. en memoria, base de datos,
 * persistencia de archivos).
 */
interface BoletaRepositorio {

    /**
     * Guarda o persiste una instancia de [Boleta].
     *
     * @param b La [Boleta] a guardar.
     * @return La [Boleta] guardada (puede ser la misma instancia o una actualizada).
     */
    fun guardar(b: Boleta): Boleta

    /**
     * Busca y devuelve una [Boleta] específica.
     *
     * La boleta se identifica por la combinación del RUT del cliente
     * y el período (año y mes) de facturación.
     *
     * @param rut El RUT del cliente asociado a la boleta.
     * @param anio El año del período de facturación.
     * @param mes El mes del período de facturación.
     * @return La [Boleta] encontrada, o `null` si no existe una boleta para esos criterios.
     */
    fun obtener(rut: String, anio: Int, mes: Int): Boleta?

    /**
     * Devuelve una lista de todas las [Boleta] asociadas a un cliente específico.
     *
     * @param rut El RUT del cliente cuyas boletas se desean listar.
     * @return Una [List] de [Boleta] (puede estar vacía si el cliente no tiene boletas).
     */
    fun listarPorCliente(rut: String): List<Boleta>

}