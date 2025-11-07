package com.carlosgrandabastiannunezmartinparada.proyectocge.componentes

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import com.carlosgrandabastiannunezmartinparada.proyectocge.desktopApp.paginaPrincipal

private enum class Pantallas { AGREGAR, REMOVER, LISTADO, DEVOLVER}

fun selectorPantallas() {
        var pantalla by remember { mutableStateOf(Pantallas.DEVOLVER) }
        when (pantalla) {
            Pantallas.DEVOLVER -> paginaPrincipal()

        }
}