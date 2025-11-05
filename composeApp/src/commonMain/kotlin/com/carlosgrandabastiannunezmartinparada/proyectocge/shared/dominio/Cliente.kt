package com.carlosgrandabastiannunezmartinparada.proyectocge.shared.dominio

class Cliente(
    rut: String,
    nombre: String,
    email: String,
    private var direccionFacturacion: String,
    private var estado: EstadoCliente,
    private var listaBoletas: List<Boleta>,
    private var listaMedidor: List<Medidor>
) : Persona (
    rut = rut,
    nombre = nombre,
    email = email
) {
}