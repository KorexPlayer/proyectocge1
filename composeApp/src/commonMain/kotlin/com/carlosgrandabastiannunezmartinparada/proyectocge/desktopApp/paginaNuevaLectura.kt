package com.carlosgrandabastiannunezmartinparada.proyectocge.desktopApp

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier

@Composable
fun pantallaNuevaLectura() {
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
    }
}