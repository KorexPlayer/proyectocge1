package com.carlosgrandabastiannunezmartinparada.proyectocge.shared.dominio

open class Persona(
    private var rut: String,
    private var nombre: String,
    private var email: String
) {
    //Getters y Setters
    fun getRut(): String = rut
    fun getNombre(): String = nombre
    fun getEmail(): String = email
    fun setRut(nuevoRut: String) {rut = nuevoRut}
    fun setNombre(nuevoNombre: String) {nombre = nuevoNombre}
    fun setEmail(nuevoEmail: String) {email = nuevoEmail}
    //Getters y Setters
}