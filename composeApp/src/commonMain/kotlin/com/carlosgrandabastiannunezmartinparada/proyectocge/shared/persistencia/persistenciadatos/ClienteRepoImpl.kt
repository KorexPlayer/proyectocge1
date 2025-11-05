package com.carlosgrandabastiannunezmartinparada.proyectocge.shared.persistencia.persistenciadatos

import com.carlosgrandabastiannunezmartinparada.proyectocge.shared.dominio.Cliente
import com.carlosgrandabastiannunezmartinparada.proyectocge.shared.persistencia.repositorios.ClienteRepositorio

class ClienteRepoImpl(
    private val persistenciaDato: PersistenciaDato
) : ClienteRepositorio {
    val repositorio: MutableList<Cliente> = mutableListOf()
    fun agregar(c: Cliente){
        repositorio.add(c)
        println("Se ha ingresado correctamente el cliente al repositorio.")
    }
    fun eliminar(c: Cliente){
        if(repositorio.remove(c)) {
            println("Se ha eliminado correctamente al cliente del repositorio.")
        }
    }
    fun listado() {
        println("Se recorrera el repositorio.")
        repositorio.forEach{println(it.toString())}
    }

    override fun crear(c: Cliente): Cliente {
        TODO("Not yet implemented")

    }

    override fun actualizar(c: Cliente): Cliente {
        TODO("Not yet implemented")
    }

    override fun eliminar(rut: String): Boolean {
        TODO("Not yet implemented")
    }

    override fun obtenerPorRut(rut: String): Cliente? {
        TODO("Not yet implemented")
    }

    override fun listar(filtro: String): List<Cliente> {
        TODO("Not yet implemented")
    }

}