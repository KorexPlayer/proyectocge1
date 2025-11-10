package com.carlosgrandabastiannunezmartinparada.proyectocge.shared.servicios


import org.apache.pdfbox.pdmodel.PDDocument
import org.apache.pdfbox.pdmodel.PDPage
import org.apache.pdfbox.pdmodel.PDPageContentStream
import org.apache.pdfbox.pdmodel.font.PDType1Font
import java.io.File

actual fun guardarPdf(nombreArchivo: String, bytes: ByteArray) {
    try {
        val texto = bytes.toString(Charsets.UTF_8)

        val document = PDDocument()
        val page = PDPage()
        document.addPage(page)

        val contentStream = PDPageContentStream(document, page)
        contentStream.beginText()
        contentStream.setFont(PDType1Font.HELVETICA, 12f)
        contentStream.newLineAtOffset(50f, 750f)

        texto.lines().forEach { rawLine ->
            val line = rawLine.replace("\t", "    ")  // 4 espacios en lugar de tab
                .replace(Regex("[\\p{Cntrl}&&[^\n]]"), "") // elimina otros controles

            contentStream.showText(line)
            contentStream.newLineAtOffset(0f, -15f)
        }

        contentStream.endText()
        contentStream.close()

        // Guardar como PDF real
        val file = File(nombreArchivo)
        document.save(file)
        document.close()

        println("PDF guardado en: ${file.absolutePath}")
    } catch (e: Exception) {
        println("Error al guardar el PDF: ${e.message}")
    }
}