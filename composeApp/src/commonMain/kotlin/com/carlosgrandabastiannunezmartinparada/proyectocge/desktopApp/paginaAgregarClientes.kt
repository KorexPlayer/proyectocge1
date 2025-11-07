package com.carlosgrandabastiannunezmartinparada.proyectocge.desktopApp

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import com.carlosgrandabastiannunezmartinparada.proyectocge.componentes.campoTextField
import com.carlosgrandabastiannunezmartinparada.proyectocge.shared.dominio.Cliente
import com.carlosgrandabastiannunezmartinparada.proyectocge.shared.dominio.EstadoCliente
import com.carlosgrandabastiannunezmartinparada.proyectocge.shared.persistencia.persistenciadatos.ClienteRepoImpl
import com.carlosgrandabastiannunezmartinparada.proyectocge.shared.persistencia.persistenciadatos.LecturaRepoImpl

@Composable
fun paginaAgregarClientes(onVolver: () -> Unit, repositorioClientes: ClienteRepoImpl) {
    var rut by remember { mutableStateOf("") }
    var nombre by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var direccion by remember { mutableStateOf("") }
    var estado by remember {mutableStateOf(EstadoCliente.ACTIVO)}
    var tipoLugar by remember { mutableStateOf("") }

    Column {
        campoTextField("RUT", rut, onChange = { rut = it }, modifier = Modifier.fillMaxWidth())
        campoTextField("Nombre", nombre, onChange = { nombre = it }, modifier = Modifier.fillMaxWidth())
        campoTextField("Email", email, onChange = { email = it }, modifier = Modifier.fillMaxWidth())
        campoTextField("Direccion", direccion, onChange = { direccion = it }, modifier = Modifier.fillMaxWidth())
        //campoTextField("Estado", estado, onChange = { estado = it }, modifier = Modifier.fillMaxWidth())
        campoTextField("Tipo de Lugar", tipoLugar, onChange = { tipoLugar = it }, modifier = Modifier.fillMaxWidth())

        Row {
            Button(onClick = { onVolver() }) {
               Text("Volver")
            }
            Button(onClick = { repositorioClientes.crear(Cliente(rut, nombre, email, direccion, estado, tipoLugar))
            rut = ""
            nombre = ""
            email = ""
            direccion = ""
            tipoLugar = ""}) {
                Text("Guardar Cliente")
            }
        }
    }


 }