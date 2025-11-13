package com.carlosgrandabastiannunezmartinparada.proyectocge.shared.dominio

/**
 * Encapsula el resultado detallado de un cálculo de tarifa.
 *
 * Almacena el desglose de los costos (subtotal, cargos, IVA) y el total final
 * basado en una cantidad específica de KWh consumidos.
 *
 * @param kwh El consumo en Kilowatts-hora utilizado para este cálculo.
 * @param subtotal El costo base del consumo (KWh * precio KWh), sin cargos ni impuestos.
 * @param cargos La suma de todos los cargos adicionales (ej. cargo fijo, recargos).
 * @param iva El monto total calculado correspondiente al IVA (impuesto).
 * @param total El monto final a pagar (subtotal + cargos + iva).
 */
class TarifaDetalle(
    var kwh: Double,
    var subtotal: Double,
    var cargos: Double,
    var iva: Double,
    var total: Double
) {
}