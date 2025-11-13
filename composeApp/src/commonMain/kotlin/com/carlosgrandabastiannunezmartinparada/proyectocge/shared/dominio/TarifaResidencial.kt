package com.carlosgrandabastiannunezmartinparada.proyectocge.shared.dominio

/**
 * Implementación de la [Tarifa] para clientes de tipo Residencial.
 *
 * Esta estrategia de cálculo es más simple; incluye un cargo fijo,
 * un precio por KWh y el IVA. No aplica recargos adicionales.
 *
 * @param cargoFijo El costo base fijo que se aplica independientemente del consumo.
 * @param precioKwh El costo monetario por cada Kilowatt-hora consumido.
 * @param iva El porcentaje de IVA (ej. 0.19 para 19%) aplicado al total antes de impuestos.
 */
class TarifaResidencial(
    private  var cargoFijo: Double = 1200.0,
    private var precioKwh: Double = 150.0,
    private var iva: Double = 0.19
) : Tarifa{
    //Getters y Setters
    /** Obtiene el cargo fijo de la tarifa. */
    fun getCargoFijo(): Double = cargoFijo
    /** Obtiene el precio por KWh. */
    fun getPrecioKwh(): Double = precioKwh
    /** Obtiene el porcentaje de IVA (ej. 0.19). */
    fun getIva(): Double = iva

    /** Actualiza el cargo fijo de la tarifa. */
    fun setCargoFijo(nuevoCargoFijo: Double) {cargoFijo = nuevoCargoFijo }
    /** Actualiza el precio por KWh. */
    fun setPrecioKwh(nuevoPrecioKwh: Double) { precioKwh = nuevoPrecioKwh }
    /** Actualiza el porcentaje de IVA. */
    fun setIva(nuevoIva: Double) {iva = nuevoIva }
    //Getters y Setters

    /**
     * Devuelve el nombre identificador de esta tarifa.
     * @return El [String] "Tarifa Residencial".
     */
    override fun nombre(): String {
        return "Tarifa Residencial"
    }

    /**
     * Calcula el desglose de costos para la tarifa residencial.
     *
     * El cálculo incluye:
     * 1. Subtotal (consumo * precio KWh).
     * 2. Cargos (solo el Cargo Fijo).
     * 3. IVA ( (Subtotal + Cargos) * % IVA).
     * 4. Total (Subtotal + Cargos + IVA).
     *
     * @param kwh El total de Kilowatts-hora consumidos.
     * @return Un [TarifaDetalle] con el desglose completo del cálculo.
     */
    override fun calcular(kwh: Double): TarifaDetalle {
        val subtotal = kwh * getPrecioKwh()
        val cargos = getCargoFijo()
        val ivaMonto = (subtotal + cargos) * getIva()
        val total = subtotal + cargos + ivaMonto
        return TarifaDetalle(kwh, subtotal, cargos, ivaMonto, total)

    }
}