package com.carlosgrandabastiannunezmartinparada.proyectocge.shared.dominio

class TarifaComercial(
    private var cargoFijo: Double,
    private var precioKwh: Double,
    private var recargoComercial: Double,
    private var iva: Double
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
        val cargo = getCargoFijo()
        val preKwhl = getPrecioKwh()
        val recComl = getRecargoComercial()
        val ival = getIva()
        val cargos = cargo + recComl
        val total = cargos + ( preKwhl * kwh)
        val ivatotal = total * ival
        val totalmasiva = total + ivatotal
        return TarifaDetalle(kwh, total, cargos, ivatotal, totalmasiva)
    }

}