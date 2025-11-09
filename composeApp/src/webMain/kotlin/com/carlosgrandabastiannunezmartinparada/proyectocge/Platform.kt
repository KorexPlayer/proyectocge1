package com.carlosgrandabastiannunezmartinparada.proyectocge


class webPlatform: Platform {
    override val name: String = "web"
}
actual fun getPlatform(): Platform = webPlatform()