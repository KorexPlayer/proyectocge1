package com.carlosgrandabastiannunezmartinparada.proyectocge.shared.servicios

import com.carlosgrandabastiannunezmartinparada.proyectocge.shared.dominio.Boleta
import com.carlosgrandabastiannunezmartinparada.proyectocge.shared.dominio.Cliente

class PdfService {
    fun generarBoletasPDF(boletas: List<Boleta>, clientes: Map<String, Cliente>): ByteArray {
        val builder = StringBuilder()
        builder.append("--- REPORTE DE BOLETAS CGE ---\n\n")

        boletas.forEach { boleta ->
            val cliente = clientes[boleta.getIdCliente()]
            builder.append("=====================================\n")
            builder.append("Cliente: ${cliente?.getNombre()} (RUT: ${cliente?.getRut()})\n")
            builder.append("Período: ${boleta.getMes()}/${boleta.getAnio()}\n")

            val tabla = boleta.toPdfString()
            tabla.rows.forEach { row ->
                builder.append("\t${row[0]}: ${row[1]}\n")
            }
            builder.append("=====================================\n\n")
        }

        // Simplemente, retornamos los bytes de un String
        return builder.toString().encodeToByteArray()
    }
}