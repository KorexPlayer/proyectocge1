package com.carlosgrandabastiannunezmartinparada.proyectocge.shared.dominio

import com.carlosgrandabastiannunezmartinparada.proyectocge.shared.servicios.PdfTable

/**
 * Define la interfaz para objetos que pueden ser exportados a una estructura de tabla PDF.
 *
 * Las clases que implementen esta interfaz deben proporcionar una forma de
 * convertir su estado en un [PdfTable] (cabeceras y filas) para su
 * posterior renderización en un documento PDF.
 */
interface ExportablePDF {
    /**
     * Convierte los datos del objeto en una [PdfTable].
     *
     * @return Un objeto [PdfTable] que contiene las cabeceras (headers)
     * y las filas (rows) que representan los datos del objeto.
     */
    public abstract fun toPdfString(): PdfTable
}