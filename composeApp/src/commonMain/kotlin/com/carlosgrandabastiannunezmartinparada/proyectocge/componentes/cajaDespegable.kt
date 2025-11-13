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

/**
 * Una función que implementa un menú desplegable utilizando [ExposedDropdownMenuBox].
 * * Este componente gestiona su propio estado de expansión y el valor seleccionado internamente,
 * comenzando con los valores proporcionados. Devuelve el valor actualmente seleccionado.
 *
 * @param estadoEntregado El estado de expansión inicial del menú (true = expandido, false = contraído).
 * @param valorEntregado El valor inicial seleccionado que se mostrará en él [OutlinedTextField].
 * @param label El texto que se muestra como etiqueta (label) flotante en él [OutlinedTextField].
 * @param listado La lista de [String] que representa las opciones a mostrar en el menú.
 * @param modifier1 El [Modifier] de Compose que se aplicará al contenedor principal [ExposedDropdownMenuBox].
 * @return El [String] correspondiente al valor actualmente seleccionado por el usuario en el menú.
 */

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun cajaDespegable(estadoEntregado: Boolean, valorEntregado: String, label: String, listado: List<String>, modifier1: Modifier = Modifier): String{

    var estado by remember { mutableStateOf(estadoEntregado) }
    var valorEntregado by remember { mutableStateOf(valorEntregado) }


    ExposedDropdownMenuBox(
        expanded = estado,
        onExpandedChange = { estado = !estado },
        modifier = modifier1
    ) {
        OutlinedTextField(
            value = valorEntregado,
            onValueChange = {},
            readOnly = true,
            label = { Text(text = label) },
            trailingIcon = {
                ExposedDropdownMenuDefaults.TrailingIcon(expanded = estado)
            },
            modifier = Modifier
                .fillMaxWidth()
                .menuAnchor()
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
    return valorEntregado
}