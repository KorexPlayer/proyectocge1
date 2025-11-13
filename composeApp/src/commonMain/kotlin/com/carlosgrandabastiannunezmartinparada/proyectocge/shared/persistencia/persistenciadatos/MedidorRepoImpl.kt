package com.carlosgrandabastiannunezmartinparada.proyectocge.shared.persistencia.persistenciadatos

import com.carlosgrandabastiannunezmartinparada.proyectocge.shared.dominio.Date
import com.carlosgrandabastiannunezmartinparada.proyectocge.shared.dominio.Medidor
import com.carlosgrandabastiannunezmartinparada.proyectocge.shared.dominio.MedidorMonofasico
import com.carlosgrandabastiannunezmartinparada.proyectocge.shared.dominio.MedidorTrifasico
import com.carlosgrandabastiannunezmartinparada.proyectocge.shared.persistencia.repositorios.ClienteRepositorio
import com.carlosgrandabastiannunezmartinparada.proyectocge.shared.persistencia.repositorios.MedidorRepositorio

/**
 * Implementación Singleton (object) del repositorio [MedidorRepositorio].
 *
 * Gestiona el almacenamiento en memoria (CRUD) de las entidades [Medidor],
 * manejando el polimorfismo entre [MedidorMonofasico] y [MedidorTrifasico].
 * Coordina la persistencia y la vinculación con el [ClienteRepositorio].
 */
object MedidorRepoImpl : MedidorRepositorio {

    /** Almacén en memoria (cache) de los medidores (mono y trifásicos). */
    private val repositorio: MutableList<Medidor> = mutableListOf()
    /** Dependencia del servicio de persistencia (ej. archivos, BD). */
    private var persistencia: PersistenciaDato? = null
    /** Dependencia del repositorio de clientes, usado para vincular medidores. */
    private var clienteRepo: ClienteRepositorio? = null

    /** Prefijo usado para las claves (keys) en el sistema de persistencia. */
    private const val PREFIJO_KEY = "medidores_"
    /** Delimitador usado para la serialización/deserialización de medidores a String. */
    private const val DELIMITADOR = "::"

    /**
     * Inicializa el repositorio.
     *
     * Inyecta las dependencias necesarias ([PersistenciaDato], [ClienteRepositorio])
     * y ejecuta la carga inicial de datos ([cargarDatos]).
     *
     * @param persistencia La instancia del servicio de persistencia.
     * @param clienteRepo La instancia del repositorio de clientes.
     */
    fun init(persistencia: PersistenciaDato, clienteRepo: ClienteRepositorio) {
        this.persistencia = persistencia
        this.clienteRepo = clienteRepo
        cargarDatos()
    }

    /**
     * Carga todos los medidores desde el servicio de persistencia.
     *
     * Lee todas las claves que coinciden con [PREFIJO_KEY], las decodifica
     * usando [textoAMedidor] (que maneja el polimorfismo) y las añade
     * al [repositorio] en memoria, evitando duplicados.
     */
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

    /**
     * Serializa una instancia de [Medidor] (mono o trifásico) a un [String] plano.
     *
     * Detecta el tipo ([MedidorMonofasico] o [MedidorTrifasico]) y añade
     * los campos específicos (como `factorPotencia` para trifásico) al final
     * del String serializado.
     *
     * @param m El [Medidor] a serializar.
     * @return Un [String] que representa el medidor.
     */
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

    /**
     * Deserializa un [String] (leído desde persistencia) a una instancia de [Medidor].
     *
     * Determina si es [MedidorTrifasico] (13 partes) o [MedidorMonofasico] (12 partes)
     * basándose en la cantidad de campos separados por [DELIMITADOR].
     *
     * Requiere que [clienteRepo] esté inicializado para buscar y re-asociar
     * el [Cliente] usando el RUT guardado.
     *
     * @param data El [String] serializado.
     * @return Una instancia de [Medidor] (Monofasico o Trifasico), o `null` si
     * el formato es inválido, faltan datos, o el cliente asociado no se encuentra.
     */
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

    /**
     * Crea un nuevo [Medidor] en el repositorio y lo persiste.
     *
     * 1. Comprueba si ya existe un medidor con el mismo código.
     * 2. Añade el medidor al [repositorio] en memoria.
     * 3. Lo vincula al [Cliente] correspondiente (usando [rutCliente]).
     * 4. Lo serializa ([medidorATexto]) y lo guarda en [persistencia].
     *
     * @param m El [Medidor] (mono o trifásico) a crear.
     * @param rutCliente El RUT del cliente al que se debe asociar el medidor.
     * @return El [Medidor] creado, o el medidor existente si se encontró un duplicado.
     */
    override fun crear(m: Medidor, rutCliente: String): Medidor {
        for (medidor in repositorio) {
            if (medidor.getCodigo() == m.getCodigo()){
                println("Ya existe medidor con este codigo")
                return medidor
            }
        }
        repositorio.add(m)
        clienteRepo?.obtenerPorRut(rutCliente)?.agregarMedidor(m)
        println("Medidor creado correctamente")

        persistencia?.let { p ->
            val key = "$PREFIJO_KEY${m.getCodigo()}"
            val data = medidorATexto(m).encodeToByteArray()
            p.save(key, data)
        }

        return m
    }

    /**
     * Obtiene una lista de medidores filtrados por el RUT del cliente.
     *
     * Si el [rut] está en blanco, devuelve la lista completa de medidores.
     * La búsqueda es case-insensitive y parcial (contains).
     *
     * @param rut El RUT del cliente a buscar.
     * @return Una [List] de [Medidor] (puede estar vacía).
     */
    override fun listarPorCliente(rut: String): List<Medidor> {
        if (rut.isBlank()) {
            return repositorio.toList()
        }

        return repositorio.filter {
            it.getCliente().getRut().contains(rut, ignoreCase = true)
        }
    }

    /**
     * Busca y devuelve un [Medidor] específico basado en su código.
     *
     * Si el [codigo] está en blanco, devuelve `null`.
     * La búsqueda es case-insensitive y parcial (contains).
     *
     * @param codigo El código (o parte del código) del medidor a buscar.
     * @return El [Medidor] encontrado, o `null` si no existe.
     */
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

    /**
     * Elimina un [Medidor] del repositorio y de la persistencia usando su código.
     *
     * @param codigo El código exacto del medidor a eliminar.
     * @return `true` si el medidor fue encontrado y eliminado, `false` en caso contrario.
     */
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