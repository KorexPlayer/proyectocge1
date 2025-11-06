package com.carlosgrandabastiannunezmartinparada.proyectocge.shared.persistencia.persistenciadatos

import com.carlosgrandabastiannunezmartinparada.proyectocge.shared.dominio.Cliente
import com.carlosgrandabastiannunezmartinparada.proyectocge.shared.persistencia.repositorios.ClienteRepositorio
import kotlinx.serialization.encodeToString
class ClienteRepoImpl(
    private var persistenciaDato: PersistenciaDato
) : ClienteRepositorio {
    val repositorio: MutableList<Cliente> = mutableListOf()

    override fun crear(c: Cliente): Cliente {
        for (cliente in repositorio) {
            if (cliente.getRut() == c.getRut()) {
                println("Ya existe cliente con ese rut")
                return cliente
            }
        }
        repositorio.add(c)
        println("Cliente creado correctamente")
        return c
    }

    override fun actualizar(c: Cliente): Cliente {
        for (i in repositorio.indices) {
            if (repositorio[i].getRut() == c.getRut()) {
                repositorio[i] = c
                println("Cliente actualizado correctamente")
                return c
            }
        }
        println("No se encontro el cliente")
        return c
    }

    override fun eliminar(rut: String): Boolean {
        for (cliente in repositorio) {
            if (cliente.getRut() == rut) {
                repositorio.remove(cliente)
                println("Se ha eliminado el Cliente del repositorio.")
                return true
            }
        }
        return false
    }

    override fun obtenerPorRut(rut: String): Cliente? {
        for (cliente in repositorio) {
            if (cliente.getRut() == rut) {
                println("Cliente encontrado: ${cliente.getNombre()}")
                return cliente
            }
        }
        println("No se encontro el cliente")
        return null
    }

    override fun listar(filtro: String): List<Cliente> {
        val listaFiltrada: MutableList<Cliente> = mutableListOf()

        if (filtro.isBlank()) {
            return emptyList()
        }

        val filtroMinusculas = filtro.lowercase()

        return repositorio.filter { cliente ->
            cliente.getRut().lowercase().contains(filtroMinusculas) ||
                    cliente.getNombre().lowercase().contains(filtroMinusculas)
        }
    }
}