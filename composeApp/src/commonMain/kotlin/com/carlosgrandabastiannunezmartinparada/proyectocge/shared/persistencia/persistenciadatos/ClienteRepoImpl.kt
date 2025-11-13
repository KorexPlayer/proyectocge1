package com.carlosgrandabastiannunezmartinparada.proyectocge.shared.persistencia.persistenciadatos

import com.carlosgrandabastiannunezmartinparada.proyectocge.shared.dominio.Cliente
import com.carlosgrandabastiannunezmartinparada.proyectocge.shared.dominio.EstadoCliente
import com.carlosgrandabastiannunezmartinparada.proyectocge.shared.persistencia.repositorios.ClienteRepositorio

/**
 * Implementación Singleton (object) del repositorio [ClienteRepositorio].
 *
 * Se encarga de la gestión en memoria (CRUD) de las entidades [Cliente]
 * y coordina la persistencia de datos (guardado/carga) mediante serialización/deserialización
 * manual a texto plano.
 */
object ClienteRepoImpl : ClienteRepositorio {
    /** Almacén en memoria (cache) de los clientes. */
    val repositorio: MutableList<Cliente> = mutableListOf()
    /** Dependencia del servicio de persistencia (ej. archivos, BD) inyectada en [init]. */
    private var persistencia: PersistenciaDato? = null
    /** Prefijo usado para las claves (keys) en el sistema de persistencia para identificar clientes. */
    private const val PREFIJO_KEY = "clientes_"
    /** Delimitador usado para la pasa y devuelve de clientes a String. */
    private const val DELIMITADOR = "::"

    /**
     * Inicializa el repositorio.
     *
     * Inyecta la dependencia de [PersistenciaDato] y ejecuta la
     * carga inicial de datos ([cargarDatos]) desde la persistencia.
     *
     * @param persistencia La instancia del servicio de persistencia a utilizar.
     */
    fun init(persistencia: PersistenciaDato) {
        this.persistencia = persistencia
        cargarDatos()
    }

    /**
     * Carga todos los clientes desde el servicio de persistencia.
     *
     * Lee todas las claves que coinciden con [PREFIJO_KEY], las decodifica
     * usando [textoACliente] y las añade al [repositorio] en memoria,
     * evitando duplicados (verificando con [obtenerPorRut]).
     */
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

    /**
     * Pasa una instancia de [Cliente] a un formato [String] plano.
     *
     * Utiliza [DELIMITADOR] para concatenar las propiedades básicas del cliente
     * para su almacenamiento.
     *
     * @param c El [Cliente] a serializar.
     * @return Un [String] que representa al cliente.
     */
    private fun clienteATexto(c: Cliente): String {
        return "${c.getRut()}$DELIMITADOR" +
                "${c.getNombre()}$DELIMITADOR" +
                "${c.getEmail()}$DELIMITADOR" +
                "${c.getDireccionFacturacion()}$DELIMITADOR" +
                "${c.getEstado()}$DELIMITADOR${c.getTipoLugar()}"
    }

    /**
     * Pasa un [String] (leído desde persistencia) a una instancia de [Cliente].
     *
     * Divide el [data] usando [DELIMITADOR] y reconstruye el objeto [Cliente].
     * Maneja la conversión de [String] a [EstadoCliente].
     *
     * @param data El [String] serializado.
     * @return Una nueva instancia de [Cliente], o `null` si el formato es inválido
     * o faltan datos (parsing falla).
     */
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

    /**
     * Crea un nuevo [Cliente] en el repositorio y lo persiste.
     *
     * Comprueba si ya existe un cliente con el mismo RUT. Si no existe,
     * lo añade a la lista [repositorio] y lo guarda en [persistencia]
     * usando [clienteATexto].
     *
     * @param c El [Cliente] a crear.
     * @return El [Cliente] creado, o el cliente existente si se encontró un duplicado.
     */
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

    /**
     * Actualiza un [Cliente] existente en el repositorio y en persistencia.
     *
     * Busca el cliente por RUT. Si lo encuentra, reemplaza la instancia en
     * [repositorio] y sobrescribe el dato en [persistencia].
     *
     * @param c El [Cliente] con los datos actualizados.
     * @return El [Cliente] actualizado. Si no se encuentra, devuelve el mismo
     * objeto [c] y notifica por consola.
     */
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

    /**
     * Elimina un [Cliente] del repositorio y de la persistencia usando su RUT.
     *
     * @param rut El RUT del cliente a eliminar.
     * @return `true` si el cliente fue encontrado y eliminado, `false` en caso contrario.
     */
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

    /**
     * Busca y devuelve un [Cliente] específico basado en su RUT.
     *
     * La búsqueda se realiza en la lista en memoria [repositorio].
     *
     * @param rut El RUT del cliente a buscar.
     * @return El [Cliente] encontrado, o `null` si no existe.
     */
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

    /**
     * Devuelve una lista de [Cliente] que coinciden con un [filtro].
     *
     * La búsqueda es case-insensitive y se aplica tanto al [getRut]
     * como al [getNombre] del cliente.
     *
     * @param filtro El texto a buscar.
     * @return Una [List] de [Cliente] (puede estar vacía si no hay coincidencias).
     */
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