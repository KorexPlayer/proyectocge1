package com.carlosgrandabastiannunezmartinparada.proyectocge.shared.dominio

class TarifaComercial(
    private var cargoFijo: Double = 5000.0,
    private var precioKwh: Double = 130.0,
    private var recargoComercial: Double = 0.05, //recargo del 5%
    private var iva: Double = 0.19
) : Tarifa {
    //Getters y Setters
    fun getCargoFijo(): Double = cargoFijo
    fun getPrecioKwh(): Double = precioKwh
    fun getRecargoComercial(): Double = recargoComercial
    fun getIva(): Double = iva
    fun setCargoFijo(nuevoCargoFijo: Double) {cargoFijo = nuevoCargoFijo }
    fun setPrecioKwh(nuevoPrecioKwh: Double) { precioKwh = nuevoPrecioKwh }
    fun setRecargoComercial(nuevoRecargoComercial: Double) {recargoComercial = nuevoRecargoComercial }
    fun setIva(nuevoIva: Double) {iva = nuevoIva }
    //Getters y Setters
    override fun nombre(): String {
        return "Tarifa Comercial"
    }

    override fun calcular(kwh: Double): TarifaDetalle {
        val subtotal = kwh * getPrecioKwh()
        val recargo = (subtotal + getCargoFijo()) * getRecargoComercial()
        val cargos = getCargoFijo() + recargo
        val ivaMonto = (subtotal + cargos) * getIva()
        val total = subtotal + cargos + ivaMonto
        return TarifaDetalle(kwh, subtotal, cargos, ivaMonto, total)
    }

}