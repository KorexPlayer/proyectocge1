package com.carlosgrandabastiannunezmartinparada.proyectocge.shared.servicios

import com.carlosgrandabastiannunezmartinparada.proyectocge.shared.dominio.Boleta
import com.carlosgrandabastiannunezmartinparada.proyectocge.shared.persistencia.repositorios.BoletaRepositorio
import com.carlosgrandabastiannunezmartinparada.proyectocge.shared.persistencia.repositorios.ClienteRepositorio
import com.carlosgrandabastiannunezmartinparada.proyectocge.shared.persistencia.repositorios.LecturaRepositorio
import com.carlosgrandabastiannunezmartinparada.proyectocge.shared.persistencia.repositorios.MedidorRepositorio

class BoletaService(
    private var clientes: ClienteRepositorio,
    private var medidores: MedidorRepositorio,
    private var lecturas: LecturaRepositorio,
    private var boletas: BoletaRepositorio,
    private var tarifas: TarifaService
) {
    public fun emitirBoletaMensual(rutCliente: String, anio: Int, mes: Int): Boleta {
        TODO("No se ha implementado")
    }
    public fun calcularKwhClienteMes(rutCliente: String, anio: Int, mes: Int): Double {
        return 0.0
    }
    public fun exportarPdfClienteMes(rutCliente: String, anio: Int, mes: Int, pdf: PdfService): ByteArray {
        TODO("No se ha implementado")
    }
}