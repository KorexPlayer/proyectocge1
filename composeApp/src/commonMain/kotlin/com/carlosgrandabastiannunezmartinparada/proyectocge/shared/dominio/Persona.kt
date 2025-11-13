package com.carlosgrandabastiannunezmartinparada.proyectocge.shared.dominio
/**
 * Clase base (abierta) que representa a un individuo con información básica.
 *
 * Proporciona las propiedades fundamentales (RUT, nombre, email) que serán
 * heredadas por otras clases más específicas como [Cliente] u [Operador].
 *
 * @param rut El identificador único de la persona (ej. RUT chileno).
 * @param nombre El nombre completo de la persona.
 * @param email La dirección de correo electrónico de contacto.
 */
open class Persona(
    private var rut: String,
    private var nombre: String,
    private var email: String
) {
    //Getters y Setters
    /** Obtiene el RUT de la persona. */
    fun getRut(): String = rut
    /** Obtiene el nombre de la persona. */
    fun getNombre(): String = nombre
    /** Obtiene el email de la persona. */
    fun getEmail(): String = email

    /** Actualiza el RUT de la persona. */
    fun setRut(nuevoRut: String) {rut = nuevoRut}
    /** Actualiza el nombre de la persona. */
    fun setNombre(nuevoNombre: String) {nombre = nuevoNombre}
    /** Actualiza el email de la persona. */
    fun setEmail(nuevoEmail: String) {email = nuevoEmail}
    //Getters y SetDters
}