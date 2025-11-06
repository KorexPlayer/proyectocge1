package com.carlosgrandabastiannunezmartinparada.proyectocge.shared.dominio

class TarifaResidencial(
    private  var cargoFijo: Double = 1200.0,
    private var precioKwh: Double = 150.0,
    private var iva: Double = 0.19
) : Tarifa{
    //Getters y Setters
    fun getCargoFijo(): Double = cargoFijo
    fun getPrecioKwh(): Double = precioKwh
    fun getIva(): Double = iva
    fun setCargoFijo(nuevoCargoFijo: Double) {cargoFijo = nuevoCargoFijo }
    fun setPrecioKwh(nuevoPrecioKwh: Double) { precioKwh = nuevoPrecioKwh }
    fun setIva(nuevoIva: Double) {iva = nuevoIva }
    //Getters y Setters
    override fun nombre(): String {
        return "Tarifa Residencial"
    }

    override fun calcular(kwh: Double): TarifaDetalle {
        val subtotal = kwh * getPrecioKwh()
        val cargos = getCargoFijo()
        val ivaMonto = (subtotal + cargos) * getIva()
        val total = subtotal + cargos + ivaMonto
        return TarifaDetalle(kwh, subtotal, cargos, ivaMonto, total)

    }
}