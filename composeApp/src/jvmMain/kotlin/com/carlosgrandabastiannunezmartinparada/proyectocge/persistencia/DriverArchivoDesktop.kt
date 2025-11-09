package com.carlosgrandabastiannunezmartinparada.proyectocge.persistencia

import com.carlosgrandabastiannunezmartinparada.proyectocge.shared.persistencia.repositorios.StorageDriver
import java.io.File

class DriverArchivoDesktop(private val directorioBase: String = "data") : StorageDriver {
    private val directorioPadre = File(directorioBase)

    init {
        if (!directorioPadre.exists()) {
            directorioPadre.mkdirs()
        }
    }

    private fun obtenerNombreArchivo(key: String): File {
        val nombreArchivo = key.replace(File.separatorChar, '_').replace(':', '_')
        return File(directorioPadre, nombreArchivo)
    }

    override fun put(key: String, data: ByteArray): Boolean {
        obtenerNombreArchivo(key).writeBytes(data)
        return true
    }

    override fun get(key: String): ByteArray? {
        val archivo = obtenerNombreArchivo(key)
        return if (archivo.exists() && archivo.isFile) {
            archivo.readBytes()
        } else {
            null
        }
    }

    override fun keys(prefix: String): List<String> {
        val prefijoArchivo = prefix.replace(File.separatorChar, '_').replace(':', '_')

        return directorioPadre.listFiles { _, name -> name.startsWith(prefijoArchivo) }
            ?.map { it.name }
            ?: emptyList()
    }

    override fun remove(key: String): Boolean {
        return obtenerNombreArchivo(key).delete()
    }
}