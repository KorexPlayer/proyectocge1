package com.carlosgrandabastiannunezmartinparada.proyectocge.shared.dominio

/**
 * Implementación de la [Tarifa] para clientes de tipo Comercial.
 *
 * Esta estrategia de cálculo incluye un cargo fijo, un precio por KWh,
 * un recargo porcentual específico (sobre la suma del subtotal y cargo fijo)
 * y el IVA.
 *
 * @param cargoFijo El costo base fijo que se aplica independientemente del consumo.
 * @param precioKwh El costo monetario por cada Kilowatt-hora consumido.
 * @param recargoComercial El porcentaje (ej. 0.05 para 5%) de recargo aplicado a los clientes comerciales.
 * @param iva El porcentaje de IVA (ej. 0.19 para 19%) aplicado al total antes de impuestos.
 */
class TarifaComercial(
    private var cargoFijo: Double = 5000.0,
    private var precioKwh: Double = 130.0,
    private var recargoComercial: Double = 0.05, //recargo del 5%
    private var iva: Double = 0.19
) : Tarifa {
    //Getters y Setters
    /** Obtiene el cargo fijo de la tarifa. */
    fun getCargoFijo(): Double = cargoFijo
    /** Obtiene el precio por KWh. */
    fun getPrecioKwh(): Double = precioKwh
    /** Obtiene el porcentaje de recargo comercial (ej. 0.05). */
    fun getRecargoComercial(): Double = recargoComercial
    /** Obtiene el porcentaje de IVA (ej. 0.19). */
    fun getIva(): Double = iva

    /** Actualiza el cargo fijo de la tarifa. */
    fun setCargoFijo(nuevoCargoFijo: Double) {cargoFijo = nuevoCargoFijo }
    /** Actualiza el precio por KWh. */
    fun setPrecioKwh(nuevoPrecioKwh: Double) { precioKwh = nuevoPrecioKwh }
    /** Actualiza el porcentaje de recargo comercial. */
    fun setRecargoComercial(nuevoRecargoComercial: Double) {recargoComercial = nuevoRecargoComercial }
    /** Actualiza el porcentaje de IVA. */
    fun setIva(nuevoIva: Double) {iva = nuevoIva }
    //Getters y Setters

    /**
     * Devuelve el nombre identificador de esta tarifa.
     * @return El [String] "Tarifa Comercial".
     */
    override fun nombre(): String {
        return "Tarifa Comercial"
    }

    /**
     * Calcula el desglose de costos para la tarifa comercial.
     *
     * El cálculo incluye:
     * 1. Subtotal (consumo * precio KWh).
     * 2. Recargo ( (Subtotal + Cargo Fijo) * % Recargo Comercial).
     * 3. Cargos Totales (Cargo Fijo + Recargo).
     * 4. IVA ( (Subtotal + Cargos Totales) * % IVA).
     * 5. Total (Subtotal + Cargos Totales + IVA).
     *
     * @param kwh El total de Kilowatts-hora consumidos.
     * @return Un [TarifaDetalle] con el desglose completo del cálculo.
     */
    override fun calcular(kwh: Double): TarifaDetalle {
        val subtotal = kwh * getPrecioKwh()
        val recargo = (subtotal + getCargoFijo()) * getRecargoComercial()
        val cargos = getCargoFijo() + recargo
        val ivaMonto = (subtotal + cargos) * getIva()
        val total = subtotal + cargos + ivaMonto
        return TarifaDetalle(kwh, subtotal, cargos, ivaMonto, total)
    }

}