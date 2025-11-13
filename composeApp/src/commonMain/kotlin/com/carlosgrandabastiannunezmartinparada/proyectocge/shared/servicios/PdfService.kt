package com.carlosgrandabastiannunezmartinparada.proyectocge.shared.servicios

import com.carlosgrandabastiannunezmartinparada.proyectocge.shared.dominio.Boleta
import com.carlosgrandabastiannunezmartinparada.proyectocge.shared.dominio.Cliente

/**
 * Servicio encargado de generar reportes de boletas.
 */
class PdfService {

    /**
     * Genera un reporte (como [ByteArray]) de una lista de boletas.
     *
     * Itera sobre la lista de [boletas], busca la información del cliente
     * en el mapa [clientes] (usando el ID del cliente de la boleta)
     * y formatea los detalles (usando [Boleta.toPdfString]) en un
     * [String] de texto plano.
     *
     * @param boletas La lista de [Boleta] a incluir en el reporte.
     * @param clientes Un [Map] (clave=ID de cliente, valor=[Cliente]) necesario
     * para buscar los datos del cliente (nombre, RUT) para cada boleta.
     * @return Un [ByteArray] que contiene el reporte de texto plano
     * (codificado como UTF-8 por defecto por [encodeToByteArray]).
     */
    fun generarBoletasPDF(boletas: List<Boleta>, clientes: Map<String, Cliente>): ByteArray {
        val builder = StringBuilder()
        builder.append("--- REPORTE DE BOLETAS CGE ---\n\n")

        boletas.forEach { boleta ->
            val cliente = clientes[boleta.getIdCliente()]
            builder.append("=====================================\n")
            builder.append("Cliente: ${cliente?.getNombre()} (RUT: ${cliente?.getRut()})\n")
            builder.append("Período: ${boleta.getMes()}/${boleta.getAnio()}\n")
            // Obtiene la estructura de tabla (filas y columnas) de la boleta
            val tabla = boleta.toPdfString()
            // Formatea las filas de la tabla
            tabla.rows.forEach { row ->
                builder.append("\t${row[0]}: ${row[1]}\n")
            }
            builder.append("=====================================\n\n")
        }

        // Simplemente, retornamos los bytes de un String
        return builder.toString().encodeToByteArray()
    }
}