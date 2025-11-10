package com.carlosgrandabastiannunezmartinparada.proyectocge.shared.servicios

import kotlinx.browser.document
import org.w3c.dom.HTMLAnchorElement
import org.w3c.files.Blob
import org.w3c.dom.url.URL
import org.w3c.files.BlobPropertyBag

actual fun guardarPdf(nombreArchivo: String, bytes: ByteArray) {
    val texto = bytes.decodeToString()

    // Crear un Blob con mime type de PDF
    val blob = Blob(arrayOf(texto), BlobPropertyBag(type = "application/pdf"))
    val url = URL.createObjectURL(blob)

    val a = document.createElement("a") as HTMLAnchorElement
    a.href = url
    a.download = nombreArchivo
    a.click()

    URL.revokeObjectURL(url)
}
