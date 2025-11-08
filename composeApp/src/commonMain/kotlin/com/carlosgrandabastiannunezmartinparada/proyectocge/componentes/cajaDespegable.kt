package com.carlosgrandabastiannunezmartinparada.proyectocge.componentes

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import com.carlosgrandabastiannunezmartinparada.proyectocge.shared.dominio.EstadoCliente

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun cajaDespegable(estadoentregado: Boolean, valorEntregado: String, label: String, listado: List<String>, modifier1: Modifier = Modifier){

    var estado by remember { mutableStateOf(estadoentregado) }
    var valorEntregado by remember { mutableStateOf(valorEntregado) }


    ExposedDropdownMenuBox(
        expanded = estado,
        onExpandedChange = { estado = !estado },
        modifier = modifier1
    ) {
        OutlinedTextField(
            value = valorEntregado,
            onValueChange = {}, // No se cambia escribiendo
            readOnly = true,
            label = { Text(text = label) },
            trailingIcon = {
                ExposedDropdownMenuDefaults.TrailingIcon(expanded = estado)
            },
            modifier = Modifier
                .fillMaxWidth()
                .menuAnchor() // Importante para anclar el menú
        )
        // Menú desplegable
        ExposedDropdownMenu(
            expanded = estado,
            onDismissRequest = { estado = false }
        ) {
            listado.forEach { opcion ->
                DropdownMenuItem(
                    text = { Text(opcion) },
                    onClick = {
                        valorEntregado = opcion
                        estado = false
                    }
                )
            }
        }
    }
}