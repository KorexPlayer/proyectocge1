package com.carlosgrandabastiannunezmartinparada.proyectocge.shared.dominio

class TarifaComercial(
    private val cargoFijo: Double,
    private val precioKwh: Double,
    private val recargoComercial: Double,
    private val iva: Double
) : Tarifa {
    override fun nombre(): String {
        TODO("Not yet implemented")
    }

    override fun calcular(kwh: Double): TarifaDetalle {
        TODO("Not yet implemented")
    }

}