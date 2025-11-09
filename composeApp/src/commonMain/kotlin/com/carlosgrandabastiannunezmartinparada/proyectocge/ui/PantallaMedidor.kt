package com.carlosgrandabastiannunezmartinparada.proyectocge.ui

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.carlosgrandabastiannunezmartinparada.proyectocge.componentes.campoTextField
import com.carlosgrandabastiannunezmartinparada.proyectocge.shared.dominio.*
import com.carlosgrandabastiannunezmartinparada.proyectocge.shared.persistencia.persistenciadatos.MedidorRepoImpl
import com.carlosgrandabastiannunezmartinparada.proyectocge.shared.persistencia.repositorios.MedidorRepositorio

@Composable
fun PantallaMedidor(repositorioMedidores: MedidorRepoImpl) {
    var selected by remember { mutableStateOf(0) }
    val options = listOf("Agregar", "Remover", "Mostrar")

    var mensaje by remember { mutableStateOf("") }

    Column(modifier = Modifier.fillMaxSize()) {
        Text(
            "Manejo de Medidores",
            style = MaterialTheme.typography.titleLarge,
            modifier = Modifier.fillMaxWidth(),
            textAlign = TextAlign.Center
        )

        Spacer(modifier = Modifier.size(2.dp))

        Box(modifier = Modifier.fillMaxWidth(), contentAlignment = Alignment.Center) {
            SingleChoiceSegmentedButtonRow {
                options.forEachIndexed { index, option ->
                    SegmentedButton(
                        shape = SegmentedButtonDefaults.itemShape(
                            index = index,
                            count = options.size
                        ),
                        onClick = { selected = index; mensaje = "" },
                        selected = index == selected,
                        label = { Text(option) }
                    )
                }
            }
        }

        Spacer(modifier = Modifier.size(5.dp))

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 16.dp)
        ) {
            when (selected) {
                0 -> {
                    paginaAgregarMedidor(repositorioMedidores, { mensaje = it })
                }
                1 -> {
                    paginaRemoverMedidor(repositorioMedidores, { mensaje = it })
                }
                2 -> {
                    paginaListarMedidores(repositorioMedidores)
                }
            }

            if (mensaje.isNotEmpty()) {
                Spacer(Modifier.height(16.dp))
                Text(
                    mensaje,
                    color = if (mensaje.startsWith("Error")) MaterialTheme.colorScheme.error else MaterialTheme.colorScheme.primary
                )
            }
        }
    }
}

@Composable
private fun paginaAgregarMedidor(
    repositorioMedidores: MedidorRepositorio,
    onMensajeChange: (String) -> Unit
) {
    var tipoMedidor by remember { mutableStateOf("Monofásico") }
    var id by remember { mutableStateOf("") }
    var createdAtAnio by remember { mutableStateOf("") }
    var createdAtMes by remember { mutableStateOf("") }
    var createdAtDia by remember { mutableStateOf("") }
    var updatedAtAnio by remember { mutableStateOf("") }
    var updatedAtMes by remember { mutableStateOf("") }
    var updatedAtDia by remember { mutableStateOf("") }

    var codigo by remember { mutableStateOf("") }
    var direccion by remember { mutableStateOf("") }
    var rutCliente by remember { mutableStateOf("") }
    var potenciaMaxKw by remember { mutableStateOf("") }
    var factorPotencia by remember { mutableStateOf("") }
    var estaActivo by remember { mutableStateOf(true) }

    val fieldWidth = 800.dp
    val defaultSpacer = 8.dp
    val buttonSpacer = 16.dp
    val compactSpacer = 4.dp

    val scrollState = rememberScrollState()

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.fillMaxWidth()
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .fillMaxWidth()
                .verticalScroll(scrollState)
        ) {
            Text(
                "Agregar Medidor",
                modifier = Modifier.fillMaxWidth(),
                style = MaterialTheme.typography.titleLarge,
                textAlign = TextAlign.Center
            )

            Spacer(Modifier.height(compactSpacer))

            campoTextField("ID Medidor", id, onChange = { id = it }, modifier1 = Modifier.width(fieldWidth))
            Spacer(Modifier.height(compactSpacer))

            Box {
                Row (modifier = Modifier.width(fieldWidth),
                    horizontalArrangement = Arrangement.spacedBy(4.dp),
                    verticalAlignment = Alignment.CenterVertically) {
                    campoTextField("Año Creado", createdAtAnio, onChange = {createdAtAnio = it}, modifier1 = Modifier.weight(1f).fillMaxWidth())
                    campoTextField("Mes Creado", createdAtMes, onChange = {createdAtMes = it}, modifier1 = Modifier.weight(1f).fillMaxWidth())
                    campoTextField("Dia Creado", createdAtDia, onChange = {createdAtDia = it}, modifier1 = Modifier.weight(1f).fillMaxWidth())
                }
            }
            Spacer(Modifier.height(compactSpacer))
            Row (modifier = Modifier.width(fieldWidth),
                horizontalArrangement = Arrangement.spacedBy(4.dp),
                verticalAlignment = Alignment.CenterVertically) {
                campoTextField("Año Actualizado", updatedAtAnio, onChange = {updatedAtAnio = it}, modifier1 = Modifier.weight(1f).fillMaxWidth())
                campoTextField("Mes Actualizado", updatedAtMes, onChange = {updatedAtMes = it}, modifier1 = Modifier.weight(1f).fillMaxWidth())
                campoTextField("Dia Actualizado", updatedAtDia, onChange = {updatedAtDia = it}, modifier1 = Modifier.weight(1f).fillMaxWidth())
            }
            Spacer(Modifier.height(compactSpacer))

            campoTextField("Código", codigo, onChange = { codigo = it }, modifier1 = Modifier.width(fieldWidth))
            Spacer(Modifier.height(compactSpacer))
            campoTextField("RUT Cliente", rutCliente, onChange = { rutCliente = it }, modifier1 = Modifier.width(fieldWidth))
            Spacer(Modifier.height(compactSpacer))
            campoTextField("Dirección Suministro", direccion, onChange = { direccion = it }, modifier1 = Modifier.width(fieldWidth))
            Spacer(Modifier.height(compactSpacer))
            campoTextField("Potencia Máxima (kW)", potenciaMaxKw, onChange = { potenciaMaxKw = it }, modifier1 = Modifier.width(fieldWidth))

            Spacer(Modifier.height(defaultSpacer))

            Row(
                modifier = Modifier.width(fieldWidth),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text("Medidor Activo:")
                    Spacer(Modifier.width(8.dp))
                    Text(if (estaActivo) "SÍ" else "NO")
                    Spacer(Modifier.width(8.dp))
                    Switch(
                        checked = estaActivo,
                        onCheckedChange = { estaActivo = it }
                    )
                }

                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text("Tipo de Medidor:")
                    Spacer(Modifier.width(8.dp))
                    RadioButton(selected = tipoMedidor == "Monofásico", onClick = { tipoMedidor = "Monofásico" })
                    Text("Monofásico")
                    Spacer(Modifier.width(8.dp))
                    RadioButton(selected = tipoMedidor == "Trifásico", onClick = { tipoMedidor = "Trifásico" })
                    Text("Trifásico")
                }
            }

            Spacer(Modifier.height(defaultSpacer))

            if (tipoMedidor == "Trifásico") {
                campoTextField("Factor Potencia", factorPotencia, onChange = { factorPotencia = it }, modifier1 = Modifier.width(fieldWidth))
            }

            Spacer(Modifier.height(buttonSpacer))

            Row {
                Button(
                    onClick = {
                        try {
                            val cliente = Cliente(
                                rut = rutCliente,
                                nombre = "",
                                email = "",
                                direccionFacturacion = "",
                                estado = EstadoCliente.ACTIVO,
                                tipoLugar = ""
                            )

                            val createdDate = Date(createdAtAnio.toInt(), createdAtMes.toInt(), createdAtDia.toInt())
                            val updatedDate = Date(updatedAtAnio.toInt(), updatedAtMes.toInt(), updatedAtDia.toInt())

                            val nuevoMedidor = if (tipoMedidor == "Monofásico") {
                                MedidorMonofasico(
                                    id = id,
                                    createdAt = createdDate,
                                    updatedAt = updatedDate,
                                    codigo = codigo,
                                    direccionSuministro = direccion,
                                    activo = estaActivo,
                                    cliente = cliente,
                                    potenciaMaxKw = potenciaMaxKw.toDouble()
                                )
                            } else {
                                MedidorTrifasico(
                                    id = id,
                                    createdAt = createdDate,
                                    updatedAt = updatedDate,
                                    codigo = codigo,
                                    direccionSuministro = direccion,
                                    activo = estaActivo,
                                    cliente = cliente,
                                    potenciaMaxKw = potenciaMaxKw.toDouble(),
                                    factorPotencia = factorPotencia.toDouble()
                                )
                            }

                            repositorioMedidores.crear(nuevoMedidor, rutCliente)

                            id = ""
                            createdAtAnio = ""
                            createdAtMes = ""
                            createdAtDia = ""
                            updatedAtAnio = ""
                            updatedAtMes = ""
                            updatedAtDia = ""
                            codigo = ""
                            direccion = ""
                            rutCliente = ""
                            potenciaMaxKw = ""
                            factorPotencia = ""
                            estaActivo = true

                            onMensajeChange("Medidor agregado correctamente")
                        } catch (e: Exception) {
                            onMensajeChange("Error: ${e.message}")
                        }
                    },
                    modifier = Modifier.height(48.dp)
                ) {
                    Text("Guardar Medidor", style = MaterialTheme.typography.titleLarge)
                }
            }
            Spacer(Modifier.height(defaultSpacer))
        }
    }
}

@Composable
private fun paginaRemoverMedidor(
    repositorioMedidores: MedidorRepositorio,
    onMensajeChange: (String) -> Unit
) {
    var codigo by remember { mutableStateOf("") }

    Text(
        "Remover Medidor",
        style = MaterialTheme.typography.titleLarge,
        modifier = Modifier.fillMaxWidth(),
        textAlign = TextAlign.Center
    )

    Column(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        campoTextField("Código a Eliminar", codigo, onChange = { codigo = it }, Modifier.width(800.dp))

        Row {
            Button(onClick = {

                val eliminado = repositorioMedidores.eliminar(codigo)
                if (eliminado) {
                    onMensajeChange("Medidor eliminado correctamente")
                } else {
                    onMensajeChange("No se encontró el medidor con código: $codigo")
                }
                codigo = ""
            }) {
                Text("Eliminar")
            }
        }
    }
}

@Composable
private fun paginaListarMedidores(
    repositorioMedidores: MedidorRepositorio
) {
    var filtro by remember { mutableStateOf("") }

    var filtroColumna by remember { mutableStateOf("RUT") }

    val medidoresFiltrados: List<Medidor> = if (filtro.isBlank()) {
        emptyList()
    } else {
        when (filtroColumna) {
            "Código" -> {
                val medidorEncontrado = repositorioMedidores.obtenerPorCodigo(filtro)
                if (medidorEncontrado != null) listOf(medidorEncontrado) else emptyList()
            }
            "RUT" -> {
                repositorioMedidores.listarPorCliente(filtro)
            }
            else -> emptyList()
        }
    }

    Text(
        "Listado de Medidores",
        style = MaterialTheme.typography.titleLarge,
        modifier = Modifier.fillMaxWidth(),
        textAlign = TextAlign.Center
    )

    val rowWidthModifier = Modifier.fillMaxWidth()

    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Column(modifier = Modifier.width(800.dp)) {

            Row(verticalAlignment = Alignment.CenterVertically) {
                Text("Filtrar por:")
                Spacer(Modifier.width(16.dp))

                RadioButton(selected = filtroColumna == "RUT", onClick = { filtroColumna = "RUT" })
                Text("RUT Cliente")
                Spacer(Modifier.width(16.dp))

                RadioButton(selected = filtroColumna == "Código", onClick = { filtroColumna = "Código" })
                Text("Código Medidor")
            }

            Spacer(Modifier.height(8.dp))

            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                val etiquetaFiltro = "Buscar por ${filtroColumna}"
                campoTextField(etiquetaFiltro, filtro, onChange = { filtro = it }, Modifier.weight(1f))
            }
        }

        Spacer(Modifier.height(16.dp))

        Row(modifier = rowWidthModifier.padding(horizontal = 16.dp)){
            Text("ID", modifier = Modifier.weight(1.2f), style = MaterialTheme.typography.titleSmall, textAlign = TextAlign.Start)
            Text("Código", modifier = Modifier.weight(1.5f), style = MaterialTheme.typography.titleSmall, textAlign = TextAlign.Start)
            Text("RUT Cliente", modifier = Modifier.weight(1.5f), style = MaterialTheme.typography.titleSmall, textAlign = TextAlign.Start)
            Text("Dirección", modifier = Modifier.weight(2.5f), style = MaterialTheme.typography.titleSmall, textAlign = TextAlign.Start)
            Text("Creado", modifier = Modifier.weight(1.5f), style = MaterialTheme.typography.titleSmall, textAlign = TextAlign.Start)
            Text("Act.", modifier = Modifier.weight(1.5f), style = MaterialTheme.typography.titleSmall, textAlign = TextAlign.Start)
            Text("Activo", modifier = Modifier.weight(0.8f), style = MaterialTheme.typography.titleSmall, textAlign = TextAlign.Start)
            Text("Tipo", modifier = Modifier.weight(1.2f), style = MaterialTheme.typography.titleSmall, textAlign = TextAlign.Start)
            Text("Potencia", modifier = Modifier.weight(1.4f), style = MaterialTheme.typography.titleSmall, textAlign = TextAlign.Start)
            Text("F. Pot.", modifier = Modifier.weight(1.4f), style = MaterialTheme.typography.titleSmall, textAlign = TextAlign.Start)
        }
        HorizontalDivider(modifier = Modifier.padding(horizontal = 16.dp))
        Spacer(Modifier.height(4.dp))

        LazyColumn(modifier = Modifier.fillMaxWidth().weight(1f).padding(horizontal = 16.dp)) {
            items(medidoresFiltrados) { m ->

                val potencia = when(m) {
                    is MedidorMonofasico -> "${m.getPotenciaMaxKw()} kW"
                    is MedidorTrifasico -> "${m.getPotenciaMaxKw()} kW"
                    else -> "N/A"
                }


                val factorPotencia = when (m) {
                    is MedidorTrifasico -> m.getFactorPotencia().toString()
                    is MedidorMonofasico -> ""
                    else -> "N/A"
                }

                val createdDateStr = m.createdAt.toString()
                val updatedDateStr = m.updatedAt.toString()

                Row(modifier = Modifier.fillMaxWidth()) {
                    Text(m.id, modifier = Modifier.weight(1.2f), textAlign = TextAlign.Start)
                    Text(m.getCodigo(), modifier = Modifier.weight(1.5f), textAlign = TextAlign.Start)
                    Text(m.getCliente().getRut(), modifier = Modifier.weight(1.5f), textAlign = TextAlign.Start)
                    Text(m.getDireccionSuministro(), modifier = Modifier.weight(2.5f), textAlign = TextAlign.Start)
                    Text(createdDateStr, modifier = Modifier.weight(1.5f), textAlign = TextAlign.Start)
                    Text(updatedDateStr, modifier = Modifier.weight(1.5f), textAlign = TextAlign.Start)
                    Text(if (m.getActivo()) "SÍ" else "NO", modifier = Modifier.weight(0.8f), textAlign = TextAlign.Start)
                    Text(m.tipo(), modifier = Modifier.weight(1.2f), textAlign = TextAlign.Start)
                    Text(potencia, modifier = Modifier.weight(1.4f), textAlign = TextAlign.Start)
                    Text(factorPotencia, modifier = Modifier.weight(1.4f), textAlign = TextAlign.Start)
                }
                HorizontalDivider()
            }
        }
    }
}