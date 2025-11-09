package com.carlosgrandabastiannunezmartinparada.proyectocge

import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.window.ComposeViewport
import com.carlosgrandabastiannunezmartinparada.proyectocge.persistencia.DriverArchivoWeb
import com.carlosgrandabastiannunezmartinparada.proyectocge.shared.persistencia.persistenciadatos.BoletaRepoImpl
import com.carlosgrandabastiannunezmartinparada.proyectocge.shared.persistencia.persistenciadatos.ClienteRepoImpl
import com.carlosgrandabastiannunezmartinparada.proyectocge.shared.persistencia.persistenciadatos.LecturaRepoImpl
import com.carlosgrandabastiannunezmartinparada.proyectocge.shared.persistencia.persistenciadatos.MedidorRepoImpl
import com.carlosgrandabastiannunezmartinparada.proyectocge.shared.persistencia.persistenciadatos.PersistenciaDato

@OptIn(ExperimentalComposeUiApi::class)
fun main() {
    val driver = DriverArchivoWeb()
    val persistencia = PersistenciaDato(driver)


    ClienteRepoImpl.init(persistencia)
    LecturaRepoImpl.init(persistencia)
    MedidorRepoImpl.init(persistencia, ClienteRepoImpl)
    BoletaRepoImpl.init(persistencia, ClienteRepoImpl)
    ComposeViewport {
        App()
    }
}