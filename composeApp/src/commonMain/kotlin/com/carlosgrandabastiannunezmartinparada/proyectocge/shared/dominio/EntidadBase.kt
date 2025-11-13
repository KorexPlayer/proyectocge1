package com.carlosgrandabastiannunezmartinparada.proyectocge.shared.dominio
/**
 * Clase base (abierta) que proporciona propiedades comunes para otras entidades.
 *
 * Esta clase incluye un identificador único y marcas de tiempo para la creación
 * y la última actualización, utilizando la clase personalizada [Date].
 *
 * @param id El identificador único [String] para la entidad.
 * @param createdAt La fecha ([Date]) en que la entidad fue creada.
 * @param updatedAt La fecha ([Date]) de la última modificación de la entidad.
 */
open class EntidadBase(
    var id: String,
    var createdAt: Date,
    var updatedAt: Date,
) {
}