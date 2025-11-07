package com.carlosgrandabastiannunezmartinparada.proyectocge.shared.persistencia.persistenciadatos

import com.carlosgrandabastiannunezmartinparada.proyectocge.shared.dominio.Medidor
import com.carlosgrandabastiannunezmartinparada.proyectocge.shared.persistencia.repositorios.MedidorRepositorio

object MedidorRepoImpl : MedidorRepositorio {

    private val repositorio: MutableList<Medidor> = mutableListOf()

    override fun crear(m: Medidor, rutCliente: String): Medidor {
        for (medidor in repositorio) {
            if (medidor.getCodigo() == m.getCodigo()){
                println("Ya existe medidor con este codigo")
                return medidor
            }
        }
        repositorio.add(m)
        println("Medidor creado correctamente")
        return m
    }

    override fun listarPorCliente(rut: String): List<Medidor> {
        val listaFiltrada: MutableList<Medidor> = mutableListOf()

        for (medidor in repositorio) {
            if (medidor.getCliente().getRut() == rut){
                listaFiltrada.add(medidor)
            }
        }
        return listaFiltrada
    }

    override fun obtenerPorCodigo(codigo: String): Medidor? {
        for (medidor in repositorio){
            if (medidor.getCodigo() == codigo){
                println("Medidor encontrado")
                return medidor
            }
        }
        println("No se encontro el medidor")
        return null
    }

    override fun eliminar(codigo: String): Boolean {
        val medidorEncontrado = repositorio.find { it.getCodigo() == codigo }

        if (medidorEncontrado != null){
            repositorio.remove(medidorEncontrado)
            println("Medidor eliminado correctamente")
            return true
        }
        return false
    }

}





