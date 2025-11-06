package com.carlosgrandabastiannunezmartinparada.proyectocge.shared.persistencia.persistenciadatos

import com.carlosgrandabastiannunezmartinparada.proyectocge.shared.dominio.LecturaConsumo
import com.carlosgrandabastiannunezmartinparada.proyectocge.shared.persistencia.repositorios.LecturaRepositorio

class LecturaRepoImpl (
   private val persistenciaDato: PersistenciaDato
) : LecturaRepositorio{

    private val repositorio: MutableList<LecturaConsumo> = mutableListOf()

    override fun registrar(l: LecturaConsumo): LecturaConsumo {
        repositorio.add(l)
        println("Se ha registrado la nueva lectura")
        return l
    }

    override fun listarPorMedidor(idMedidor: String, anio: Int, mes: Int): List<LecturaConsumo> {
        val listaFiltrada: MutableList<LecturaConsumo> = mutableListOf()
        for (lectura in repositorio) {
            if (lectura.getIdMedidor() == idMedidor || lectura.getAnio() == anio && lectura.getMes() == mes) {
                listaFiltrada.add(lectura)
            }
        }
        return listaFiltrada
    }

    override fun ultimaLectura(idMedidor: String): LecturaConsumo? {
        for (lectura in repositorio) {
            if (lectura.getIdMedidor() == idMedidor) {
                println("Lectura conseguida")
                return lectura
            }
        }
        println("No se consigio la lectura")
        return null
    }
}