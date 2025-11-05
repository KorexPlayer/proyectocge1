package com.carlosgrandabastiannunezmartinparada.proyectocge.shared.persistencia.persistenciadatos

import com.carlosgrandabastiannunezmartinparada.proyectocge.shared.dominio.Cliente
import com.carlosgrandabastiannunezmartinparada.proyectocge.shared.persistencia.repositorios.ClienteRepositorio

class ClienteRepoImpl(
    private val persistenciaDato: PersistenciaDato
) : ClienteRepositorio {


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