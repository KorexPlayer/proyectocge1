package com.carlosgrandabastiannunezmartinparada.proyectocge.shared.persistencia.repositorios

import com.carlosgrandabastiannunezmartinparada.proyectocge.shared.dominio.Cliente

interface ClienteRepositorio {
    fun crear(c: Cliente): Cliente

    fun actualizar(c: Cliente): Cliente

    fun eliminar(rut: String): Boolean

    fun obtenerPorRut(rut: String): Cliente?

    fun listar(filtro: String = ""): List<Cliente>

}