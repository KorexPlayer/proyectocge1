package com.carlosgrandabastiannunezmartinparada.proyectocge

import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application

fun main() = application {
    Window(
        onCloseRequest = ::exitApplication,
        title = "Proyecto CGE by Carlos Granda, Bastian Nuñez y Martin Parada"
    ) {
        App()
    }
}