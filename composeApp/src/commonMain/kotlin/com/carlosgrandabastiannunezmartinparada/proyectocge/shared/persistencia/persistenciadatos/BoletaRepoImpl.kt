package com.carlosgrandabastiannunezmartinparada.proyectocge.shared.persistencia.persistenciadatos

import com.carlosgrandabastiannunezmartinparada.proyectocge.shared.dominio.Boleta
import com.carlosgrandabastiannunezmartinparada.proyectocge.shared.persistencia.repositorios.BoletaRepositorio




object BoletaRepoImpl : BoletaRepositorio {
    private val repositorio: MutableList<Boleta> = mutableListOf()
    override fun guardar(b: Boleta): Boleta {
        repositorio.add(b)
        println("Se ha anadido la boleta al repositorio")
        return b
    }

    override fun obtener(
        rut: String,
        anio: Int,
        mes: Int
    ): Boleta? {
        for (boleta in repositorio) {
            if (boleta.getCliente().getRut() == rut && boleta.getAnio() == anio && boleta.getMes() == mes) {
                println("Boleta encontrada con exito")
                return boleta
            }
        }
        println("No existe la boleta en nuestro repositorio")
        return null
    }

    override fun listarPorCliente(rut: String): List<Boleta> {
        val listaPorCliente: MutableList<Boleta> = mutableListOf()
        for (boleta in repositorio) {
            if (boleta.getCliente().getRut() == rut) {
                listaPorCliente.add(boleta)
            }
        }
        return listaPorCliente
    }


}