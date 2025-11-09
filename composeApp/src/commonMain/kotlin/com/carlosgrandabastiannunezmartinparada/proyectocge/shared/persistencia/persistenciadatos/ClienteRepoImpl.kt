package com.carlosgrandabastiannunezmartinparada.proyectocge.shared.persistencia.persistenciadatos

import com.carlosgrandabastiannunezmartinparada.proyectocge.shared.dominio.Cliente
import com.carlosgrandabastiannunezmartinparada.proyectocge.shared.dominio.EstadoCliente
import com.carlosgrandabastiannunezmartinparada.proyectocge.shared.persistencia.repositorios.ClienteRepositorio

object ClienteRepoImpl : ClienteRepositorio {
    val repositorio: MutableList<Cliente> = mutableListOf()
    private var persistencia: PersistenciaDato? = null
    private const val PREFIJO_KEY = "clientes_"
    private const val DELIMITADOR = "::"
    fun init(persistencia: PersistenciaDato) {
        this.persistencia = persistencia
        cargarDatos()
    }
    private fun cargarDatos() {
        persistencia?.list(PREFIJO_KEY)?.forEach { key ->
            val bytes = persistencia?.read(key)
            if (bytes != null) {
                val cliente = textoACliente(bytes.decodeToString())
                if (cliente != null && obtenerPorRut(cliente.getRut()) == null) {
                    repositorio.add(cliente)
                }
            }
        }
    }
    private fun clienteATexto(c: Cliente): String {
        return "${c.getRut()}$DELIMITADOR" +
                "${c.getNombre()}$DELIMITADOR" +
                "${c.getEmail()}$DELIMITADOR" +
                "${c.getDireccionFacturacion()}$DELIMITADOR" +
                "${c.getEstado()}$DELIMITADOR${c.getTipoLugar()}"
    }
    private fun textoACliente(data: String): Cliente? {
        val parts = data.split(DELIMITADOR)
        val rut = parts.getOrNull(0)
        val nombre = parts.getOrNull(1)
        val email = parts.getOrNull(2)
        val direccion = parts.getOrNull(3)
        val estado = parts.getOrNull(4)
        val tipoLugar = parts.getOrNull(5)

        if (rut == null || nombre == null || direccion == null || tipoLugar == null || email == null || estado == null) {
            return null
        }

        return Cliente(rut, nombre, email!!,direccion ,if(estado == "ACTIVO"){EstadoCliente.ACTIVO} else {EstadoCliente.INACTIVO}, tipoLugar)
    }
    override fun crear(c: Cliente): Cliente {
        for (cliente in repositorio) {
            if (cliente.getRut() == c.getRut()) {
                println("Ya existe cliente con ese rut")
                return cliente
            }
        }
        repositorio.add(c)
        println("Cliente creado correctamente")

        persistencia?.let { p ->
            val key = "$PREFIJO_KEY${c.getRut()}"
            val data = clienteATexto(c).encodeToByteArray()
            p.save(key, data)
        }
        return c
    }

    override fun actualizar(c: Cliente): Cliente {
        for (i in repositorio.indices) {
            if (repositorio[i].getRut() == c.getRut()) {
                repositorio[i] = c
                println("Cliente actualizado correctamente")

                persistencia?.let { p ->
                    val key = "$PREFIJO_KEY${c.getRut()}"
                    val data = clienteATexto(c).encodeToByteArray()
                    p.save(key, data)
                }
                return c
            }
        }
        println("No se encontro el cliente")
        return c
    }

    override fun eliminar(rut: String): Boolean {
        for (cliente in repositorio) {
            if (cliente.getRut() == rut) {
                repositorio.remove(cliente)
                println("Se ha eliminado el Cliente del repositorio.")

                persistencia?.let { p ->
                    val key = "$PREFIJO_KEY$rut"
                    p.delete(key)
                }

                return true
            }
        }
        return false
    }

    override fun obtenerPorRut(rut: String): Cliente? {
        for (cliente in repositorio) {
            if (cliente.getRut() == rut) {
                println("Cliente encontrado: ${cliente.getNombre()}")
                return cliente
            }
        }
        println("No se encontro el cliente")
        return null
    }

    override fun listar(filtro: String): List<Cliente> {
        val listaFiltrada: MutableList<Cliente> = mutableListOf()
        val filtroMinusculas = filtro.lowercase()
        for (cliente in repositorio) {
            if (cliente.getRut().lowercase().contains(filtroMinusculas) ||
                cliente.getNombre().lowercase().contains(filtroMinusculas)) {
                listaFiltrada.add(cliente)
            }
        }
        return listaFiltrada
        }
    }