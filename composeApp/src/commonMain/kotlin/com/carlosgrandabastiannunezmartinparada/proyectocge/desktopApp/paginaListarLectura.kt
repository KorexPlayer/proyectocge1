package com.carlosgrandabastiannunezmartinparada.proyectocge.desktopApp

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
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
import com.carlosgrandabastiannunezmartinparada.proyectocge.shared.persistencia.persistenciadatos.LecturaRepoImpl

@Composable
fun paginaListarLecturas(
    repositorio: LecturaRepoImpl,
    onVolver:() ->Unit,
){
    var idMedidor by remember { mutableStateOf("") }
    Column(modifier = Modifier.fillMaxSize()) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically)
        {
            Text("Listado de Lecturas", style = MaterialTheme.typography.titleMedium)
            campoTextField("Id Medidor", idMedidor, onChange = { idMedidor = it }, Modifier.weight(1f))
            OutlinedButton(onClick = onVolver){Text("Volver") }

        }
        Spacer(Modifier.width(8.dp))

        Row(modifier = Modifier.fillMaxWidth()){
            Text("ID", modifier = Modifier.weight(1f))
            Text("Creado", modifier = Modifier.weight(1f))
            Text("Actualizado", modifier = Modifier.weight(1f))
            Text("ID Medidor", modifier = Modifier.weight(1f))
            Text("Año", modifier = Modifier.weight(1f))
            Text("Mes", modifier = Modifier.weight(1f))
            Text("KWh Leidos", modifier = Modifier.weight(1f))
        }

        Spacer(Modifier.width(8.dp))

        val repositorioLista = repositorio.listarPorMedidor(idMedidor, 0 ,0)
        LazyColumn() {
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