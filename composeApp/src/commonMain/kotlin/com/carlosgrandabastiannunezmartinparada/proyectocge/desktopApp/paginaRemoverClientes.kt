package com.carlosgrandabastiannunezmartinparada.proyectocge.desktopApp

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.carlosgrandabastiannunezmartinparada.proyectocge.componentes.campoTextField
import com.carlosgrandabastiannunezmartinparada.proyectocge.shared.persistencia.persistenciadatos.ClienteRepoImpl
@Composable
fun paginaRemoverClientes(onVolver: () -> Unit, repositorioClientes: ClienteRepoImpl) {

    var rut by remember { mutableStateOf("") }
    Column () {
        Text("Remover Clientes", style = MaterialTheme.typography.titleLarge)

        campoTextField("RUT a Eliminar", rut, onChange = { rut = it }, Modifier.fillMaxWidth())

        Row {
            Button(onClick = { onVolver() }) {
                Text("Volver")
            }
            OutlinedButton( onClick = {
                repositorioClientes.eliminar(rut)
            }) {
                Text("Eliminar")
            }
        }
    }

}