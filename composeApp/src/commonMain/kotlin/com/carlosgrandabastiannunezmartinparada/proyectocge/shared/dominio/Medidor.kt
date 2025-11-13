package com.carlosgrandabastiannunezmartinparada.proyectocge.shared.dominio
/**
 * Clase base (abierta) que representa un medidor de consumo eléctrico.
 *
 * Contiene la información del dispositivo (código, dirección), su estado (activo/inactivo)
 * y el [Cliente] al que está asociado. También gestiona una lista de [LecturaConsumo]
 * (agregación) para registrar el historial de consumo.
 *
 * Hereda de [EntidadBase] (ID y Fechas).
 *
 * @param id El identificador único de la entidad (heredado).
 * @param createdAt La fecha de creación de la entidad (heredado).
 * @param updatedAt La fecha de última modificación de la entidad (heredado).
 * @param codigo Un código identificador único para el dispositivo medidor (ej. número de serie).
 * @param direccionSuministro La dirección física donde está instalado el medidor y se provee el servicio.
 * @param activo Estado booleano que indica si el medidor está actualmente en operación (`true`) o no (`false`).
 * @param cliente La instancia del [Cliente] propietario o asociado a este medidor.
 */
open class Medidor(
    id: String,
    createdAt: Date,
    updatedAt: Date,
    private var codigo: String,
    private var direccionSuministro: String,
    private var activo: Boolean,
    private var cliente: Cliente,
) : EntidadBase(
    id = id,
    createdAt = createdAt,
    updatedAt = updatedAt
){
    //Agregación de lecturaConsumo
    /** Lista interna que almacena el historial de lecturas de este medidor. */
    private var lecturaConsumo: MutableList<LecturaConsumo> = mutableListOf()

    /**
     * Añade una [LecturaConsumo] a la lista del medidor.
     * @param nuevaLecturaConsumo La instancia [LecturaConsumo] a agregar.
     */
    fun agregarLecturaConsumo(nuevaLecturaConsumo: LecturaConsumo){
        lecturaConsumo.add(nuevaLecturaConsumo)
        println("Se ha agregado una nueva lectura de consumo.")
    }

    /**
     * Intenta eliminar una [LecturaConsumo] específica de la lista.
     * @param lectura La instancia de [LecturaConsumo] a eliminar.
     * @return `true` si la lectura fue encontrada y eliminada, `false` en caso contrario.
     */
    fun quitarLecturaConsumo(lectura: LecturaConsumo): Boolean{
        if (lecturaConsumo.remove(lectura)) {
            println("Se ha eliminado una lectura de consumo.")
            return true
        }
        return false
    }

    /**
     * Imprime en consola (stdout) el [toString] de cada [LecturaConsumo] asociada al medidor.
     */
    fun listarLecturaConsumo() {
        println("Se realizara una lista de las lecturas actuales")
        lecturaConsumo.forEach { println(it.toString())}
    }

    /**
     * Obtiene la última [LecturaConsumo] registrada en la lista.
     * Nota: Esto lanzará [NoSuchElementException] si la lista está vacía.
     * @return La última [LecturaConsumo].
     */
    fun ultimaLecturaConsumo(): LecturaConsumo {
        return lecturaConsumo.last()
    }
    //Agregacion de lecturaConsumo

    //Getters y Setters
    /** Obtiene el código identificador del medidor. */
    fun getCodigo():String = codigo
    /** Obtiene la dirección de suministro donde está instalado. */
    fun getDireccionSuministro():String = direccionSuministro
    /** Verifica si el medidor está activo. */
    fun getActivo():Boolean = activo
    /** Obtiene la instancia del [Cliente] asociado. */
    fun getCliente(): Cliente = cliente

    /** Actualiza el código identificador del medidor. */
    fun setCodigo(nuevoCodigo: String) {codigo = nuevoCodigo}
    /** Actualiza la dirección de suministro. */
    fun setDireccionSuministro(nuevaDireccion: String) {direccionSuministro = nuevaDireccion}
    /** Actualiza el estado (activo/inactivo) del medidor. */
    fun setActivo(nuevoActivo: Boolean) {activo = nuevoActivo}
    /** Actualiza el [Cliente] asociado a este medidor. */
    fun setCliente(nuevoCliente: Cliente) {cliente = nuevoCliente}
    //Getters y Setters

    //Funciones
    /**
     * Función (abierta) destinada a ser sobrescrita por clases hijas
     * para especificar el tipo de medidor (ej. "Monofásico", "Trifásico").
     *
     * @return Un [String] vacío en la clase base.
     */
    open fun tipo(): String{
        return ""
    }

    /**
     * Devuelve una representación en [String] de la instancia [Medidor].
     *
     * @return Un [String] con los valores de las propiedades del medidor.
     */
    override fun toString(): String {
        return "Medidor(codigo='$codigo', direccionSuministro='$direccionSuministro', activo=$activo, cliente=$cliente, lecturaConsumo=$lecturaConsumo)"
    }

    //Funciones
}