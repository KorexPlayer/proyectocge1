package com.carlosgrandabastiannunezmartinparada.proyectocge.shared.persistencia.persistenciadatos


import com.carlosgrandabastiannunezmartinparada.proyectocge.shared.persistencia.repositorios.StorageDriver

class PersistenciaDato(
    private val driver: StorageDriver
) {
    fun save(key: String, bytes: ByteArray): Boolean {
        return true
    }

    fun read(key: String): ByteArray? {
        return null
    }

    fun list(prefix: String): List<String> {
        return emptyList()
    }

    fun delete(key: String): Boolean {
        return true
    }
}