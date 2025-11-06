package com.carlosgrandabastiannunezmartinparada.proyectocge.shared.persistencia.persistenciadatos


import com.carlosgrandabastiannunezmartinparada.proyectocge.shared.persistencia.repositorios.StorageDriver

class PersistenciaDato(
    private val driver: StorageDriver
) {
    fun save(key: String, bytes: ByteArray): Boolean {
        return driver.put(key, bytes)
    }
    fun read(key: String): ByteArray? {
        return driver.get(key)
    }

    fun list(prefix: String): List<String> {
        return driver.keys(prefix)
    }

    fun delete(key: String): Boolean {
        return driver.remove(key)
    }
}