package com.carlosgrandabastiannunezmartinparada.proyectocge.desktopApp

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.carlosgrandabastiannunezmartinparada.proyectocge.componentes.campoTextField
import com.carlosgrandabastiannunezmartinparada.proyectocge.shared.dominio.Date
import com.carlosgrandabastiannunezmartinparada.proyectocge.shared.dominio.LecturaConsumo
import com.carlosgrandabastiannunezmartinparada.proyectocge.shared.persistencia.persistenciadatos.LecturaRepoImpl

@Composable
fun pantallaNuevaLectura( onVolver: () -> Unit, repositorioLecturas: LecturaRepoImpl) {
    var id by remember { mutableStateOf("") }
    var createdAtAnio by remember { mutableStateOf("") }
    var createdAtMes by remember { mutableStateOf("") }
    var createdAtDia by remember { mutableStateOf("") }
    var updatedAtAnio by remember { mutableStateOf("") }
    var updatedAtMes by remember { mutableStateOf("") }
    var updatedAtDia by remember { mutableStateOf("") }
    var idMedidor by remember { mutableStateOf("") }
    var anio by remember { mutableStateOf("") }
    var mes by remember { mutableStateOf("") }
    var kwhLeidos by remember { mutableStateOf("") }

    Column (
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text("Registrar nueva lectura.", style = MaterialTheme.typography.titleLarge)
        Spacer(modifier = Modifier.height(16.dp))
        campoTextField("id", id, onChange = {id = it})
        campoTextField("idMedidor", idMedidor, onChange = {idMedidor = it}, modifier = Modifier.width(18.dp))
        campoTextField("Aniol", anio, onChange = {anio = it}, modifier = Modifier.width(18.dp))
        campoTextField("mesl", mes, onChange = {mes = it}, modifier = Modifier.width(18.dp))
        campoTextField("KwhLeidos", kwhLeidos, onChange = {kwhLeidos = it}, modifier = Modifier.width(18.dp))
        //Colocar como cajitas con row.
        campoTextField("CAnio", createdAtAnio, onChange = {createdAtAnio = it}, modifier = Modifier.width(18.dp))
        campoTextField("CMes", createdAtMes, onChange = {createdAtMes = it}, modifier = Modifier.width(18.dp))
        campoTextField("CDia", createdAtDia, onChange = {createdAtDia = it}, modifier = Modifier.width(18.dp))
        Spacer(modifier = Modifier.height(5.dp))
        campoTextField("Anio", updatedAtAnio, onChange = {updatedAtAnio = it}, modifier = Modifier.width(18.dp))
        campoTextField("Mes", updatedAtMes, onChange = {updatedAtMes = it}, modifier = Modifier.width(18.dp))
        campoTextField("Dia", updatedAtDia, onChange = {updatedAtDia = it}, modifier = Modifier.width(18.dp))
        Row (horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            Button(onClick = {
                repositorioLecturas.registrar(LecturaConsumo(id,
                    Date(createdAtAnio.toInt(), createdAtMes.toInt(), createdAtDia.toInt()),
                    Date(updatedAtAnio.toInt(), updatedAtMes.toInt(), updatedAtDia.toInt()),
                    idMedidor, anio.toInt(), mes.toInt(), kwhLeidos.toDouble()))
            }){
                Text(text = "Registrar nueva lectura")
            }
            OutlinedButton(onClick = onVolver) {
                Text("Regresar")
            }
        }


    }
}