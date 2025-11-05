package com.carlosgrandabastiannunezmartinparada.proyectocge.shared.dominio

class Operador(
    rut: String,
    nombre: String,
    email: String,
    private val perfil: String
) : Persona (
    rut = rut,
    nombre = nombre,
    email = email
){
}