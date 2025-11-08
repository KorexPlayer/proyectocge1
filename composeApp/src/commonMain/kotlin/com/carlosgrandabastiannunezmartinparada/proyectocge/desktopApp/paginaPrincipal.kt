package com.carlosgrandabastiannunezmartinparada.proyectocge.desktopApp

import com.carlosgrandabastiannunezmartinparada.proyectocge.componentes.tema.darkScheme
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SegmentedButton
import androidx.compose.material3.SegmentedButtonDefaults
import androidx.compose.material3.SingleChoiceSegmentedButtonRow
import androidx.compose.material3.Surface
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
import com.carlosgrandabastiannunezmartinparada.proyectocge.componentes.campoTextField
import com.carlosgrandabastiannunezmartinparada.proyectocge.shared.persistencia.persistenciadatos.ClienteRepoImpl
import com.carlosgrandabastiannunezmartinparada.proyectocge.shared.persistencia.persistenciadatos.LecturaRepoImpl
import com.carlosgrandabastiannunezmartinparada.proyectocge.ui.PantallaClientes
import com.carlosgrandabastiannunezmartinparada.proyectocge.ui.PantallaLectura

@Composable
fun paginaPrincipal() {
    val repositorioClientes = ClienteRepoImpl
    val repositorioLecturas = LecturaRepoImpl
    var test by remember { mutableStateOf("") }
    var selected by remember { mutableStateOf(0) }
    val options = listOf("Clientes", "Boletas", "Lecturas", "Medidores")
    MaterialTheme(colorScheme = darkScheme) {
        Surface {
            Column {
                Text("Proyecto CGE", style = MaterialTheme.typography.titleLarge, modifier = Modifier.fillMaxWidth(), textAlign = TextAlign.Center)
                Text("Creado por: Carlos Granda, Bastian Nuñez y Martin Parada", style = MaterialTheme.typography.bodyLarge, modifier = Modifier.fillMaxWidth(), textAlign = TextAlign.Center)
                Box(Modifier.fillMaxWidth().padding(16.dp), contentAlignment = Alignment.Center) {
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
                    Spacer(modifier = Modifier.size(5.dp))
                    Column(modifier = Modifier.fillMaxSize()) {
                        when (selected) {
                            0 -> {
                                PantallaClientes(repositorioClientes)
                            }

                            1 -> {
                                PantallaLectura(repositorioLecturas)
                            }

                            2 -> {
                                //PantallaBoleta(repositorioBoletas)
                            }

                            3 -> {
                                //PantallaMedidor(repositorioMedidores)
                            }
                        }
                    }
            }
        }
    }
}