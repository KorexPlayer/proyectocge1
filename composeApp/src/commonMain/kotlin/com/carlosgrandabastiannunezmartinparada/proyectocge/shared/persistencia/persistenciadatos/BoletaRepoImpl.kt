package com.carlosgrandabastiannunezmartinparada.proyectocge.shared.persistencia.persistenciadatos

import com.carlosgrandabastiannunezmartinparada.proyectocge.shared.dominio.Boleta
import com.carlosgrandabastiannunezmartinparada.proyectocge.shared.dominio.Cliente
import com.carlosgrandabastiannunezmartinparada.proyectocge.shared.dominio.Date
import com.carlosgrandabastiannunezmartinparada.proyectocge.shared.dominio.EstadoBoleta
import com.carlosgrandabastiannunezmartinparada.proyectocge.shared.dominio.EstadoCliente
import com.carlosgrandabastiannunezmartinparada.proyectocge.shared.dominio.TarifaDetalle
import com.carlosgrandabastiannunezmartinparada.proyectocge.shared.persistencia.repositorios.BoletaRepositorio
import com.carlosgrandabastiannunezmartinparada.proyectocge.shared.persistencia.repositorios.ClienteRepositorio


object BoletaRepoImpl : BoletaRepositorio {
    private val repositorio: MutableList<Boleta> = mutableListOf()
    private var persistencia: PersistenciaDato? = null
    private var clienteRepo: ClienteRepositorio? = null
    private const val PREFIJO_KEY = "boletas_"
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
                val boleta = textoABoleta(bytes.decodeToString())
                if (boleta != null) {
                    // Evitamos duplicados al cargar
                    val existe = obtener(boleta.getCliente().getRut(), boleta.getAnio(), boleta.getMes())
                    if (existe == null) {
                        repositorio.add(boleta)
                    }
                }
            }
        }
    }

    private fun boletaATexto(b: Boleta): String {
        return   b.id +
                "$DELIMITADOR${b.createdAt.anioGet()}" +
                "$DELIMITADOR${b.createdAt.mesGet()}" +
                "$DELIMITADOR${b.createdAt.diaGet()}" +
                "$DELIMITADOR${b.updatedAt.anioGet()}" +
                "$DELIMITADOR${b.updatedAt.mesGet()}" +
                "$DELIMITADOR${b.updatedAt.diaGet()}" +
                "$DELIMITADOR${b.getIdCliente()}" +
                "$DELIMITADOR${b.getCliente().getRut()}" +
                "$DELIMITADOR${b.getCliente().getNombre()}" +
                "$DELIMITADOR${b.getCliente().getEmail()}" +
                "$DELIMITADOR${b.getCliente().getDireccionFacturacion()}" +
                "$DELIMITADOR${b.getCliente().getEstado()}" +
                "$DELIMITADOR${b.getCliente().getTipoLugar()}" +
                "$DELIMITADOR${b.getAnio()}" +
                "$DELIMITADOR${b.getMes()}" +
                "$DELIMITADOR${b.getKwhTotal()}" +
                "$DELIMITADOR${b.getDetalle().kwh}" +
                "$DELIMITADOR${b.getDetalle().subtotal}" +
                "$DELIMITADOR${b.getDetalle().cargos}" +
                "$DELIMITADOR${b.getDetalle().iva}" +
                "$DELIMITADOR${b.getDetalle().total}" +
                "$DELIMITADOR${b.getEstado()}"
    }

    private fun textoABoleta(data: String): Boleta? {
        val parts = data.split(DELIMITADOR)
        val id = parts.getOrNull(0)
        val createdAnio = parts.getOrNull(1)?.toIntOrNull()
        val createdMes = parts.getOrNull(2)?.toIntOrNull()
        val createdDia = parts.getOrNull(3)?.toIntOrNull()
        val updatedAnio = parts.getOrNull(4)?.toIntOrNull()
        val updatedMes = parts.getOrNull(5)?.toIntOrNull()
        val updatedDia = parts.getOrNull(6)?.toIntOrNull()
        val idCliente = parts.getOrNull(7)
        val rut = parts.getOrNull(8)
        val nombre = parts.getOrNull(9)
        val email = parts.getOrNull(10)
        val direccion = parts.getOrNull(11)
        val estadoC = parts.getOrNull(12)
        val tipoLugar = parts.getOrNull(13)
        val anio = parts.getOrNull(14)?.toIntOrNull()
        val mes = parts.getOrNull(15)?.toIntOrNull()
        val kwhTotal = parts.getOrNull(16)?.toDoubleOrNull()
        val detalleKwh = parts.getOrNull(17)?.toDoubleOrNull()
        val detalleSubtotal = parts.getOrNull(18)?.toDoubleOrNull()
        val detalleCargos = parts.getOrNull(19)?.toDoubleOrNull()
        val detalleIva = parts.getOrNull(20)?.toDoubleOrNull()
        val detalleTotal = parts.getOrNull(21)?.toDoubleOrNull()
        val estado = parts.getOrNull(22)

        if (id == null || createdAnio == null || createdMes == null || createdDia == null || updatedAnio == null || updatedMes == null || updatedDia == null || idCliente == null ||
            rut == null || nombre == null || email == null || direccion == null || estadoC == null || tipoLugar == null || anio == null || mes == null || kwhTotal == null ||
            detalleKwh == null || detalleSubtotal == null || detalleCargos == null || detalleIva == null || detalleTotal == null || estado == null) {
            return null
        }
        return Boleta(id,
            Date(createdAnio, createdMes, createdDia), Date(updatedAnio, updatedMes, updatedDia),
            Cliente(rut, nombre, email, direccion, if(estadoC == "ACTIVO") {
                EstadoCliente.ACTIVO} else {EstadoCliente.INACTIVO}, tipoLugar), idCliente, anio, mes, kwhTotal,
            TarifaDetalle(detalleKwh, detalleSubtotal, detalleCargos, detalleIva, detalleTotal),
            when {(estado == "EMITIDA") -> {EstadoBoleta.EMITIDA} (estado == "ENVIADA") -> {EstadoBoleta.ENVIADA}
                            (estado == "PAGADA") -> {EstadoBoleta.PAGADA} else -> {EstadoBoleta.ANULADA}}
        )
    }

    override fun guardar(b: Boleta): Boleta {
        repositorio.add(b)
        clienteRepo?.obtenerPorRut(b.getIdCliente())?.agregarBoleta(b)
        println("Se ha anadido la boleta al repositorio")
        persistencia?.let { p ->
            val key = "$PREFIJO_KEY${b.getCliente().getRut()}_${b.getAnio()}_${b.getMes()}"
            val data = boletaATexto(b).encodeToByteArray()
            p.save(key, data)
        }
        return b
    }

    override fun obtener(
        rut: String,
        anio: Int,
        mes: Int
    ): Boleta? {
        for (boleta in repositorio) {
            if (boleta.getCliente().getRut() == rut && boleta.getAnio() == anio && boleta.getMes() == mes) {
                println("Boleta encontrada con exito")
                return boleta
            }
        }
        println("No existe la boleta en nuestro repositorio")
        return null
    }

    override fun listarPorCliente(rut: String): List<Boleta> {
        val listaPorCliente: MutableList<Boleta> = mutableListOf()
        for (boleta in repositorio) {
            if (boleta.getCliente().getRut() == rut) {
                listaPorCliente.add(boleta)
            }
        }
        return listaPorCliente
    }


}