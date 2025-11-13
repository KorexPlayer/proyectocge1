package com.carlosgrandabastiannunezmartinparada.proyectocge.shared.persistencia.persistenciadatos


import com.carlosgrandabastiannunezmartinparada.proyectocge.shared.persistencia.repositorios.StorageDriver

/**
 * Capa de abstracción sobre un [StorageDriver] para operaciones de persistencia.
 *
 * Esta clase simplifica el driver, proveyendo métodos directos
 * para guardar (save), leer (read), listar (list) y eliminar (delete)
 * datos binarios (ByteArray) asociados a una clave (key).
 *
 * @param driver La implementación concreta del [StorageDriver],
 * que manejará las operaciones de E/S.
 */
class PersistenciaDato(
    private val driver: StorageDriver
) {

    /**
     * Guarda un [ByteArray] en la persistencia asociado a una [key].
     *
     * Delega la operación "put" al [driver].
     *
     * @param key La clave única bajo la cual se almacenarán los datos.
     * @param bytes Los datos binarios a guardar.
     * @return `true` si la operación fue exitosa, `false` en caso contrario.
     */
    fun save(key: String, bytes: ByteArray): Boolean {
        return driver.put(key, bytes)
    }

    /**
     * Lee y devuelve un [ByteArray] desde la persistencia usando una [key].
     *
     * Delega la operación "get" al [driver].
     *
     * @param key La clave única de los datos a recuperar.
     * @return El [ByteArray] almacenado, o `null` si la clave no existe.
     */
    fun read(key: String): ByteArray? {
        return driver.get(key)
    }

    /**
     * Obtiene una lista de todas las claves que comienzan con un [prefix] específico.
     *
     * Delega la operación "keys" al [driver].
     *
     * @param prefix El prefijo a buscar (ej. "clientes_").
     * @return Una [List] de [String] con las claves que coinciden.
     */
    fun list(prefix: String): List<String> {
        return driver.keys(prefix)
    }

    /**
     * Elimina un registro de la persistencia basado en su [key].
     *
     * Delega la operación "remove" al [driver].
     *
     * @param key La clave única del registro a eliminar.
     * @return `true` si la eliminación fue exitosa, `false` en caso contrario.
     */
    fun delete(key: String): Boolean {
        return driver.remove(key)
    }
}