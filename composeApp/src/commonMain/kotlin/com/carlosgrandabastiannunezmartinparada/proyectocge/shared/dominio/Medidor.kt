package com.carlosgrandabastiannunezmartinparada.proyectocge.shared.dominio

abstract class Medidor(
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
    //Agregacion de lecturaConsumo
    private var lecturaConsumo: MutableList<LecturaConsumo> = mutableListOf()
    fun agregarLecturaConsumo(nuevaLecturaConsumo: LecturaConsumo){
        lecturaConsumo.add(nuevaLecturaConsumo)
        println("Se ha agregado una nueva lectura de consumo.")
    }
    fun quitarLecturaConsumo(lectura: LecturaConsumo): Boolean{
        if (lecturaConsumo.remove(lectura)) {
            println("Se ha eliminado una lectura de consumo.")
            return true
        }
        return false
    }
    fun listarLecturaConsumo() {
        println("Se realizara una lista de las lecturas actuales")
        lecturaConsumo.forEach { println(it.toString())}
    }
    //Agregacion de lecturaConsumo
    //Getters y Setters
    fun getCodigo():String = codigo
    fun getDireccionSuministro():String = direccionSuministro
    fun getActivo():Boolean = activo
    fun getCliente(): Cliente = cliente
    fun setCodigo(nuevoCodigo: String) {codigo = nuevoCodigo}
    fun setDireccionSuministro(nuevaDireccion: String) {direccionSuministro = nuevaDireccion}
    fun setActivo(nuevoActivo: Boolean) {activo = nuevoActivo}
    fun setCliente(nuevoCliente: Cliente) {cliente = nuevoCliente}
    //Getters y Setters
    //Funciones
    open fun tipo(): String{
        return ""
    }

    override fun toString(): String {
        return "Medidor(codigo='$codigo', direccionSuministro='$direccionSuministro', activo=$activo, cliente=$cliente, lecturaConsumo=$lecturaConsumo)"
    }

    //Funciones
}