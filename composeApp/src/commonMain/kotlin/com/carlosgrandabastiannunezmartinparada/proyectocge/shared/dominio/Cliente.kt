package com.carlosgrandabastiannunezmartinparada.proyectocge.shared.dominio

/**
 * Representa a un Cliente, que hereda las propiedades básicas de [Persona].
 *
 * Esta clase gestiona la información específica del cliente (dirección, estado)
 * y mantiene las listas de [Boleta] (agregación) y [Medidor] (agregación)
 * asociadas a él.
 *
 * @param rut El RUT del cliente (heredado de [Persona]).
 * @param nombre El nombre del cliente (heredado de [Persona]).
 * @param email El email de contacto del cliente (heredado de [Persona]).
 * @param direccionFacturacion La dirección postal donde se envían las facturas.
 * @param estado El estado actual del cliente (ej. ACTIVO, INACTIVO) usando [EstadoCliente].
 * @param tipoLugar El tipo de propiedad asociada (ej. RESIDENCIAL, COMERCIAL).
 */
class Cliente(
    rut: String,
    nombre: String,
    email: String,
    private var direccionFacturacion: String,
    private var estado: EstadoCliente,
    private var tipoLugar: String
) : Persona (
    rut = rut,
    nombre = nombre,
    email = email
) {
    //Getters y Setters
    /** Obtiene la dirección de facturación actual. */
    fun getDireccionFacturacion(): String = direccionFacturacion
    /** Obtiene el estado actual del cliente ([EstadoCliente]). */
    fun getEstado(): EstadoCliente = estado
    /** Obtiene el tipo de lugar (ej. RESIDENCIAL). */
    fun getTipoLugar(): String = tipoLugar

    /** Actualiza la dirección de facturación. */
    fun setDireccionFacturacion(nuevaDireccionFacturacion: String) {direccionFacturacion = nuevaDireccionFacturacion}
    /** Actualiza el estado del cliente. */
    fun setEstado(nuevoEstado: EstadoCliente) {estado = nuevoEstado}
    /** Actualiza el tipo de lugar. */
    fun setTipoLugar(nuevolugar: String) {tipoLugar = nuevolugar}
    //Getters y Setters

    //Agregacion de Boleta
    /** Lista interna que almacena las boletas asociadas a este cliente. */
    private val listadoBoletas: MutableList<Boleta> = mutableListOf()
    /**
     * Añade una [Boleta] a la lista del cliente.
     * @param nuevaBoleta La instancia de [Boleta] a agregar.
     */
    fun agregarBoleta(nuevaBoleta: Boleta) {
        listadoBoletas.add(nuevaBoleta)
        println("Se ha agregado una nueva Boleta.")
    }
    /**
     * Intenta eliminar una [Boleta] específica de la lista del cliente.
     * @param boleta La instancia de [Boleta] a eliminar.
     * @return `true` si la boleta fue encontrada y eliminada, `false` en caso contrario.
     */
    fun quitarBoleta(boleta: Boleta): Boolean {
        if(listadoBoletas.remove(boleta)) {
            println("Se ha eliminado una Boleta.")
            return true
        }
        return false
    }
    /**
     * Imprime en consola (stdout) el [toString] de cada [Boleta] asociada al cliente.
     */
    fun listarBoletas() {
        println("Se realizara una lista de Boletas.")
        listadoBoletas.forEach {println(it.toString())}
    }
    /**
     * Devuelve una vista de solo lectura de la lista de [Boleta] del cliente.
     * @return [List]<[Boleta]>
     */
    fun listadeBoletas(): List<Boleta> {
        return listadoBoletas
    }
    /**
     * Obtiene la última [Boleta] agregada a la lista.
     * @return La última [Boleta] o `null` si la lista está vacía.
     */
    fun ultimaBoleta(): Boleta? {
        return listadoBoletas.lastOrNull()
    }
    //Agregacion de Boleta

    //Agregacion de Medidor
    /** Lista interna que almacena los medidores asociados a este cliente. */
    private val listadoMedidores: MutableList<Medidor> = mutableListOf()
    /**
     * Añade un [Medidor] a la lista del cliente.
     * @param nuevoMedidor La instancia de [Medidor] a agregar.
     */
    fun agregarMedidor(nuevoMedidor: Medidor) {
        listadoMedidores.add(nuevoMedidor)
        println("Se ha agregado un nuevo Medidor.")
    }
    /**
     * Intenta eliminar un [Medidor] específico de la lista del cliente.
     * @param medidor La instancia de [Medidor] a eliminar.
     * @return `true` si el medidor fue encontrado y eliminado, `false` en caso contrario.
     */
    fun quitarMedidor(medidor: Medidor): Boolean {
        if(listadoMedidores.remove(medidor)) {
            println("Se ha eliminado un Medidor.")
            return true
        }
        return false
    }
    /**
     * Imprime en consola (stdout) el [toString] de cada [Medidor] asociado al cliente.
     */
    fun listarMedidores() {
        println("Se realizara una lista de Medidores.")
        listadoMedidores.forEach {println(it.toString())}
    }
    /**
     * Obtiene el último [Medidor] agregado a la lista.
     * @return El último [Medidor] o `null` si la lista está vacía.
     */
    fun devolverMedidor(): Medidor? {
        val medidore = listadoMedidores.lastOrNull()
        return medidore

    }
    //Agregacion de Medidor

    /**
     * Devuelve una representación en [String] de la instancia [Cliente].
     * Incluye datos básicos y las listas de boletas y medidores.
     *
     * @return Un [String] con los valores de las propiedades del cliente.
     */
    override fun toString(): String {
        return "Cliente(rut = ${getRut()}, nombre = ${getNombre()}, email = ${getEmail()}, direccionFacturacion='$direccionFacturacion', estado=$estado, listadoBoletas=$listadoBoletas, listadoMedidores=$listadoMedidores)"
    }



}