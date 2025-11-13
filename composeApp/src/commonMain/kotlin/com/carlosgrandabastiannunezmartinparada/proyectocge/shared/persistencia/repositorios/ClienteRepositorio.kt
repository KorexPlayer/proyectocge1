package com.carlosgrandabastiannunezmartinparada.proyectocge.shared.persistencia.repositorios

import com.carlosgrandabastiannunezmartinparada.proyectocge.shared.dominio.Cliente

/**
 * Define el contrato para un repositorio que gestiona las entidades [Cliente].
 *
 * Establece las operaciones básicas de CRUD (Crear, Leer, Actualizar, Eliminar)
 * para los clientes, abstrayendo la implementación concreta.
 */
interface ClienteRepositorio {

    /**
     * Crea y persiste una nueva entidad [Cliente].
     *
     * @param c El [Cliente] a crear.
     * @return El [Cliente] creado (puede ser la misma instancia o una actualizada).
     */
    fun crear(c: Cliente): Cliente

    /**
     * Actualiza un [Cliente] existente en el repositorio.
     *
     * @param c El [Cliente] con los datos modificados.
     * @return El [Cliente] actualizado.
     */
    fun actualizar(c: Cliente): Cliente

    /**
     * Elimina un [Cliente] del repositorio basándose en su RUT.
     *
     * @param rut El RUT del cliente a eliminar.
     * @return `true` si el cliente fue encontrado y eliminado, `false` en caso contrario.
     */
    fun eliminar(rut: String): Boolean

    /**
     * Busca y devuelve un [Cliente] específico basándose en su RUT.
     *
     * @param rut El RUT del cliente a buscar.
     * @return El [Cliente] encontrado, o `null` si no existe.
     */
    fun obtenerPorRut(rut: String): Cliente?

    /**
     * Devuelve una lista de [Cliente] que coinciden con un [filtro].
     *
     * Si no se provee [filtro] (o está vacío), debe devolver todos los clientes.
     * El filtro se aplica usualmente al nombre o RUT.
     *
     * @param filtro El texto a buscar (por defecto es "", devolviendo todo).
     * @return Una [List] de [Cliente] (puede estar vacía si no hay coincidencias).
     */
    fun listar(filtro: String = ""): List<Cliente>

}