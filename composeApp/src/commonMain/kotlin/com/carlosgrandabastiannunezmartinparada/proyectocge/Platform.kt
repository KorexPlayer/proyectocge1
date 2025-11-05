package com.carlosgrandabastiannunezmartinparada.proyectocge

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform