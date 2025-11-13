package com.carlosgrandabastiannunezmartinparada.proyectocge.shared.servicios

/**
 * Clase de datos (DTO) que representa una estructura de tabla simple.
 *
 * Se utiliza como un formato de intercambio de datos (por ejemplo,
 * por la interfaz [ExportablePDF] y consumido por [PdfService])
 * para definir el contenido de una tabla antes de ser renderizada.
 *
 * @param headers Una lista de [String] que representa los títulos de las columnas (cabeceras).
 * @param rows Una lista de filas, donde cada fila es una lista de [String]
 * que representa las celdas de esa fila.
 */
class PdfTable(
    val headers: List<String>,
    val rows: List<List<String>>
) {
}