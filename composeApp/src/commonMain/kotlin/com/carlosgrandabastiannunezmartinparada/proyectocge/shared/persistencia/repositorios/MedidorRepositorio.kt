package com.carlosgrandabastiannunezmartinparada.proyectocge.shared.persistencia.repositorios

import com.carlosgrandabastiannunezmartinparada.proyectocge.shared.dominio.Medidor

/**
 * Define el contrato para un repositorio que gestiona las entidades [Medidor].
 *
 * Establece las operaciones básicas de CRUD (Crear, Leer, Eliminar) para los medidores,
 * abstrayendo la implementación concreta y manejando la asociación con el cliente.
 */
interface MedidorRepositorio {

    /**
     * Crea y persiste una nueva entidad [Medidor], asociándola a un cliente.
     *
     * @param m El [Medidor] a crear (puede ser [MedidorMonofasico] o [MedidorTrifasico]).
     * @param rutCliente El RUT del [Cliente] al que se debe asociar este medidor.
     * @return El [Medidor] creado.
     */
    fun crear(m: Medidor, rutCliente: String): Medidor

    /**
     * Devuelve una lista de [Medidor] asociados a un [Cliente] específico.
     *
     * @param rut El RUT del cliente cuyos medidores se desean listar.
     * @return Una [List] de [Medidor] (puede estar vacía si el cliente no tiene medidores).
     */
    fun listarPorCliente(rut: String): List<Medidor>

    /**
     * Busca y devuelve un [Medidor] específico basándose en su código único.
     *
     * @param codigo El código (ej. número de serie) del medidor a buscar.
     * @return El [Medidor] encontrado, o `null` si no existe.
     */
    fun obtenerPorCodigo(codigo: String): Medidor?

    /**
     * Elimina un [Medidor] del repositorio basándose en su código.
     *
     * @param codigo El código del medidor a eliminar.
     * @return `true` si el medidor fue encontrado y eliminado, `false` en caso contrario.
     */
    fun eliminar(codigo: String): Boolean

}