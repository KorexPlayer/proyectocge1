package com.carlosgrandabastiannunezmartinparada.proyectocge.shared.dominio
/**
 * Representa un tipo específico de [Medidor]: un Medidor Monofásico.
 *
 * Hereda todas las propiedades y comportamientos de [Medidor] y añade
 * la especificación de la potencia máxima soportada.
 *
 * @param id El identificador único de la entidad (heredado de [Medidor]).
 * @param createdAt La fecha de creación de la entidad (heredado de [Medidor]).
 * @param updatedAt La fecha de última modificación (heredado de [Medidor]).
 * @param codigo El código identificador del dispositivo (heredado de [Medidor]).
 * @param direccionSuministro La dirección de instalación (heredado de [Medidor]).
 * @param activo El estado operativo del medidor (heredado de [Medidor]).
 * @param cliente El [Cliente] asociado (heredado de [Medidor]).
 * @param potenciaMaxKw La potencia máxima (en Kilowatts) que fue registrada en esta instalación monofásica.
 */
class MedidorMonofasico(
    id: String,
    createdAt: Date,
    updatedAt: Date,
    codigo: String,
    direccionSuministro: String,
    activo: Boolean,
    cliente: Cliente,
    private var potenciaMaxKw: Double
) : Medidor(
    id = id,
    createdAt = createdAt,
    updatedAt = updatedAt,
    codigo = codigo,
    direccionSuministro = direccionSuministro,
    activo = activo,
    cliente = cliente,
){
    // Getters y Setters
    /** Obtiene la potencia máxima registrada en KW. */
    fun getPotenciaMaxKw(): Double = potenciaMaxKw
    /** Actualiza la potencia máxima soportada en KW. */
    fun setPotenciaMaxKw(nuevaPotenciaMaxKw: Double) {potenciaMaxKw = nuevaPotenciaMaxKw}
    // Getters y Setters

    /**
     * Sobrescribe la función [tipo] de [Medidor] para identificar esta
     * instancia como un medidor monofásico.
     *
     * @return El [String] "Medidor Monofasico".
     */
    override fun tipo(): String {
        return "Medidor Monofasico"
    }
}