package com.carlosgrandabastiannunezmartinparada.proyectocge.shared.dominio
/**
 * Representa a un Operador del sistema.
 *
 * Hereda las propiedades básicas de [Persona] (rut, nombre, email) y añade
 * un perfil específico para definir sus roles o permisos.
 *
 * @param rut El RUT del operador (heredado de [Persona]).
 * @param nombre El nombre del operador (heredado de [Persona]).
 * @param email El email de contacto del operador (heredado de [Persona]).
 * @param perfil Un [String] que define el tipo de perfil del operador.
 */
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
    /** Obtiene el perfil del operador. */
    fun getPerfil(): String = perfil
    /** Actualiza el perfil del operador. */
    fun setPerfil(nuevoPerfil: String) {perfil = nuevoPerfil}
    //Getter y Setter
}