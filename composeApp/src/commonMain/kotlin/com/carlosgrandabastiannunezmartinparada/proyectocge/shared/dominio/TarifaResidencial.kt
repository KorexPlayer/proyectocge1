package com.carlosgrandabastiannunezmartinparada.proyectocge.shared.dominio

class TarifaResidencial(
    private  var cargoFijo: Double,
    private var precioKwh: Double,
    private var iva: Double
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
        val cargo = getCargoFijo()
        val preKwhl = getPrecioKwh()
        val ival = getIva()
        val total = cargo + ( preKwhl * kwh)
        val ivatotal = total * ival
        val totalmasiva = total + ivatotal
        return TarifaDetalle(kwh, total, cargo, ivatotal, totalmasiva)

    }
}