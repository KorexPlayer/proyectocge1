package com.carlosgrandabastiannunezmartinparada.proyectocge

import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import com.carlosgrandabastiannunezmartinparada.proyectocge.persistencia.DriverArchivoDesktop
import com.carlosgrandabastiannunezmartinparada.proyectocge.shared.persistencia.persistenciadatos.BoletaRepoImpl
import com.carlosgrandabastiannunezmartinparada.proyectocge.shared.persistencia.persistenciadatos.ClienteRepoImpl
import com.carlosgrandabastiannunezmartinparada.proyectocge.shared.persistencia.persistenciadatos.LecturaRepoImpl
import com.carlosgrandabastiannunezmartinparada.proyectocge.shared.persistencia.persistenciadatos.MedidorRepoImpl
import com.carlosgrandabastiannunezmartinparada.proyectocge.shared.persistencia.persistenciadatos.PersistenciaDato

fun main() {
    val driver = DriverArchivoDesktop("data")
    val persistencia = PersistenciaDato(driver)

    ClienteRepoImpl.init(persistencia)
    LecturaRepoImpl.init(persistencia)
    MedidorRepoImpl.init(persistencia, ClienteRepoImpl)
    BoletaRepoImpl.init(persistencia, ClienteRepoImpl)

    application {
        Window(
            onCloseRequest = ::exitApplication,
            title = "Proyecto CGE by Carlos Granda, Bastian Nuñez y Martin Parada"
        ) {
            App()
        }
    }
}