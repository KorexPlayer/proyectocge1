package com.carlosgrandabastiannunezmartinparada.proyectocge.shared.persistencia.persistenciadatos

import com.carlosgrandabastiannunezmartinparada.proyectocge.shared.dominio.Date
import com.carlosgrandabastiannunezmartinparada.proyectocge.shared.dominio.LecturaConsumo
import com.carlosgrandabastiannunezmartinparada.proyectocge.shared.dominio.Medidor
import com.carlosgrandabastiannunezmartinparada.proyectocge.shared.persistencia.repositorios.LecturaRepositorio
import com.carlosgrandabastiannunezmartinparada.proyectocge.shared.persistencia.repositorios.MedidorRepositorio

/**
 * Implementación Singleton (object) del repositorio [LecturaRepositorio].
 *
 * Gestiona el almacenamiento en memoria (CRUD) de [LecturaConsumo],
 * coordina su persistencia (serialización/deserialización) y
 * vincula las lecturas a sus [Medidor] correspondientes a través de [MedidorRepoImpl].
 */
object LecturaRepoImpl : LecturaRepositorio{

    /** Almacén en memoria (cache) de las lecturas de consumo. */
    private val repositorio: MutableList<LecturaConsumo> = mutableListOf()
    /** Dependencia del repositorio de medidores, usado para vincular lecturas. */
    private var repositorioM: MedidorRepoImpl? = null
    /** Dependencia del servicio de persistencia (ej. archivos, BD). */
    private var persistencia: PersistenciaDato? = null

    /** Prefijo usado para las claves (keys) en el sistema de persistencia. */
    private const val PREFIJO_KEY = "lecturas_"
    /** Delimitador usado para la serialización/deserialización de lecturas a String. */
    private const val DELIMITADOR = "::"

    /**
     * Inicializa el repositorio.
     *
     * Inyecta las dependencias necesarias ([PersistenciaDato], [MedidorRepoImpl])
     * y ejecuta la carga inicial de datos ([cargarDatos]) desde la persistencia.
     *
     * @param persistencia La instancia del servicio de persistencia.
     * @param repositorioMedidores La instancia del repositorio de medidores.
     */
    fun init(persistencia: PersistenciaDato, repositorioMedidores: MedidorRepoImpl) {
        this.persistencia = persistencia
        this.repositorioM = repositorioMedidores
        cargarDatos()
    }

    /**
     * Carga todas las lecturas desde el servicio de persistencia.
     *
     * Lee todas las claves que coinciden con [PREFIJO_KEY], las decodifica
     * usando [textoALectura] y las añade al [repositorio] en memoria.
     */
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

    /**
     * Serializa una instancia de [LecturaConsumo] a un formato [String] plano.
     *
     * Utiliza [DELIMITADOR] para concatenar todas las propiedades necesarias
     * para la persistencia.
     *
     * @param l La [LecturaConsumo] a serializar.
     * @return Un [String] que representa la lectura.
     */
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

    /**
     * Deserializa un [String] (leído desde persistencia) a una instancia de [LecturaConsumo].
     *
     * Divide el [data] usando [DELIMITADOR] y reconstruye el objeto [LecturaConsumo],
     * incluyendo sus objetos [Date].
     *
     * @param data El [String] serializado.
     * @return Una nueva instancia de [LecturaConsumo], o `null` si el formato es inválido
     * o faltan datos (parsing falla).
     */
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


    /**
     * Registra una nueva [LecturaConsumo] en el sistema.
     *
     * 1. Añade la lectura al [repositorio] en memoria.
     * 2. La vincula al [Medidor] correspondiente llamando a [repositorioM].
     * 3. La serializa [lecturaATexto] y la guarda en [persistencia].
     *
     * @param l La [LecturaConsumo] a registrar.
     * @return La [LecturaConsumo] registrada.
     */
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

    /**
     * Obtiene una lista de lecturas filtrada.
     *
     * **Nota:** La lógica actual usa `||` (OR). La lectura se incluirá si
     * el `idMedidor` *contiene* el ID buscado, O si el `anio` coincide,
     * O si el `mes` coincide.
     *
     * @param idMedidor El ID (o parte del ID) del medidor a buscar.
     * @param anio El año a filtrar.
     * @param mes El mes a filtrar.
     * @return Una [List] de [LecturaConsumo] que cumple al menos una de las condiciones.
     */
    override fun listarPorMedidor(idMedidor: String, anio: Int, mes: Int): List<LecturaConsumo> {
        val listaFiltrada: MutableList<LecturaConsumo> = mutableListOf()
        for (lectura in repositorio) {
            if (lectura.getIdMedidor().contains(idMedidor) || lectura.getAnio() == anio && lectura.getMes() == mes) {
                listaFiltrada.add(lectura)
            }
        }
        return listaFiltrada
    }

    /**
     * Busca y devuelve la *primera* lectura encontrada para un [idMedidor] específico.
     *
     * Nota: A pesar del nombre "ultimaLectura", este método itera la lista y
     * devuelve la primera coincidencia que encuentra, no necesariamente la más reciente
     * cronológicamente.
     *
     * @param idMedidor El ID exacto del medidor a buscar.
     * @return La *primera* [LecturaConsumo] encontrada para ese ID, o `null` si no existe.
     */
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