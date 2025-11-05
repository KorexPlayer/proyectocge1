package com.carlosgrandabastiannunezmartinparada.proyectocge

class WasmPlatform : Platform {
    override val name: String = "Web with Kotlin/Wasm"
}

actual fun getPlatform(): Platform = WasmPlatform()