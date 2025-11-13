package com.carlosgrandabastiannunezmartinparada.proyectocge.componentes

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

/**
 * Un Composable reutilizable que muestra un [OutlinedTextField] con una etiqueta
 * y un [Spacer] fijo de 8.dp debajo para añadir espaciado vertical.
 *
 * @param label El texto que se muestra como etiqueta (label) flotante en él [OutlinedTextField].
 * @param value El valor de texto actual que se mostrará en el campo.
 * @param onChange El callback (lambda) que se invoca cuando el usuario modifica el texto.
 * Recibe el nuevo [String] como parámetro.
 * @param modifier1 El [Modifier] de Compose que se aplicará directamente al [OutlinedTextField].
 */

@Composable
fun campoTextField(
    label : String,
    value : String,
    onChange: (String) -> Unit,
    modifier1: Modifier = Modifier
) {
    OutlinedTextField(
        value = value,
        onValueChange = onChange,
        label = { Text(label) },
        singleLine = true,
        modifier = modifier1
    )
    Spacer(modifier = Modifier.height(8.dp))
}