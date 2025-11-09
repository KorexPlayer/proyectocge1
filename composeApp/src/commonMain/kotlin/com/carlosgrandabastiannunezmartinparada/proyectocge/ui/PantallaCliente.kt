package com.carlosgrandabastiannunezmartinparada.proyectocge.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
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
import com.carlosgrandabastiannunezmartinparada.proyectocge.componentes.tema.backgroundLight
import com.carlosgrandabastiannunezmartinparada.proyectocge.shared.dominio.Cliente
import com.carlosgrandabastiannunezmartinparada.proyectocge.shared.dominio.EstadoCliente
import com.carlosgrandabastiannunezmartinparada.proyectocge.shared.persistencia.persistenciadatos.ClienteRepoImpl
import com.carlosgrandabastiannunezmartinparada.proyectocge.shared.persistencia.persistenciadatos.LecturaRepoImpl

@Composable
fun PantallaClientes(repositorioClientes: ClienteRepoImpl) {
    var selected by remember { mutableStateOf(0) }
    val options = listOf("Agregar", "Remover", "Mostrar")
    Column {
        Text("Manejo de Clientes", style = MaterialTheme.typography.titleLarge, modifier = Modifier.fillMaxWidth(), textAlign = TextAlign.Center)
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

        Spacer(modifier = Modifier.size(5.dp))
        Column(modifier = Modifier.fillMaxSize()) {
            when(selected) {
                0 -> {
                    paginaAgregarClientes(repositorioClientes)
                }
                1 -> {
                    paginaRemoverClientes(repositorioClientes)
                }
                2 -> {
                    paginaListarClientes(repositorioClientes)
                }
            }
        }
    }
}

@Composable
private fun paginaAgregarClientes(repositorioClientes: ClienteRepoImpl) {
    var rut by remember { mutableStateOf("") }
    var nombre by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var direccion by remember { mutableStateOf("") }

    //Estado del cliente
    var estadoSeleccionado by remember { mutableStateOf("") }
    var estadoExpandido by remember { mutableStateOf(false) }
    val estados = listOf("ACTIVO", "INACTIVO")

    //Tipo de lugar
    var tipoLugar by remember { mutableStateOf("") }
    var tipoExpandido by remember { mutableStateOf(false) }
    val tipos = listOf("Residencial", "Comercial")
    val scrollState = rememberScrollState()
        Column(horizontalAlignment = Alignment.CenterHorizontally, modifier = Modifier.fillMaxWidth().verticalScroll(scrollState)) {
            Text("Agregar Cliente", modifier = Modifier.fillMaxWidth(), style = MaterialTheme.typography.titleLarge, textAlign = TextAlign.Center)
            campoTextField("RUT", rut, onChange = { rut = it }, modifier1 = Modifier.width(800.dp))
            campoTextField("Nombre", nombre, onChange = { nombre = it }, modifier1 = Modifier.width(800.dp))
            campoTextField("Email", email, onChange = { email = it }, modifier1 = Modifier.width(800.dp))
            campoTextField("Direccion", direccion, onChange = { direccion = it }, modifier1 = Modifier.width(800.dp))
            val estado = cajaDespegable(estadoExpandido, estadoSeleccionado, "Estado", estados, Modifier.width(800.dp))
            val tipoLugar1 = cajaDespegable(tipoExpandido, tipoLugar, label = "Tipo de Lugar", tipos, modifier1 = Modifier.width(800.dp))
            Box(modifier = Modifier.fillMaxWidth(), contentAlignment = Alignment.Center) {
            Row() {
                val estadoFinal = if(estado == "ACTIVO"){
                    EstadoCliente.ACTIVO} else { EstadoCliente.INACTIVO }
                val tipoFinal = if(tipoLugar1 == "Residencial"){
                    "Residencial" } else { "Comercial" }
                Button(onClick = {
                    repositorioClientes.crear(Cliente(rut, nombre, email, direccion, estadoFinal, tipoFinal))
                    rut = ""
                    nombre = ""
                    email = ""
                    direccion = ""
                    tipoLugar = ""
                    estadoSeleccionado = ""

                }) {
                    Text("Guardar Cliente")
                }
                Spacer(modifier = Modifier.size(10.dp))
                Button(onClick = {
                    repositorioClientes.actualizar(Cliente(rut, nombre, email, direccion, estadoFinal, tipoFinal))
                    rut = ""
                    nombre = ""
                    email = ""
                    direccion = ""
                    tipoLugar = ""
                    estadoSeleccionado = ""}) {
                    Text("Sobreescribir Cliente")
                }
            }
        }
    }
}

@Composable
private fun paginaListarClientes(repositorioClientes: ClienteRepoImpl) {
    var filtro by remember { mutableStateOf("") }
    Text("Listado de Clientes", style = MaterialTheme.typography.titleLarge, modifier = Modifier.fillMaxWidth(), textAlign = TextAlign.Center)
    Column(modifier = Modifier.fillMaxSize(), horizontalAlignment = Alignment.CenterHorizontally) {
        Row(
            modifier = Modifier.width(800.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically)
        {
            campoTextField("Filtro (Por RUT o Nombre)", filtro, onChange = { filtro = it }, Modifier.weight(1f))
        }
        Spacer(Modifier.width(8.dp))
        Row(modifier = Modifier.width(1280.dp)){
            Text("RUT", modifier = Modifier.weight(1f), textAlign = TextAlign.Center)
            Text("Nombre", modifier = Modifier.weight(1f), textAlign = TextAlign.Center)
            Text("Email", modifier = Modifier.weight(1f), textAlign = TextAlign.Center)
            Text("Direccion", modifier = Modifier.weight(1f), textAlign = TextAlign.Center)
            Text("Estado", modifier = Modifier.weight(1f), textAlign = TextAlign.Center)
            Text("Tipo de Lugar", modifier = Modifier.weight(1f), textAlign = TextAlign.Center)
        }
        Spacer(Modifier.width(8.dp))
        val repositorioClient = repositorioClientes.listar(filtro)
        LazyColumn() {
            items(repositorioClient) { c ->
                Row(modifier = Modifier.width(1280.dp)) {
                    Text(c.getRut(), modifier = Modifier.weight(1f), textAlign = TextAlign.Center)
                    Text(c.getNombre(), modifier = Modifier.weight(1f), textAlign = TextAlign.Center)
                    Text(c.getEmail(), modifier = Modifier.weight(1f), textAlign = TextAlign.Center)
                    Text(c.getDireccionFacturacion(), modifier = Modifier.weight(1f), textAlign = TextAlign.Center)
                    Text(c.getEstado().toString(), modifier = Modifier.weight(1f), textAlign = TextAlign.Center)
                    Text(c.getTipoLugar(), modifier = Modifier.weight(1f), textAlign = TextAlign.Center)
                }
                Spacer(Modifier.width(2.dp))
            }

        }

    }
}

@Composable
private fun paginaRemoverClientes(repositorioClientes: ClienteRepoImpl) {

    var rut by remember { mutableStateOf("") }
    Text("Remover Clientes", style = MaterialTheme.typography.titleLarge, modifier = Modifier.fillMaxWidth(), textAlign = TextAlign.Center)
    Column (modifier = Modifier.fillMaxWidth(), horizontalAlignment = Alignment.CenterHorizontally) {
        campoTextField("RUT a Eliminar", rut, onChange = { rut = it }, Modifier.width(800.dp))
        Row {
            Button( onClick = {
                repositorioClientes.eliminar(rut)
                rut = ""
                }) {
                Text("Eliminar")
            }
        }
    }
}