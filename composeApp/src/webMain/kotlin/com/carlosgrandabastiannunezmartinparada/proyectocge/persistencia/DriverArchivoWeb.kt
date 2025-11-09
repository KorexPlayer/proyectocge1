package com.carlosgrandabastiannunezmartinparada.proyectocge.persistencia

import com.carlosgrandabastiannunezmartinparada.proyectocge.shared.persistencia.repositorios.StorageDriver
import kotlinx.browser.localStorage
import org.w3c.dom.get
import org.w3c.dom.set

class DriverArchivoWeb : StorageDriver {

    private fun bytesAString(bytes: ByteArray): String {
        return bytes.decodeToString()
    }

    private fun stringABytes(str: String): ByteArray {
        return str.encodeToByteArray()
    }

    override fun put(key: String, bytes: ByteArray): Boolean {
        localStorage[key] = bytesAString(bytes)
        return true
    }

    override fun get(key: String): ByteArray? {
        val str = localStorage[key]
        return str?.let { stringABytes(it) }
    }

    override fun keys(prefix: String): List<String> {
        val matchingKeys = mutableListOf<String>()
        for (i in 0 until localStorage.length) {
            val key = localStorage.key(i)
            if (key != null && key.startsWith(prefix)) {
                matchingKeys.add(key)
            }
        }
        return matchingKeys
    }

    override fun remove(key: String): Boolean {
        localStorage.removeItem(key)
        return true
    }
}