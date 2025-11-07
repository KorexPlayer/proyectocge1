package com.carlosgrandabastiannunezmartinparada.proyectocge.ui

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.carlosgrandabastiannunezmartinparada.proyectocge.shared.dominio.*
import com.carlosgrandabastiannunezmartinparada.proyectocge.shared.persistencia.repositorios.MedidorRepositorio
//import java.util.UUID

@Composable
fun PantallaMedidor(
    medidorRepo: MedidorRepositorio
) {

    var codigo by remember { mutableStateOf("") }
    var rutClienteAsociado by remember { mutableStateOf("") }


    var tipoMedidor by remember { mutableStateOf<String>("Monofasico") }


    var potenciaMaxKw by remember { mutableStateOf("") }

    var mensajeEstado by remember { mutableStateOf("") }

    Column(
        modifier = Modifier.padding(16.dp).fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text("Registro de Medidores", style = MaterialTheme.typography.headlineMedium)
        Spacer(Modifier.height(16.dp))


        OutlinedTextField(value = codigo, onValueChange = { codigo = it }, label = { Text("Código del Medidor") })
        OutlinedTextField(value = rutClienteAsociado, onValueChange = { rutClienteAsociado = it }, label = { Text("RUT del Cliente a Asociar") })
        OutlinedTextField(value = potenciaMaxKw, onValueChange = { potenciaMaxKw = it }, label = { Text("Potencia Máxima (kW)") })

        Spacer(Modifier.height(16.dp))


        Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceEvenly) {

            Button(onClick = { tipoMedidor = "Monofasico" }, enabled = tipoMedidor != "Monofasico") {
                Text("Monofásico")
            }
            // Botón para Trifásico
            Button(onClick = { tipoMedidor = "Trifasico" }, enabled = tipoMedidor != "Trifasico") {
                Text("Trifásico")
            }
        }

        Spacer(Modifier.height(24.dp))

        Button(onClick = {
            try {

                if (codigo.isBlank() || rutClienteAsociado.isBlank()) {
                    mensajeEstado = "Debe completar Código y RUT."
                    return@Button
                }

//                val clienteAsociado = Cliente(
//                    rut = rutClienteAsociado,
//                    nombre = "Cliente Temp",
//                    email = "",
//                    direccionFacturacion = "",
//                    estado = EstadoCliente.ACTIVO
//                )
//
//                val nuevoMedidor: Medidor = if (tipoMedidor == "Monofasico") {
//                    MedidorMonofasico(
//                        id = UUID.randomUUID().toString(),
//                        codigo = codigo,
//                        direccionSuministro = "",
//                        activo = true,
//                        cliente = clienteAsociado,
//                        potenciaMaxKw = potenciaMaxKw.toDoubleOrNull() ?: 0.0
//                    )
//                } else {
//                    MedidorTrifasico(
//                        id = UUID.randomUUID().toString(),
//                        codigo = codigo,
//                        direccionSuministro = "",
//                        activo = true,
//                        cliente = clienteAsociado,
//                        potenciaMaxKw = potenciaMaxKw.toDoubleOrNull() ?: 0.0,
//                        factorPotencia = 1.0
//                    )
  //              }

       //         medidorRepo.crear(nuevoMedidor, rutClienteAsociado)
        //        mensajeEstado = "Medidor ${nuevoMedidor.tipo()} registrado con código $codigo."

            } catch (e: Exception) {
                mensajeEstado = "Error: ${e.message}"
            }
        }, enabled = codigo.isNotBlank()) {
            Text("Registrar Medidor")
        }

        Spacer(Modifier.height(24.dp))
        Text(mensajeEstado)
    }
}