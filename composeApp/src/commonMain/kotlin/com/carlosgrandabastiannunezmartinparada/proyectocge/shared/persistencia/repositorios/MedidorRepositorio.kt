package com.carlosgrandabastiannunezmartinparada.proyectocge.shared.persistencia.repositorios

import com.carlosgrandabastiannunezmartinparada.proyectocge.shared.dominio.Medidor

interface MedidorRepositorio {
    fun crear(m: Medidor, rutCliente: String): Medidor

    fun listarPorCliente(rut: String): List<Medidor>

    fun obtenerPorCodigo(codigo: String): Medidor?

    fun eliminar(codigo: String): Boolean

}