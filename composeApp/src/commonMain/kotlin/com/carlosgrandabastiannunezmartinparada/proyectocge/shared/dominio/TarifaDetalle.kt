package com.carlosgrandabastiannunezmartinparada.proyectocge.shared.dominio

class TarifaDetalle(
    public var kwh: Double,
    public var subtotal: Double,
    public var cargos: Double,
    public var iva: Double,
    public var total: Double
) {
}