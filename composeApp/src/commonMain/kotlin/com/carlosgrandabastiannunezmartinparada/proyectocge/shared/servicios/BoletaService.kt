package com.carlosgrandabastiannunezmartinparada.proyectocge.shared.servicios

import com.carlosgrandabastiannunezmartinparada.proyectocge.shared.dominio.Boleta
import com.carlosgrandabastiannunezmartinparada.proyectocge.shared.dominio.Cliente
import com.carlosgrandabastiannunezmartinparada.proyectocge.shared.dominio.Date
import com.carlosgrandabastiannunezmartinparada.proyectocge.shared.dominio.EstadoBoleta
import com.carlosgrandabastiannunezmartinparada.proyectocge.shared.persistencia.repositorios.BoletaRepositorio
import com.carlosgrandabastiannunezmartinparada.proyectocge.shared.persistencia.repositorios.ClienteRepositorio
import com.carlosgrandabastiannunezmartinparada.proyectocge.shared.persistencia.repositorios.LecturaRepositorio
import com.carlosgrandabastiannunezmartinparada.proyectocge.shared.persistencia.repositorios.MedidorRepositorio

/**
 * Clase de servicio que orquesta la lógica de negocio para la facturación.
 *
 * Coordina los diferentes repositorios ([ClienteRepositorio], [MedidorRepositorio], etc.)
 * y servicios ([TarifaService]) para calcular y emitir boletas,
 * y exportar los resultados.
 *
 * @param clientes El repositorio para acceder a los datos del cliente.
 * @param medidores El repositorio para acceder a los datos del medidor.
 * @param lecturas El repositorio para acceder a las lecturas de consumo.
 * @param boletas El repositorio para guardar o consultar boletas.
 * @param tarifas El servicio para determinar qué tarifa aplicar y cómo calcularla.
 */
class BoletaService(
    private var clientes: ClienteRepositorio,
    private var medidores: MedidorRepositorio,
    private var lecturas: LecturaRepositorio,
    private var boletas: BoletaRepositorio,
    private var tarifas: TarifaService,
) {
    /**
     * Genera (emite) una nueva [Boleta] para un cliente en un período específico.
     *
     * Este método calcula el consumo del mes (delta), determina la tarifa
     * del cliente, calcula los costos y crea una nueva instancia de [Boleta]
     * con estado [EstadoBoleta.EMITIDA].
     *
     * @param rutCliente El RUT del cliente al que se le emitirá la boleta.
     * @param anio El año del período de facturación.
     * @param mes El mes del período de facturación.
     * @return La [Boleta] recién generada.
     */
    fun emitirBoletaMensual(rutCliente: String, anio: Int, mes: Int): Boleta {
        val boletaId = "A0000" // ID hardcodeado
        val creadoDia = Date(anio, mes, 10)
        val actualizadoDia = Date(anio, mes, 10)
        val clienteBoleta: Cliente = clientes.obtenerPorRut(rutCliente)!!
        val idCliente = rutCliente
        // Calcula el consumo *diferencial* del mes
        val nuevoKWhTotal = calcularKwhClienteMes(rutCliente, anio, mes)
        // Obtiene la lectura *total* del medidor para el cálculo de tarifa
        val nuevoKWhLeidos = clientes.obtenerPorRut(rutCliente)?.devolverMedidor()?.ultimaLecturaConsumo()?.getKwhLeidos()!!
        // Calcula el detalle de costos (la tarifa se aplica sobre la lectura total)
        val nuevoDetalle = TarifaService().tarifaPara(clienteBoleta).calcular(nuevoKWhLeidos)
        val estado = EstadoBoleta.EMITIDA

        val boletaMensual = Boleta(boletaId, creadoDia,
            actualizadoDia, clienteBoleta, idCliente,
            anio, mes, nuevoKWhTotal, nuevoDetalle, estado)

        // Agrega la boleta a la lista interna del cliente
        clienteBoleta.agregarBoleta(boletaMensual)

        return boletaMensual
    }

    /**
     * Calcula el consumo diferencial (delta) de KWh para un cliente en un mes.
     *
     * El cálculo se basa en la *última lectura* del medidor registrada,
     * menos el KWh total acumulado en la *última boleta* emitida para ese cliente.
     *
     * @param rutCliente El RUT del cliente.
     * @param anio El año del período (actualmente no usado en la lógica de cálculo).
     * @param mes El mes del período (actualmente no usado en la lógica de cálculo).
     * @return El total de KWh consumidos (delta) en el período. 0.0 si no hay boleta anterior.
     */
    fun calcularKwhClienteMes(rutCliente: String, anio: Int, mes: Int): Double {
        val anteriorKWhTotal = clientes.obtenerPorRut(rutCliente)?.ultimaBoleta()?.getKwhTotal()?: 0.0
        val nuevoKWhLeidos = clientes.obtenerPorRut(rutCliente)?.devolverMedidor()?.ultimaLecturaConsumo()?.getKwhLeidos()!!
        val nuevoKWhTotal = nuevoKWhLeidos - anteriorKWhTotal
        return nuevoKWhTotal
    }

    /**
     * Genera un archivo PDF (como [ByteArray]) que contiene las boletas de un cliente.
     *
     * Utiliza un [PdfService] para compilar la lista de boletas
     * (actualmente, obtiene *todas* las boletas del cliente) y los datos del cliente
     * en un documento PDF.
     *
     * @param rutCliente El RUT del cliente.
     * @param anio El año del período (actualmente no usado para filtrar).
     * @param mes El mes del período (actualmente no usado para filtrar).
     * @param pdf La instancia del [PdfService] que generará el archivo.
     * @return Un [ByteArray] que representa el archivo PDF generado.
     */
    fun exportarPdfClienteMes(rutCliente: String, anio: Int, mes: Int, pdf: PdfService): ByteArray {
        val listaBoletas = clientes.obtenerPorRut(rutCliente)?.listadeBoletas()!!
        val cliente: Cliente = clientes.obtenerPorRut(rutCliente)!!
        val clientes: Map<String, Cliente> = mapOf(rutCliente to cliente)
        return pdf.generarBoletasPDF(listaBoletas, clientes)
    }
}