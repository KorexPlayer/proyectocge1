package com.carlosgrandabastiannunezmartinparada.proyectocge.shared.dominio

/**
 * Representa un tipo específico de [Medidor]: un Medidor Trifásico.
 *
 * Hereda todas las propiedades de [Medidor] y añade propiedades específicas
 * de instalaciones trifásicas, como la potencia máxima y el factor de potencia.
 *
 * @param id El identificador único de la entidad (heredado de [Medidor]).
 * @param createdAt La fecha de creación de la entidad (heredado de [Medidor]).
 * @param updatedAt La fecha de última modificación (heredado de [Medidor]).
 * @param codigo El código identificador del dispositivo (heredado de [Medidor]).
 * @param direccionSuministro La dirección de instalación (heredado de [Medidor]).
 * @param activo El estado operativo del medidor (heredado de [Medidor]).
 * @param cliente El [Cliente] asociado (heredado de [Medidor]).
 * @param potenciaMaxKw La potencia máxima (en Kilowatts) que soporta esta instalación trifásica.
 * @param factorPotencia El factor de potencia (usualmente entre 0 y 1) de la instalación.
 */
class MedidorTrifasico(
    id: String,
    createdAt: Date,
    updatedAt: Date,
    codigo: String,
    direccionSuministro: String,
    activo: Boolean,
    cliente: Cliente,
    private var potenciaMaxKw: Double,
    private var factorPotencia: Double
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
    /** Obtiene la potencia máxima soportada en KW. */
    fun getPotenciaMaxKw(): Double = potenciaMaxKw
    /** Actualiza la potencia máxima soportada en KW. */
    fun setPotenciaMaxKw(nuevaPotenciaMaxKw: Double) {potenciaMaxKw = nuevaPotenciaMaxKw}
    /** Obtiene el factor de potencia de la instalación. */
    fun getFactorPotencia(): Double = factorPotencia
    /** Actualiza el factor de potencia de la instalación. */
    fun setFactorPotencia(nuevaFactorPotencia: Double) {factorPotencia = nuevaFactorPotencia}
    // Getters y Setters

    /**
     * Sobrescribe la función [tipo] de [Medidor] para identificar esta
     * instancia como un medidor trifásico.
     *
     * @return El [String] "Medidor Trifasico".
     */
    override fun tipo(): String {
        return "Medidor Trifasico"
    }
}