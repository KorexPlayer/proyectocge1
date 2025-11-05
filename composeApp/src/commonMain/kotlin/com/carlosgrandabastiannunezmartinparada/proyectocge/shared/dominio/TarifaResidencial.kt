package com.carlosgrandabastiannunezmartinparada.proyectocge.shared.dominio

class TarifaResidencial(
    private  val cargoFijo: Double,
    private val precioKwh: Double,
    private val iva: Double
) : Tarifa{
    override fun nombre(): String {
        TODO("Not yet implemented")
    }

    override fun calcular(kwh: Double): TarifaDetalle {
        TODO("Not yet implemented")
    }
}