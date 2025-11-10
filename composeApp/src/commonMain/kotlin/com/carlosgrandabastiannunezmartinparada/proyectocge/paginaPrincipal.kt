package com.carlosgrandabastiannunezmartinparada.proyectocge

import androidx.compose.foundation.layout.Arrangement
import com.carlosgrandabastiannunezmartinparada.proyectocge.componentes.tema.darkScheme
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SegmentedButton
import androidx.compose.material3.SegmentedButtonDefaults
import androidx.compose.material3.SingleChoiceSegmentedButtonRow
import androidx.compose.material3.Surface
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.carlosgrandabastiannunezmartinparada.proyectocge.componentes.tema.lightScheme
import com.carlosgrandabastiannunezmartinparada.proyectocge.shared.persistencia.persistenciadatos.ClienteRepoImpl
import com.carlosgrandabastiannunezmartinparada.proyectocge.shared.persistencia.persistenciadatos.LecturaRepoImpl
import com.carlosgrandabastiannunezmartinparada.proyectocge.shared.persistencia.persistenciadatos.MedidorRepoImpl
import com.carlosgrandabastiannunezmartinparada.proyectocge.shared.persistencia.persistenciadatos.BoletaRepoImpl
import com.carlosgrandabastiannunezmartinparada.proyectocge.shared.servicios.BoletaService
import com.carlosgrandabastiannunezmartinparada.proyectocge.shared.servicios.TarifaService
import com.carlosgrandabastiannunezmartinparada.proyectocge.shared.servicios.PdfService
import com.carlosgrandabastiannunezmartinparada.proyectocge.ui.PantallaBoleta
import com.carlosgrandabastiannunezmartinparada.proyectocge.ui.PantallaClientes
import com.carlosgrandabastiannunezmartinparada.proyectocge.ui.PantallaLectura
import com.carlosgrandabastiannunezmartinparada.proyectocge.ui.PantallaMedidor


@Composable
fun paginaPrincipal() {
    val repositorioClientes = ClienteRepoImpl
    val repositorioLecturas = LecturaRepoImpl
    val repositorioMedidores = MedidorRepoImpl
    val repositorioBoletas = BoletaRepoImpl
    val tarifaService = TarifaService()
    val pdfService = PdfService()
    val boletaService = BoletaService(repositorioClientes, repositorioMedidores, repositorioLecturas, repositorioBoletas, tarifaService)
    var selected by remember { mutableStateOf(0) }
    val options = listOf("Clientes", "Boletas", "Lecturas", "Medidores")
    var checked by remember { mutableStateOf(true) }
    MaterialTheme(colorScheme = if(checked) {darkScheme} else {lightScheme}) {
        Surface {
            Column {
                Text("Proyecto CGE", style = MaterialTheme.typography.titleLarge, modifier = Modifier.fillMaxWidth(), textAlign = TextAlign.Center)
                Text("Creado por: Carlos Granda, Bastian Nuñez y Martin Parada", style = MaterialTheme.typography.bodyLarge, modifier = Modifier.fillMaxWidth(), textAlign = TextAlign.Center)
                Box (modifier = Modifier.fillMaxWidth(), contentAlignment = Alignment.Center) {
                    Row(modifier = Modifier.width(200.dp), verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.Center) {
                        Text("TemaOscuro: ",modifier = Modifier.weight(1f), textAlign = TextAlign.Right)
                        Switch(checked = checked, onCheckedChange = { checked = it }, modifier = Modifier.weight(1f))
                    }
                }
                Box(Modifier.fillMaxWidth().padding(4.dp), contentAlignment = Alignment.Center) {
                    Spacer(modifier = Modifier.size(1.dp))
                    SingleChoiceSegmentedButtonRow {
                        options.forEachIndexed { index, option ->
                            SegmentedButton(
                                shape = SegmentedButtonDefaults.itemShape(
                                    index = index,
                                    count = options.size
                                ), onClick = { selected = index },
                                selected = index == selected,
                                label = { Text(option) }
                            )
                        }
                    }
                }
                    Spacer(modifier = Modifier.size(1.dp))
                    Column(modifier = Modifier.fillMaxSize()) {
                        when (selected) {
                            0 -> {
                                PantallaClientes(repositorioClientes)
                            }

                            1 -> {
                                PantallaBoleta(boletaService,pdfService)
                            }

                            2 -> {
                                PantallaLectura(repositorioLecturas, )
                            }

                            3 -> {
                                PantallaMedidor(repositorioMedidores)
                            }
                        }
                    }
            }
        }
    }
}