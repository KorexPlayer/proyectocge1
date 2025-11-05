package com.carlosgrandabastiannunezmartinparada.proyectocge.shared.persistencia.repositorios

import com.carlosgrandabastiannunezmartinparada.proyectocge.shared.dominio.Boleta

interface BoletaRepositorio {
    fun guardar(b: Boleta): Boleta

    fun obtener(rut: String, anio: Int, mes: Int): Boleta?

    fun listarPorCliente(rut: String): List<Boleta>

}