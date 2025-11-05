package com.carlosgrandabastiannunezmartinparada.proyectocge.shared.dominio

interface Tarifa {
    fun nombre(): String

    fun calcular(kwh: Double): TarifaDetalle

}