package com.carlosgrandabastiannunezmartinparada.proyectocge.shared.dominio

class Date(
    private var anio: Int,
    private var mes: Int,
    private var dia: Int,
) {
    //Getters y Setters
    fun anioGet(): Int = anio
    fun mesGet(): Int = mes
    fun diaGet(): Int = dia
    fun anioSet(nuevoAnio: Int) { anio = nuevoAnio }
    fun mesSet(nuevoMes: Int) { mes = nuevoMes }
    fun diaSet(nuevoDia: Int) { dia = nuevoDia }
    //Getters y Setters
    //Clase implementada para guardar datos de anio mes y dia para los atributos createdAt y updatedAt
}