package com.carlosgrandabastiannunezmartinparada.proyectocge.shared.persistencia.repositorios

import com.carlosgrandabastiannunezmartinparada.proyectocge.shared.dominio.LecturaConsumo

interface LecturaRepositorio {
    fun registrar(l: LecturaConsumo): LecturaConsumo

    fun listarPorMedidor(idMedidor: String, anio: Int, mes: Int): List<LecturaConsumo>

    fun ultimaLectura(idMedidor: String): LecturaConsumo?
}