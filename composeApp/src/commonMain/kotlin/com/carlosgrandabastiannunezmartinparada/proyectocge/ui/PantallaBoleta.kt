package com.carlosgrandabastiannunezmartinparada.proyectocge.ui

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.carlosgrandabastiannunezmartinparada.proyectocge.componentes.campoTextField
import com.carlosgrandabastiannunezmartinparada.proyectocge.shared.dominio.Boleta
import com.carlosgrandabastiannunezmartinparada.proyectocge.shared.servicios.BoletaService
import com.carlosgrandabastiannunezmartinparada.proyectocge.shared.servicios.PdfService
import kotlinx.coroutines.launch
import com.carlosgrandabastiannunezmartinparada.proyectocge.shared.servicios.guardarPdf

/**
 * Un Composable que representa la pantalla de "Emisión de Boletas".
 *
 * Esta pantalla permite al usuario ingresar un RUT, año y mes para
 * invocar al [boletaService] y generar una nueva [Boleta].
 * Gestiona su propio estado para los campos de entrada, el mensaje de
 * retroalimentación y la boleta generada.
 *
 * Muestra los detalles de la boleta generada y ofrece un botón
 * para exportar un PDF utilizando el [pdfService].
 *
 * @param boletaService El servicio que contiene la lógica de negocio para emitir boletas.
 * @param pdfService El servicio utilizado para generar el archivo PDF.
 */
@Composable
fun PantallaBoleta(
    boletaService: BoletaService,
    pdfService: PdfService
) {
    // --- Estados ---
    var rut by remember { mutableStateOf("") }
    var anio by remember { mutableStateOf("") }
    var mes by remember { mutableStateOf("") }

    var boleta by remember { mutableStateOf<Boleta?>(null) }
    var mensaje by remember { mutableStateOf("") }

    val scope = rememberCoroutineScope()
    val scrollState = rememberScrollState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(scrollState)
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            "Emisión de Boletas",
            style = MaterialTheme.typography.titleLarge,
            modifier = Modifier.fillMaxWidth(),
            textAlign = TextAlign.Center
        )

        Spacer(Modifier.height(16.dp))

        // Campos de entrada
        campoTextField("RUT Cliente", rut, { rut = it }, Modifier.width(400.dp))
        campoTextField("Año", anio, { anio = it }, Modifier.width(400.dp))
        campoTextField("Mes (1–12)", mes, { mes = it }, Modifier.width(400.dp))

        Spacer(Modifier.height(16.dp))

        // --- Botón Emitir ---
        Button(
            onClick = {
                if (rut.isBlank() || anio.isBlank() || mes.isBlank()) {
                    mensaje = "Rellena todos los campos."
                    return@Button
                }

                scope.launch {
                    try {
                        val nuevaBoleta = boletaService.emitirBoletaMensual(
                            rutCliente = rut,
                            anio = anio.toInt(),
                            mes = mes.toInt()
                        )
                        boleta = nuevaBoleta
                        mensaje = "Boleta emitida correctamente."
                    } catch (e: Exception) {
                        mensaje = "Error al emitir: ${e.message}"
                    }
                }
            },
            modifier = Modifier.height(48.dp)
        ) {
            Text("Emitir Boleta", style = MaterialTheme.typography.titleLarge)
        }

        Spacer(Modifier.height(16.dp))

        if (mensaje.isNotEmpty()) {
            Text(
                mensaje,
                color = if (mensaje.startsWith("Error")) MaterialTheme.colorScheme.error
                else MaterialTheme.colorScheme.primary
            )
        }

        Spacer(Modifier.height(24.dp))

        // --- Mostrar tabla si existe boleta ---
        boleta?.let { b ->
            val detalle = b.getDetalle()

            Column(
                modifier = Modifier.width(600.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    "Detalle de Boleta",
                    style = MaterialTheme.typography.titleMedium,
                    textAlign = TextAlign.Center
                )
                Spacer(Modifier.height(8.dp))
                Divider()

                val filas = listOf(
                    "Cliente RUT" to b.getCliente().getRut(),
                    "Periodo" to "${b.getMes()}/${b.getAnio()}",
                    "Consumo (kWh)" to b.getKwhTotal().toString(),
                    "Subtotal" to "${detalle.subtotal}",
                    "Cargos" to "${detalle.cargos}",
                    "IVA (19%)" to "${detalle.iva}",
                    "Total a pagar" to "${detalle.total}"
                )

                filas.forEach { (dato, valor) ->
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 4.dp),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(dato, style = MaterialTheme.typography.bodyLarge)
                        Text(valor, style = MaterialTheme.typography.bodyLarge)
                    }
                    Divider()
                }

                Spacer(Modifier.height(24.dp))

                // --- Botón Exportar PDF ---
                Button(
                    onClick = {
                        scope.launch {
                            try {
                                val bytes = boletaService.exportarPdfClienteMes(
                                    rutCliente = rut,
                                    anio = anio.toInt(),
                                    mes = mes.toInt(),
                                    pdf = pdfService
                                )
                                guardarPdf("boleta_${rut}_${mes}_${anio}.pdf", bytes)
                                mensaje = "PDF generado y guardado correctamente."
                            } catch (e: Exception) {
                                mensaje = "Error al exportar PDF: ${e.message}"
                            }
                        }
                    },
                    modifier = Modifier.height(48.dp)
                ) {
                    Text("Exportar a PDF", style = MaterialTheme.typography.titleLarge)
                }
            }
        }
    }
}