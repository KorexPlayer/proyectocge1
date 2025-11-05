package com.carlosgrandabastiannunezmartinparada.proyectocge.shared.dominio

class Cliente(
    rut: String,
    nombre: String,
    email: String,
    private var direccionFacturacion: String,
    private var estado: EstadoCliente
) : Persona (
    rut = rut,
    nombre = nombre,
    email = email
) {
    //Getters y Setters
    fun getDireccionFacturacion(): String = direccionFacturacion
    fun getEstado(): EstadoCliente = estado
    fun setDireccionFacturacion(nuevaDireccionFacturacion: String) {direccionFacturacion = nuevaDireccionFacturacion}
    fun setEstado(nuevoEstado: EstadoCliente) {estado = nuevoEstado}
    //Getters y Setters
    //Agregacion de Boleta
    private val listadoBoletas: MutableList<Boleta> = mutableListOf()
    fun agregarBoleta(nuevaBoleta: Boleta) {
        listadoBoletas.add(nuevaBoleta)
        println("Se ha agregado una nueva Boleta.")
    }
    fun quitarBoleta(boleta: Boleta): Boolean {
        if(listadoBoletas.remove(boleta)) {
            println("Se ha eliminado una Boleta.")
            return true
        }
        return false
    }
    fun listarBoletas() {
        println("Se realizara una lista de Boletas.")
        listadoBoletas.forEach {println(it.toString())}
    }
    //Agregacion de Boleta
    //Agregacion de Medidor
    private val listadoMedidores: MutableList<Medidor> = mutableListOf()
    fun agregarMedidor(nuevoMedidor: Medidor) {
        listadoMedidores.add(nuevoMedidor)
        println("Se ha agregado un nuevo Medidor.")
    }
    fun quitarBoleta(medidor: Medidor): Boolean {
        if(listadoMedidores.remove(medidor)) {
            println("Se ha eliminado un Medidor.")
            return true
        }
        return false
    }
    fun listarMedidores() {
        println("Se realizara una lista de Medidores.")
        listadoMedidores.forEach {println(it.toString())}
    }

    override fun toString(): String {
        return "Cliente(rut = ${getRut()}, nombre = ${getNombre()}, email = ${getEmail()}, direccionFacturacion='$direccionFacturacion', estado=$estado, listadoBoletas=$listadoBoletas, listadoMedidores=$listadoMedidores)"
    }
    //Agregacion de Medidor


}