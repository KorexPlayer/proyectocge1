package com.carlosgrandabastiannunezmartinparada.proyectocge.shared.servicios

import com.carlosgrandabastiannunezmartinparada.proyectocge.shared.dominio.Boleta
import com.carlosgrandabastiannunezmartinparada.proyectocge.shared.dominio.Cliente
import com.carlosgrandabastiannunezmartinparada.proyectocge.shared.dominio.Date
import com.carlosgrandabastiannunezmartinparada.proyectocge.shared.dominio.EstadoBoleta
import com.carlosgrandabastiannunezmartinparada.proyectocge.shared.persistencia.repositorios.BoletaRepositorio
import com.carlosgrandabastiannunezmartinparada.proyectocge.shared.persistencia.repositorios.ClienteRepositorio
import com.carlosgrandabastiannunezmartinparada.proyectocge.shared.persistencia.repositorios.LecturaRepositorio
import com.carlosgrandabastiannunezmartinparada.proyectocge.shared.persistencia.repositorios.MedidorRepositorio

class BoletaService(
    private var clientes: ClienteRepositorio,
    private var medidores: MedidorRepositorio,
    private var lecturas: LecturaRepositorio,
    private var boletas: BoletaRepositorio,
    private var tarifas: TarifaService,
) {
    fun emitirBoletaMensual(rutCliente: String, anio: Int, mes: Int): Boleta {
        val boletaId = "A0000"
        val creadoDia = Date(anio, mes, 10)
        val actualizadoDia = Date(anio, mes, 10)
        val clienteBoleta: Cliente = clientes.obtenerPorRut(rutCliente)!!
        val idCliente = rutCliente
        val nuevoKWhTotal = calcularKwhClienteMes(rutCliente, anio, mes)
        val nuevoKWhLeidos = clientes.obtenerPorRut(rutCliente)?.devolverMedidor()?.ultimaLecturaConsumo()?.getKwhLeidos()!!
        val nuevoDetalle = TarifaService().tarifaPara(clienteBoleta).calcular(nuevoKWhLeidos)
        val estado = EstadoBoleta.EMITIDA

        val boletaMensual = Boleta(boletaId, creadoDia,
            actualizadoDia, clienteBoleta, idCliente,
            anio, mes, nuevoKWhTotal, nuevoDetalle, estado)
        clienteBoleta.agregarBoleta(boletaMensual)

        return boletaMensual
    }
    fun calcularKwhClienteMes(rutCliente: String, anio: Int, mes: Int): Double {
        val anteriorKWhTotal = clientes.obtenerPorRut(rutCliente)?.ultimaBoleta()?.getKwhTotal()?: 0.0
        val nuevoKWhLeidos = clientes.obtenerPorRut(rutCliente)?.devolverMedidor()?.ultimaLecturaConsumo()?.getKwhLeidos()!!
        val nuevoKWhTotal = nuevoKWhLeidos - anteriorKWhTotal
        return nuevoKWhTotal
    }
    fun exportarPdfClienteMes(rutCliente: String, anio: Int, mes: Int, pdf: PdfService): ByteArray {
        val listaBoletas = clientes.obtenerPorRut(rutCliente)?.listadeBoletas()!!
        val cliente: Cliente = clientes.obtenerPorRut(rutCliente)!!
        val clientes: Map<String, Cliente> = mapOf(rutCliente to cliente)
        return pdf.generarBoletasPDF(listaBoletas, clientes)
    }
}