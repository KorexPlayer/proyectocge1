package com.carlosgrandabastiannunezmartinparada.proyectocge.shared.persistencia.persistenciadatos

import com.carlosgrandabastiannunezmartinparada.proyectocge.shared.dominio.Boleta
import com.carlosgrandabastiannunezmartinparada.proyectocge.shared.dominio.Cliente
import com.carlosgrandabastiannunezmartinparada.proyectocge.shared.dominio.Date
import com.carlosgrandabastiannunezmartinparada.proyectocge.shared.dominio.EstadoBoleta
import com.carlosgrandabastiannunezmartinparada.proyectocge.shared.dominio.EstadoCliente
import com.carlosgrandabastiannunezmartinparada.proyectocge.shared.dominio.TarifaDetalle
import com.carlosgrandabastiannunezmartinparada.proyectocge.shared.persistencia.repositorios.BoletaRepositorio
import com.carlosgrandabastiannunezmartinparada.proyectocge.shared.persistencia.repositorios.ClienteRepositorio


/**
 * Implementación Singleton (object) del repositorio [BoletaRepositorio].
 *
 * Gestiona el almacenamiento en memoria (CRUD) de las [Boleta] y coordina
 * la persistencia de datos (guardado/carga) y la vinculación con el [ClienteRepositorio].
 */
object BoletaRepoImpl : BoletaRepositorio {

    /** Almacén en memoria (cache) de las boletas. */
    private val repositorio: MutableList<Boleta> = mutableListOf()
    /** Dependencia del servicio de persistencia (ej. archivos, BD). */
    private var persistencia: PersistenciaDato? = null
    /** Dependencia del repositorio de clientes, usado para vincular boletas. */
    private var clienteRepo: ClienteRepositorio? = null

    /** Prefijo usado para las claves (keys) en el sistema de persistencia. */
    private const val PREFIJO_KEY = "boletas_"
    /** Delimitador usado para la creación/lectura de boletas a String. */
    private const val DELIMITADOR = "::"

    /**
     * Inicializa el repositorio.
     *
     * Inyecta las dependencias necesarias (persistencia, repositorio de clientes)
     * y ejecuta la carga inicial de datos desde la persistencia.
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
     * Carga todas las boletas desde el servicio de persistencia.
     *
     * Lee todas las claves que coinciden con [PREFIJO_KEY], las decodifica
     * usando [textoABoleta] y las añade al repositorio en memoria,
     * evitando duplicados.
     */
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

    /**
     * Serializa una instancia de [Boleta] a un formato [String] plano.
     *
     * Utiliza [DELIMITADOR] para concatenar todas las propiedades necesarias
     * para la persistencia.
     *
     * @param b La [Boleta] a crear.
     * @return Un [String] que representa la boleta.
     */
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

    /**
     * Pasa un [String] (leído desde persistencia) a una instancia de [Boleta].
     *
     * Divide el [data] usando [DELIMITADOR] y reconstruye el objeto [Boleta],
     * incluyendo sus objetos anidados ([Cliente], [Date], [TarifaDetalle]).
     *
     * @param data El [String] serializado.
     * @return Una nueva instancia de [Boleta], o `null` si el formato es inválido
     * o faltan datos (parsing falla).
     */
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

    /**
     * Guarda una nueva [Boleta] en el repositorio y en persistencia.
     *
     * 1. Añade la boleta a la lista en memoria [repositorio].
     * 2. La vincula al [Cliente] correspondiente a través de [clienteRepo].
     * 3. La crea [boletaATexto] y la guarda usando [persistencia].
     *
     * @param b La [Boleta] a guardar.
     * @return La [Boleta] guardada.
     */
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

    /**
     * Busca y devuelve una [Boleta] específica.
     *
     * La búsqueda se realiza en memoria usando una combinación única de
     * RUT del cliente, año y mes.
     *
     * @param rut El RUT del cliente.
     * @param anio El año de la boleta.
     * @param mes El mes de la boleta.
     * @return La [Boleta] encontrada, o `null` si no existe.
     */
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

    /**
     * Obtiene todas las boletas asociadas a un RUT de cliente específico.
     *
     * Filtra la lista en memoria [repositorio] por el RUT del cliente.
     *
     * @param rut El RUT del cliente a buscar.
     * @return Una [List] de [Boleta] (puede estar vacía si no se encuentran).
     */
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