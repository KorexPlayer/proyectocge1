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
import com.carlosgrandabastiannunezmartinparada.proyectocge.shared.persistencia.persistenciadatos.ClienteRepoImpl
@Composable
fun paginaListarClientes(onVolver: () -> Unit, repositorioClientes: ClienteRepoImpl) {
    var filtro by remember { mutableStateOf("") }
    Column(modifier = Modifier.fillMaxSize()) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically)
        {
            Text("Listado de Lecturas", style = MaterialTheme.typography.titleMedium)
            campoTextField("Filtro", filtro, onChange = { filtro = it }, Modifier.weight(1f))
            OutlinedButton(onClick = onVolver){Text("Volver") }

        }
        Spacer(Modifier.width(8.dp))

        Row(modifier = Modifier.fillMaxWidth()){
            Text("RUT", modifier = Modifier.weight(1f))
            Text("Nombre", modifier = Modifier.weight(1f))
            Text("Email", modifier = Modifier.weight(1f))
            Text("Direccion", modifier = Modifier.weight(1f))
            Text("Estado", modifier = Modifier.weight(1f))
            Text("Tipo de Lugar", modifier = Modifier.weight(1f))
        }

        Spacer(Modifier.width(8.dp))

        val repositorioClient = repositorioClientes.listar(filtro)
        LazyColumn() {
            items(repositorioClient) { c ->
                Row(modifier = Modifier.fillMaxWidth()) {
                    Text(c.getRut(), modifier = Modifier.weight(1f))
                    Text(c.getNombre(), modifier = Modifier.weight(1f))
                    Text(c.getEmail(), modifier = Modifier.weight(1f))
                    Text(c.getDireccionFacturacion(), modifier = Modifier.weight(1f))
                    Text(c.getEstado().toString(), modifier = Modifier.weight(1f))
                    Text(c.getTipoLugar(), modifier = Modifier.weight(1f))
                }
                Spacer(Modifier.width(2.dp))
            }

        }

    }
}