package com.carlosgrandabastiannunezmartinparada.proyectocge.shared.persistencia.persistenciadatos

import com.carlosgrandabastiannunezmartinparada.proyectocge.shared.dominio.Date
import com.carlosgrandabastiannunezmartinparada.proyectocge.shared.dominio.Medidor
import com.carlosgrandabastiannunezmartinparada.proyectocge.shared.dominio.MedidorMonofasico
import com.carlosgrandabastiannunezmartinparada.proyectocge.shared.dominio.MedidorTrifasico
import com.carlosgrandabastiannunezmartinparada.proyectocge.shared.persistencia.repositorios.ClienteRepositorio
import com.carlosgrandabastiannunezmartinparada.proyectocge.shared.persistencia.repositorios.MedidorRepositorio

object MedidorRepoImpl : MedidorRepositorio {

    private val repositorio: MutableList<Medidor> = mutableListOf()
    private var persistencia: PersistenciaDato? = null
    private var clienteRepo: ClienteRepositorio? = null
    private const val PREFIJO_KEY = "medidores_"
    private const val DELIMITADOR = "::"

    fun init(persistencia: PersistenciaDato, clienteRepo: ClienteRepositorio) {
        this.persistencia = persistencia
        this.clienteRepo = clienteRepo
        cargarDatos()
    }

    private fun cargarDatos() {
        persistencia?.list(PREFIJO_KEY)?.forEach { key ->
            val bytes = persistencia?.read(key)
            if (bytes != null) {
                val medidor = textoAMedidor(bytes.decodeToString())
                if (medidor != null && obtenerPorCodigo(medidor.getCodigo()) == null) {
                    repositorio.add(medidor)
                }
            }
        }
    }

    private fun medidorATexto(m: Medidor): String {
        val mono = ""
        if(m is MedidorMonofasico) {
            val mono: String = m.id +
                    "$DELIMITADOR${m.createdAt.diaGet()}" +
                    "$DELIMITADOR${m.createdAt.mesGet()}" +
                    "$DELIMITADOR${m.createdAt.anioGet()}" +
                    "$DELIMITADOR${m.updatedAt.diaGet()}" +
                    "$DELIMITADOR${m.updatedAt.mesGet()}" +
                    "$DELIMITADOR${m.updatedAt.anioGet()}" +
                    "$DELIMITADOR${m.getCodigo()}" +
                    "$DELIMITADOR${m.getDireccionSuministro()}" +
                    "$DELIMITADOR${m.getActivo()}" +
                    "$DELIMITADOR${m.getCliente().getRut()}" +
                    "$DELIMITADOR${m.getPotenciaMaxKw()}"
            return mono
        } else if(m is MedidorTrifasico){
            val mono: String = m.id +
                    "$DELIMITADOR${m.createdAt.diaGet()}" +
                    "$DELIMITADOR${m.createdAt.mesGet()}" +
                    "$DELIMITADOR${m.createdAt.anioGet()}" +
                    "$DELIMITADOR${m.updatedAt.diaGet()}" +
                    "$DELIMITADOR${m.updatedAt.mesGet()}" +
                    "$DELIMITADOR${m.updatedAt.anioGet()}" +
                    "$DELIMITADOR${m.getCodigo()}" +
                    "$DELIMITADOR${m.getDireccionSuministro()}" +
                    "$DELIMITADOR${m.getActivo()}" +
                    "$DELIMITADOR${m.getCliente().getRut()}" +
                    "$DELIMITADOR${m.getPotenciaMaxKw()}" +
                    "$DELIMITADOR${m.getFactorPotencia()}"
            return mono
        }
        return mono
    }

    private fun textoAMedidor(data: String): Medidor? {
        val parts = data.split(DELIMITADOR)
        val id = ""
        val creadoDia = ""
        val creadoMes = ""
        val creadoAnio = ""
        val updatedDia = ""
        val updatedMes = ""
        val updatedAnio = ""
        val codigo = ""
        val direccionSuministro = ""
        val activo = ""
        val rutCliente = ""
        val potenciaMaxKw = ""
        val factorPotencia = ""
            if (parts.size == 13) {
            val id = parts.getOrNull(0)
            val creadoDia = parts.getOrNull(1)
            val creadoMes = parts.getOrNull(2)
            val creadoAnio = parts.getOrNull(3)
            val updatedDia = parts.getOrNull(4)
            val updatedMes = parts.getOrNull(5)
            val updatedAnio = parts.getOrNull(6)
            val codigo = parts.getOrNull(7)
            val direccionSuministro = parts.getOrNull(8)
            val activo = parts.getOrNull(9)
            val rutCliente = parts.getOrNull(10)
            val potenciaMaxKw = parts.getOrNull(11)
            val factorPotencia = parts.getOrNull(12)
                if (codigo == null || rutCliente == null || id == null || creadoDia == null || creadoMes == null || creadoAnio == null ||updatedDia == null ||
                    updatedMes == null || updatedAnio == null || direccionSuministro == null || activo == null || factorPotencia == null || potenciaMaxKw == null) {
                    return null
                }

                val cliente = clienteRepo?.obtenerPorRut(rutCliente)
                if (cliente == null) {
                    println("No se encontró cliente $rutCliente para medidor $codigo")
                    return null
                }
                return MedidorTrifasico(
                    id,
                    Date(creadoAnio.toInt(), creadoMes.toInt(), creadoDia.toInt()),
                    Date(updatedAnio.toInt(), updatedMes.toInt(), updatedDia.toInt()),
                    codigo,
                    direccionSuministro,
                    activo.toBoolean(),
                    cliente,
                    potenciaMaxKw.toDouble(),
                    factorPotencia.toDouble(),
                )
        }
        else {
            val id = parts.getOrNull(0)
            val creadoDia = parts.getOrNull(1)
            val creadoMes = parts.getOrNull(2)
            val creadoAnio = parts.getOrNull(3)
            val updatedDia = parts.getOrNull(4)
            val updatedMes = parts.getOrNull(5)
            val updatedAnio = parts.getOrNull(6)
            val codigo = parts.getOrNull(7)
            val direccionSuministro = parts.getOrNull(8)
            val activo = parts.getOrNull(9)
            val rutCliente = parts.getOrNull(10)
            val potenciaMaxKw = parts.getOrNull(11)
                if (codigo == null || rutCliente == null || id == null || creadoDia == null || creadoMes == null || creadoAnio == null ||updatedDia == null ||
                    updatedMes == null || updatedAnio == null || direccionSuministro == null || activo == null || potenciaMaxKw == null) {
                    return null
                }

                val cliente = clienteRepo?.obtenerPorRut(rutCliente)
                if (cliente == null) {
                    println("No se encontró cliente $rutCliente para medidor $codigo")
                    return null
                }
                return MedidorMonofasico(id, Date(creadoAnio.toInt(), creadoMes.toInt(), creadoDia.toInt()),
                    Date(updatedAnio.toInt(), updatedMes.toInt(), updatedDia.toInt()), codigo, direccionSuministro, activo.toBoolean(), cliente, potenciaMaxKw.toDouble())
        }
    }

    override fun crear(m: Medidor, rutCliente: String): Medidor {
        for (medidor in repositorio) {
            if (medidor.getCodigo() == m.getCodigo()){
                println("Ya existe medidor con este codigo")
                return medidor
            }
        }
        repositorio.add(m)
        println("Medidor creado correctamente")

        persistencia?.let { p ->
            val key = "$PREFIJO_KEY${m.getCodigo()}"
            val data = medidorATexto(m).encodeToByteArray()
            p.save(key, data)
        }

        return m
    }

    override fun listarPorCliente(rut: String): List<Medidor> {
        if (rut.isBlank()) {
            return repositorio.toList()
        }

        return repositorio.filter {
            it.getCliente().getRut().contains(rut, ignoreCase = true)
        }
    }

    override fun obtenerPorCodigo(codigo: String): Medidor? {
        if (codigo.isBlank()) {
            return null
        }

        val medidorEncontrado = repositorio.find {
            it.getCodigo().contains(codigo, ignoreCase = true)
        }

        if (medidorEncontrado != null) {
            println("Medidor encontrado")
        } else {
            println("No se encontro el medidor")
        }
        return medidorEncontrado
    }

    override fun eliminar(codigo: String): Boolean {
        val medidorEncontrado = repositorio.find { it.getCodigo() == codigo }

        if (medidorEncontrado != null){
            repositorio.remove(medidorEncontrado)
            println("Medidor eliminado correctamente")
            persistencia?.let { p ->
                val key = "$PREFIJO_KEY$codigo"
                p.delete(key)
            }
            return true
        }
        return false
    }
}