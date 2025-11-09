package com.carlosgrandabastiannunezmartinparada.proyectocge.ui
//
//import androidx.compose.foundation.layout.Arrangement
//import androidx.compose.foundation.layout.Column
//import androidx.compose.foundation.layout.Row
//import androidx.compose.foundation.layout.Spacer
//import androidx.compose.foundation.layout.fillMaxSize
//import androidx.compose.foundation.layout.fillMaxWidth
//import androidx.compose.foundation.layout.height
//import androidx.compose.foundation.layout.padding
//import androidx.compose.foundation.lazy.LazyColumn
//import androidx.compose.foundation.lazy.items
//import androidx.compose.material3.Button
//import androidx.compose.material3.Divider
//import androidx.compose.material3.MaterialTheme
//import androidx.compose.material3.OutlinedButton
//import androidx.compose.material3.SegmentedButton
//import androidx.compose.material3.SegmentedButtonDefaults
//import androidx.compose.material3.SingleChoiceSegmentedButtonRow
//import androidx.compose.material3.Text
//import androidx.compose.runtime.Composable
//import androidx.compose.runtime.getValue
//import androidx.compose.runtime.mutableStateOf
//import androidx.compose.runtime.remember
//import androidx.compose.runtime.setValue
//import androidx.compose.ui.Alignment
//import androidx.compose.ui.Modifier
//import androidx.compose.ui.unit.dp
//import com.carlosgrandabastiannunezmartinparada.proyectocge.componentes.campoTextField
//import com.carlosgrandabastiannunezmartinparada.proyectocge.shared.dominio.*
//import com.carlosgrandabastiannunezmartinparada.proyectocge.shared.persistencia.repositorios.MedidorRepositorio
//import java.util.UUID
//import java.util.Date
//
//@Composable
//fun PantallaMedidor(repositorioMedidores: MedidorRepositorio) {
//    var selected by remember { mutableStateOf(0) }
//    val options = listOf("Agregar", "Remover", "Mostrar")
//
//        SingleChoiceSegmentedButtonRow(Modifier.fillMaxWidth().padding(horizontal = 8.dp)) {
//        options.forEachIndexed { index, option ->
//        SegmentedButton(
//        shape = SegmentedButtonDefaults.itemShape(
//        index = index,
//        count = options.size
//        ),
//        onClick = { selected = index },
//        selected = index == selected,
//        label = { Text(option) }
//        )
//        }
//        }
//
//        Column(modifier = Modifier.fillMaxSize().padding(horizontal = 16.dp)) {
//        when(selected) {
//        0 -> paginaAgregarMedidor(repositorioMedidores)
//        1 -> paginaRemoverMedidor(repositorioMedidores)
//        2 -> paginaListarMedidores(repositorioMedidores)
//        }
//        }
//        }
//
//        @Composable
//        private fun paginaAgregarMedidor(repositorioMedidores: MedidorRepositorio) {
//        var codigo by remember { mutableStateOf("") }
//        var rutClienteAsociado by remember { mutableStateOf("") }
//        var direccion by remember { mutableStateOf("") }
//        var potenciaMaxKw by remember { mutableStateOf("") }
//
//        var tipoMedidor by remember { mutableStateOf<String>("Monofasico") }
//        var factorPotencia by remember { mutableStateOf("") }
//
//        var mensajeEstado by remember { mutableStateOf("") }
//
//        Column(Modifier.fillMaxWidth(), horizontalAlignment = Alignment.CenterHorizontally) {
//        Text("Agregar Nuevo Medidor", style = MaterialTheme.typography.titleLarge)
//        Spacer(Modifier.height(16.dp))
//
//        campoTextField("Código", codigo, onChange = { codigo = it }, modifier = Modifier.fillMaxWidth())
//        campoTextField("RUT Cliente", rutClienteAsociado, onChange = { rutClienteAsociado = it }, modifier = Modifier.fillMaxWidth())
//        campoTextField("Dirección Suministro", direccion, onChange = { direccion = it }, modifier = Modifier.fillMaxWidth())
//        campoTextField("Potencia Max (kW)", potenciaMaxKw, onChange = { potenciaMaxKw = it }, modifier = Modifier.fillMaxWidth())
//
//        Spacer(Modifier.height(16.dp))
//
//        Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceEvenly) {
//        Button(onClick = { tipoMedidor = "Monofasico" }, enabled = tipoMedidor != "Monofasico") { Text("Monofásico") }
//        Button(onClick = { tipoMedidor = "Trifasico" }, enabled = tipoMedidor != "Trifasico") { Text("Trifásico") }
//        }
//
//        if (tipoMedidor == "Trifasico") {
//        campoTextField("Factor de Potencia", factorPotencia, onChange = { factorPotencia = it }, modifier = Modifier.fillMaxWidth())
//        }
//
//        Spacer(Modifier.height(24.dp))
//
//        Button(
//        onClick = {
//        try {
//        if (codigo.isBlank() || rutClienteAsociado.isBlank()) {
//        mensajeEstado = "Faltan Código o RUT del Cliente."
//        return@Button
//        }
//
//        val clienteAsociado = Cliente(rutClienteAsociado, "Temp", "", direccion, EstadoCliente.ACTIVO, "Residencial")
//
//        val nuevoMedidor: Medidor = if (tipoMedidor == "Monofasico") {
//        MedidorMonofasico(
//        id = UUID.randomUUID().toString(),
//        createdAt = Date(), updatedAt = Date(),
//        codigo = codigo,
//        direccionSuministro = direccion,
//        activo = true,
//        cliente = clienteAsociado,
//        potenciaMaxKw = potenciaMaxKw.toDoubleOrNull() ?: 0.0
//        )
//        } else {
//        MedidorTrifasico(
//        id = UUID.randomUUID().toString(),
//        createdAt = Date(), updatedAt = Date(),
//        codigo = codigo,
//        direccionSuministro = direccion,
//        activo = true,
//        cliente = clienteAsociado,
//        potenciaMaxKw = potenciaMaxKw.toDoubleOrNull() ?: 0.0,
//        factorPotencia = factorPotencia.toDoubleOrNull() ?: 0.0
//        )
//        }
//
//        repositorioMedidores.crear(nuevoMedidor, rutClienteAsociado)
//        mensajeEstado = "Medidor ${nuevoMedidor.tipo()} registrado para $rutClienteAsociado."
//
//        codigo = ""; rutClienteAsociado = ""; direccion = ""; potenciaMaxKw = ""; factorPotencia = ""
//        } catch (e: Exception) {
//        mensajeEstado = "Error: ${e.message}"
//        }
//        },
//        enabled = codigo.isNotBlank() && rutClienteAsociado.isNotBlank()
//        ) {
//        Text("Guardar Medidor")
//        }
//        Spacer(Modifier.height(16.dp))
//        Text(mensajeEstado)
//        }
//        }
//
//        @Composable
//        private fun paginaRemoverMedidor(repositorioMedidores: MedidorRepositorio) {
//        var codigo by remember { mutableStateOf("") }
//        var mensajeEstado by remember { mutableStateOf("") }
//
//        Column(Modifier.fillMaxWidth(), horizontalAlignment = Alignment.CenterHorizontally) {
//        Text("Remover Medidor", style = MaterialTheme.typography.titleLarge)
//        Spacer(Modifier.height(16.dp))
//
//        campoTextField("Código del Medidor a Eliminar", codigo, onChange = { codigo = it }, Modifier.fillMaxWidth())
//
//        Spacer(Modifier.height(24.dp))
//
//        OutlinedButton(
//        onClick = {
//        try {
//        val eliminado = repositorioMedidores.eliminar(codigo)
//        mensajeEstado = if (eliminado) {
//        "Medidor $codigo eliminado."
//        } else {
//        "Medidor $codigo no encontrado."
//        }
//        codigo = ""
//        } catch (e: Exception) {
//        mensajeEstado = "Error al eliminar: ${e.message}"
//        }
//        },
//        enabled = codigo.isNotBlank()
//        ) {
//        Text("Eliminar")
//        }
//        Spacer(Modifier.height(16.dp))
//        Text(mensajeEstado)
//        }
//        }
//
//        @Composable
//        private fun paginaListarMedidores(repositorioMedidores: MedidorRepositorio) {
//        var filtro by remember { mutableStateOf("") }
//
//        val listaMedidores by remember(filtro) {
//        mutableStateOf(repositorioMedidores.listar(filtro))
//        }
//
//        Column(modifier = Modifier.fillMaxSize()) {
//        Row(
//        modifier = Modifier.fillMaxWidth(),
//        horizontalArrangement = Arrangement.SpaceBetween,
//        verticalAlignment = Alignment.CenterVertically
//        ) {
//        Text("Listado de Medidores", style = MaterialTheme.typography.titleMedium)
//        campoTextField("Filtro", filtro, onChange = { filtro = it }, Modifier.weight(1f))
//        }
//        Spacer(Modifier.height(16.dp))
//
//        Row(modifier = Modifier.fillMaxWidth().padding(vertical = 4.dp)) {
//        Text("CÓDIGO", modifier = Modifier.weight(2f), style = MaterialTheme.typography.labelSmall)
//        Text("TIPO", modifier = Modifier.weight(2f), style = MaterialTheme.typography.labelSmall)
//        Text("RUT Cliente", modifier = Modifier.weight(3f), style = MaterialTheme.typography.labelSmall)
//        Text("Potencia", modifier = Modifier.weight(2f), style = MaterialTheme.typography.labelSmall)
//        }
//
//        Divider()
//
//        LazyColumn(Modifier.fillMaxSize()) {
//        items(listaMedidores) { m ->
//        Row(modifier = Modifier.fillMaxWidth().padding(vertical = 4.dp)) {
//        Text(m.getCodigo(), modifier = Modifier.weight(2f))
//        Text(m.tipo(), modifier = Modifier.weight(2f))
//        Text(m.getCliente().getRut(), modifier = Modifier.weight(3f))
//        Text("${m.getPotenciaMaxKw()} kW", modifier = Modifier.weight(2f))
//        }
//        Divider(color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.08f))
//        }
//            if (listaMedidores.isEmpty()) {
//                item {
//                Text("No se encontraron medidores.", modifier = Modifier.padding(16.dp))
//                }
//            }
//        }
//    }
//}
