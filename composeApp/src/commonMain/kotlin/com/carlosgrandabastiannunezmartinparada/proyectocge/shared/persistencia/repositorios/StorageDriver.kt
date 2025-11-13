package com.carlosgrandabastiannunezmartinparada.proyectocge.shared.persistencia.repositorios

/**
 * Define el contrato de bajo nivel para un mecanismo de almacenamiento de clave-valor.
 *
 * Esta interfaz abstrae las operaciones fundamentales de E/S (Entrada/Salida)
 * para un sistema de persistencia que almacena datos binarios (ByteArray).
 * Es la capa más baja, utilizada por [PersistenciaDato].
 */
interface StorageDriver {

    /**
     * Almacena (o sobrescribe) un [ByteArray] bajo una [key] específica.
     *
     * @param key La clave única para almacenar los datos.
     * @param data Los datos binarios (bytes) a guardar.
     * @return `true` si la operación de guardado fue exitosa, `false` si falló.
     */
    fun put(key: String, data: ByteArray): Boolean

    /**
     * Recupera un [ByteArray] almacenado bajo una [key] específica.
     *
     * @param key La clave de los datos a recuperar.
     * @return El [ByteArray] asociado a la clave, o `null` si la clave no existe.
     */
    fun get(key: String): ByteArray?

    /**
     * Devuelve una lista de todas las claves que comienzan con un [prefix] dado.
     *
     * @param prefix El prefijo (case-sensitive) para filtrar las claves.
     * @return Una [List] de [String] con las claves que coinciden.
     */
    fun keys(prefix: String): List<String>

    /**
     * Elimina permanentemente los datos asociados a una [key] específica.
     *
     * @param key La clave de los datos a eliminar.
     * @return `true` si la eliminación fue exitosa (o si la clave no existía),
     * `false` si ocurrió un error al intentar eliminar.
     */
    fun remove(key: String): Boolean

}

