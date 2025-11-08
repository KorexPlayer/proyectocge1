package com.carlosgrandabastiannunezmartinparada.proyectocge.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.SegmentedButton
import androidx.compose.material3.SegmentedButtonDefaults
import androidx.compose.material3.SingleChoiceSegmentedButtonRow
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
import com.carlosgrandabastiannunezmartinparada.proyectocge.componentes.cajaDespegable
import com.carlosgrandabastiannunezmartinparada.proyectocge.componentes.campoTextField
import com.carlosgrandabastiannunezmartinparada.proyectocge.shared.dominio.Date
import com.carlosgrandabastiannunezmartinparada.proyectocge.shared.dominio.LecturaConsumo
import com.carlosgrandabastiannunezmartinparada.proyectocge.shared.persistencia.persistenciadatos.LecturaRepoImpl

@Composable
fun PantallaLectura(repositorioLecturas: LecturaRepoImpl) {
    var selected by remember { mutableStateOf(0) }
    val options = listOf("Agregar", "Mostrar")
    Column {
        Text("Manejo de Lecturas de Consumo", modifier = Modifier.fillMaxWidth(), style = MaterialTheme.typography.titleLarge, textAlign = TextAlign.Center)
        Box(modifier = Modifier.fillMaxWidth(), contentAlignment = Alignment.Center) {
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
            Spacer(modifier = Modifier.height(5.dp))
            Column(modifier = Modifier.fillMaxSize()) {
                when (selected) {
                    0 -> {
                        pantallaNuevaLectura(repositorioLecturas)
                    }

                    1 -> {
                        paginaListarLecturas(repositorioLecturas)
                    }
                }
            }
        }
    }

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun pantallaNuevaLectura(repositorioLecturas: LecturaRepoImpl) {
    //Datos Colocados manualmente
    var id by remember { mutableStateOf("") } // String
    var idMedidor by remember { mutableStateOf("") } // String
    var kwhLeidos by remember { mutableStateOf("") } // Double
    var aniol by remember { mutableStateOf("") } // Int
    var createdAtAnio by remember { mutableStateOf("") } // Date
    var updatedAtAnio by remember { mutableStateOf("") } // Date
    var mesl by remember { mutableStateOf("") } //Int
    //Datos colocados para created
    var createdAtMes by remember { mutableStateOf("") } // Date
    var createdAtDia by remember { mutableStateOf("") } // Date
    //Datos colocados para updated
    var updatedAtMes by remember { mutableStateOf("") } // Date
    var updatedAtDia by remember { mutableStateOf("") } // Date

    //Para que funcione la caja despegable
    //Días para createdAt y updatedAt
    val dias = listOf(
        "1", "2", "3", "4", "5", "6", "7", "8", "9", "10",
        "11", "12", "13", "14", "15", "16", "17", "18", "19", "20",
        "21", "22", "23", "24", "25", "26", "27", "28", "29", "30",
        "31")
    var diaCreatedExpandido by remember { mutableStateOf(false) }
    var diaUpdatedExpandido by remember { mutableStateOf(false) }

    //Mes para createdAt, updatedAt
    val mes = listOf("1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12")
    var mesCreatedExpandido by remember { mutableStateOf(false) }
    var mesUpdatedExpandido by remember { mutableStateOf(false) }


    Column(horizontalAlignment = Alignment.CenterHorizontally, modifier = Modifier.fillMaxWidth()) {
        Text("Registrar nueva lectura", modifier = Modifier.fillMaxWidth(), style = MaterialTheme.typography.titleLarge, textAlign = TextAlign.Center)
        campoTextField("ID", id, onChange = { id = it }, modifier1 = Modifier.width(800.dp))
        campoTextField("ID del Medidor", idMedidor, onChange = { idMedidor = it }, modifier1 = Modifier.width(800.dp))
        campoTextField("Año de Lectura", aniol, onChange = { aniol = it }, modifier1 = Modifier.width(800.dp))
        campoTextField("Mes de Lectura", mesl, onChange = { mesl = it }, modifier1 = Modifier.width(800.dp))
        campoTextField("Kw/h Leidos", kwhLeidos, onChange = { kwhLeidos = it }, modifier1 = Modifier.width(800.dp))
        //Colocar como cajitas con row.
        Box {
            Row (modifier = Modifier.width(800.dp),
                horizontalArrangement = Arrangement.spacedBy(4.dp),
                verticalAlignment = Alignment.CenterVertically) {
                campoTextField("Año Creado", createdAtAnio, onChange = {createdAtAnio = it}, modifier1 = Modifier.weight(1f).fillMaxWidth())
                campoTextField("Mes Creado", createdAtMes, onChange = {createdAtMes = it}, modifier1 = Modifier.weight(1f).fillMaxWidth())
                campoTextField("Dia Creado", createdAtDia, onChange = {createdAtDia = it}, modifier1 = Modifier.weight(1f).fillMaxWidth())
            }
        }
       Row (modifier = Modifier.width(800.dp),
           horizontalArrangement = Arrangement.spacedBy(1.dp),
           verticalAlignment = Alignment.CenterVertically) {
           campoTextField("Año Actualizado", updatedAtAnio, onChange = {updatedAtAnio = it}, modifier1 = Modifier.weight(1f).fillMaxWidth())
           campoTextField("Mes Actualizado", updatedAtMes, onChange = {updatedAtMes = it}, modifier1 = Modifier.weight(1f).fillMaxWidth())
           campoTextField("Dia Actualizado", updatedAtDia, onChange = {updatedAtDia = it}, modifier1 = Modifier.weight(1f).fillMaxWidth())
        }

            Row(horizontalArrangement = Arrangement.spacedBy(1.dp)) {
                Button(onClick = {
                    repositorioLecturas.registrar(
                        LecturaConsumo(
                            id,
                            Date(createdAtAnio.toInt(), createdAtMes.toInt(), createdAtDia.toInt()),
                            Date(updatedAtAnio.toInt(), updatedAtMes.toInt(), updatedAtDia.toInt()),
                            idMedidor, aniol.toInt(), mesl.toInt(), kwhLeidos.toDouble()
                        )
                    )
                }) {
                    Text(text = "Registrar nueva lectura")
                }
            }
        }
    }
@Composable
private fun paginaListarLecturas(
    repositorio: LecturaRepoImpl
){
    var idMedidor by remember { mutableStateOf("") }
    Text("Listado de Lecturas", style = MaterialTheme.typography.titleLarge, modifier = Modifier.fillMaxWidth(), textAlign = TextAlign.Center)
    Column(modifier = Modifier.fillMaxSize(), horizontalAlignment = Alignment.CenterHorizontally) {
        Row(
            modifier = Modifier.width(800.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically)
        {
            campoTextField("Id Medidor", idMedidor, onChange = { idMedidor = it }, Modifier.weight(1f))
        }
        Spacer(Modifier.width(8.dp))

        Row(modifier = Modifier.width(1280.dp)){
            Text("ID", modifier = Modifier.weight(1f))
            Text("Creado", modifier = Modifier.weight(1f))
            Text("Actualizado", modifier = Modifier.weight(1f))
            Text("ID Medidor", modifier = Modifier.weight(1f))
            Text("Año", modifier = Modifier.weight(1f))
            Text("Mes", modifier = Modifier.weight(1f))
            Text("Kw/h Leidos", modifier = Modifier.weight(1f))
        }

        Spacer(Modifier.width(8.dp))

        val repositorioLista = repositorio.listarPorMedidor(idMedidor, 0 ,0)
        LazyColumn {
            items(repositorioLista) { l ->
                Row(modifier = Modifier.fillMaxWidth()) {
                    Text(l.id, modifier = Modifier.weight(1f))
                    Text(l.createdAt.toString(), modifier = Modifier.weight(1f))
                    Text(l.updatedAt.toString(), modifier = Modifier.weight(1f))
                    Text(l.getIdMedidor(), modifier = Modifier.weight(1f))
                    Text(l.getAnio().toString(), modifier = Modifier.weight(1f))
                    Text(l.getMes().toString(), modifier = Modifier.weight(1f))
                    Text(l.getKwhLeidos().toString(), modifier = Modifier.weight(1f))
                }
                Spacer(Modifier.width(2.dp))
            }

        }

    }
}