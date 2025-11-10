package com.carlosgrandabastiannunezmartinparada.proyectocge.shared.persistencia.persistenciadatos

import com.carlosgrandabastiannunezmartinparada.proyectocge.shared.dominio.Date
import com.carlosgrandabastiannunezmartinparada.proyectocge.shared.dominio.LecturaConsumo
import com.carlosgrandabastiannunezmartinparada.proyectocge.shared.dominio.Medidor
import com.carlosgrandabastiannunezmartinparada.proyectocge.shared.persistencia.repositorios.LecturaRepositorio
import com.carlosgrandabastiannunezmartinparada.proyectocge.shared.persistencia.repositorios.MedidorRepositorio

object LecturaRepoImpl : LecturaRepositorio{

    private val repositorio: MutableList<LecturaConsumo> = mutableListOf()
    private var repositorioM: MedidorRepoImpl? = null
    private var persistencia: PersistenciaDato? = null
    private const val PREFIJO_KEY = "lecturas_"
    private const val DELIMITADOR = "::"

    fun init(persistencia: PersistenciaDato, repositorioMedidores: MedidorRepoImpl) {
        this.persistencia = persistencia
        this.repositorioM = repositorioMedidores
        cargarDatos()
    }

    private fun cargarDatos() {
        persistencia?.list(PREFIJO_KEY)?.forEach { key ->
            val bytes = persistencia?.read(key)
            if (bytes != null) {
                val lectura = textoALectura(bytes.decodeToString())
                if (lectura != null) {
                    repositorio.add(lectura)
                }
            }
        }
    }

    private fun lecturaATexto(l: LecturaConsumo): String {
        return "${l.id}$DELIMITADOR" +
                "${l.createdAt.anioGet()}$DELIMITADOR" +
                "${l.createdAt.mesGet()}$DELIMITADOR" +
                "${l.createdAt.diaGet()}$DELIMITADOR" +
                "${l.updatedAt.anioGet()}$DELIMITADOR" +
                "${l.updatedAt.mesGet()}$DELIMITADOR" +
                "${l.updatedAt.diaGet()}$DELIMITADOR" +
                "${l.getIdMedidor()}$DELIMITADOR" +
                "${l.getAnio()}$DELIMITADOR" +
                "${l.getMes()}$DELIMITADOR" +
                "${l.getKwhLeidos()}$DELIMITADOR"
    }

    private fun textoALectura(data: String): LecturaConsumo? {
        val parts = data.split(DELIMITADOR)
        val id = parts.getOrNull(0)
        val createdAnio = parts.getOrNull(1)?.toIntOrNull()
        val createdMes = parts.getOrNull(2)?.toIntOrNull()
        val createdDia = parts.getOrNull(3)?.toIntOrNull()
        val updatedAnio = parts.getOrNull(4)?.toIntOrNull()
        val updatedMes = parts.getOrNull(5)?.toIntOrNull()
        val updatedDia = parts.getOrNull(6)?.toIntOrNull()
        val idMedidor = parts.getOrNull(7)
        val anio = parts.getOrNull(8)?.toIntOrNull()
        val mes = parts.getOrNull(9)?.toIntOrNull()
        val kwh = parts.getOrNull(10)?.toDoubleOrNull()


        if (id == null || createdAnio == null || createdMes == null || createdDia == null || updatedAnio == null ||
            updatedMes == null || updatedDia == null || idMedidor == null || anio == null || mes == null || kwh == null) {
            return null
        }
        return LecturaConsumo(id, Date(createdAnio, createdMes, createdDia), Date(updatedAnio, updatedMes, updatedDia), idMedidor, anio, mes, kwh)
    }


    override fun registrar(l: LecturaConsumo): LecturaConsumo {
        repositorio.add(l)
        repositorioM?.obtenerPorCodigo(l.getIdMedidor())?.agregarLecturaConsumo(l)
        println("Se ha registrado la nueva lectura")
        persistencia?.let { p ->
            // Creamos una key única simple usando el tiempo
            val key = "$PREFIJO_KEY${l.getIdMedidor()}_${l.id}"
            val data = lecturaATexto(l).encodeToByteArray()
            p.save(key, data)
        }
        return l
    }

    override fun listarPorMedidor(idMedidor: String, anio: Int, mes: Int): List<LecturaConsumo> {
        val listaFiltrada: MutableList<LecturaConsumo> = mutableListOf()
        for (lectura in repositorio) {
            if (lectura.getIdMedidor().contains(idMedidor) || lectura.getAnio() == anio || lectura.getMes() == mes) {
                listaFiltrada.add(lectura)
            }
        }
        return listaFiltrada
    }

    override fun ultimaLectura(idMedidor: String): LecturaConsumo? {
        for (lectura in repositorio) {
            if (lectura.getIdMedidor() == idMedidor) {
                println("Lectura conseguida")
                return lectura
            }
        }
        println("No se consiguio la lectura")
        return null
    }
}