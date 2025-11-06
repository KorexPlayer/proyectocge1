package com.carlosgrandabastiannunezmartinparada.proyectocge.shared.persistencia.persistenciadatos

import com.carlosgrandabastiannunezmartinparada.proyectocge.shared.persistencia.repositorios.StorageDriver

class EscritorioStorageDriver(): StorageDriver {
    override fun put(key: String, data: ByteArray): Boolean {
        TODO("Not yet implemented")
    }

    override fun get(key: String): ByteArray? {
        TODO("Not yet implemented")
    }

    override fun keys(prefix: String): List<String> {
        TODO("Not yet implemented")
    }

    override fun remove(key: String): Boolean {
        TODO("Not yet implemented")
    }

}