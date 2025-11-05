package com.carlosgrandabastiannunezmartinparada.proyectocge.shared.dominio

class Operador(
    rut: String,
    nombre: String,
    email: String,
    private var perfil: String
) : Persona (
    rut = rut,
    nombre = nombre,
    email = email
){
    //Getter y Setter
    fun getPerfil(): String = perfil
    fun setPerfil(nuevoPerfil: String) {perfil = nuevoPerfil}
    //Getter y Setter
}