package com.carlosgrandabastiannunezmartinparada.proyectocge.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SegmentedButton
import androidx.compose.material3.SegmentedButtonDefaults
import androidx.compose.material3.SingleChoiceSegmentedButtonRow
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.carlosgrandabastiannunezmartinparada.proyectocge.componentes.cajaDespegable
import com.carlosgrandabastiannunezmartinparada.proyectocge.componentes.campoTextField
import com.carlosgrandabastiannunezmartinparada.proyectocge.shared.dominio.Date
import com.carlosgrandabastiannunezmartinparada.proyectocge.shared.dominio.LecturaConsumo
import com.carlosgrandabastiannunezmartinparada.proyectocge.shared.persistencia.persistenciadatos.LecturaRepoImpl

@Composable
fun PantallaLectura(repositorioLecturas: LecturaRepoImpl) {
    var selected by remember { mutableStateOf(0) }
    val options = listOf("Agregar", "Mostrar")
    Column {
        Text("Manejo de Lecturas de Consumo", modifier = Modifier.fillMaxWidth(), style = MaterialTheme.typography.titleLarge, textAlign = TextAlign.Center)
        Box(modifier = Modifier.fillMaxWidth(), contentAlignment = Alignment.Center) {
            SingleChoiceSegmentedButtonRow {
                options.forEachIndexed { index, option ->
                    SegmentedButton(
                        shape = SegmentedButtonDefaults.itemShape(
                            index = index,
                            count = options.size
                        ), onClick = { selected = index },
                        selected = index == selected,
                        label = { Text(option) }
                    )
                }
            }
        }
            Spacer(modifier = Modifier.height(5.dp))
            Column(modifier = Modifier.fillMaxSize()) {
                when (selected) {
                    0 -> {
                        pantallaNuevaLectura(repositorioLecturas)
                    }

                    1 -> {
                        paginaListarLecturas(repositorioLecturas)
                    }
                }
            }
        }
    }

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun pantallaNuevaLectura(repositorioLecturas: LecturaRepoImpl) {
    //Datos Colocados manualmente
    var id by remember { mutableStateOf("") } // String
    var idMedidor by remember { mutableStateOf("") } // String
    var kwhLeidos by remember { mutableStateOf("") } // Double
    var aniol by remember { mutableStateOf("") } // Int
    var createdAtAnio by remember { mutableStateOf("") } // Date
    var updatedAtAnio by remember { mutableStateOf("") } // Date
    var mesl by remember { mutableStateOf("") } //Int
    //Datos colocados para created
    var createdAtMes by remember { mutableStateOf("") } // Date
    var createdAtDia by remember { mutableStateOf("") } // Date
    //Datos colocados para updated
    var updatedAtMes by remember { mutableStateOf("") } // Date
    var updatedAtDia by remember { mutableStateOf("") } // Date

    Column(horizontalAlignment = Alignment.CenterHorizontally, modifier = Modifier.fillMaxWidth()) {
        Text("Registrar nueva lectura", modifier = Modifier.fillMaxWidth(), style = MaterialTheme.typography.titleLarge, textAlign = TextAlign.Center)
        campoTextField("ID", id, onChange = { id = it }, modifier1 = Modifier.width(800.dp))
        campoTextField("ID del Medidor", idMedidor, onChange = { idMedidor = it }, modifier1 = Modifier.width(800.dp))
        campoTextField("Año de Lectura (Solo Numeros)", aniol, onChange = { aniol = it }, modifier1 = Modifier.width(800.dp))
        campoTextField("Mes de Lectura (Solo Numeros)", mesl, onChange = { mesl = it }, modifier1 = Modifier.width(800.dp))
        campoTextField("Kw/h Leidos (Solo Numeros)", kwhLeidos, onChange = { kwhLeidos = it }, modifier1 = Modifier.width(800.dp))
        //Colocar como cajitas con row.
        Box {
            Row (modifier = Modifier.width(800.dp).height(50.dp),
                horizontalArrangement = Arrangement.spacedBy(4.dp),
                verticalAlignment = Alignment.CenterVertically) {
                campoTextField("Año Creado (Solo Numeros)", createdAtAnio, onChange = {createdAtAnio = it}, modifier1 = Modifier.weight(1f).fillMaxWidth())
                campoTextField("Mes Creado (Solo Numeros)", createdAtMes, onChange = {createdAtMes = it}, modifier1 = Modifier.weight(1f).fillMaxWidth())
                campoTextField("Dia Creado (Solo Numeros)", createdAtDia, onChange = {createdAtDia = it}, modifier1 = Modifier.weight(1f).fillMaxWidth())
            }
        }
           Row (modifier = Modifier.width(800.dp).height(100.dp),
               horizontalArrangement = Arrangement.spacedBy(4.dp),
               verticalAlignment = Alignment.CenterVertically) {
               campoTextField("Año Actualizado (Solo Numeros)", updatedAtAnio, onChange = {updatedAtAnio = it}, modifier1 = Modifier.weight(1f).fillMaxWidth())
               campoTextField("Mes Actualizado (Solo Numeros)", updatedAtMes, onChange = {updatedAtMes = it}, modifier1 = Modifier.weight(1f).fillMaxWidth())
               campoTextField("Dia Actualizado (Solo Numeros)", updatedAtDia, onChange = {updatedAtDia = it}, modifier1 = Modifier.weight(1f).fillMaxWidth())
            }

            Row(horizontalArrangement = Arrangement.spacedBy(1.dp)) {
                Button(onClick = {
                    repositorioLecturas.registrar(
                        LecturaConsumo(
                            id,
                            Date(createdAtAnio.toInt(), createdAtMes.toInt(), createdAtDia.toInt()),
                            Date(updatedAtAnio.toInt(), updatedAtMes.toInt(), updatedAtDia.toInt()),
                            idMedidor, aniol.toInt(), mesl.toInt(), kwhLeidos.toDouble()
                        )
                    )
                    id = ""
                    createdAtMes = ""
                    createdAtAnio = ""
                    createdAtDia = ""
                    updatedAtMes = ""
                    updatedAtDia = ""
                    updatedAtAnio = ""
                    idMedidor = ""
                    aniol = ""
                    mesl = ""
                    kwhLeidos = ""

                }) {
                    Text(text = "Registrar nueva lectura")
                }
            }
        }
    }
@Composable
private fun paginaListarLecturas(
    repositorio: LecturaRepoImpl
){
    var idMedidor by remember { mutableStateOf("") }
    //año seleccionado
    var diaSeleccionado by remember { mutableStateOf("1") }
    var diaExpandido by remember { mutableStateOf(false) }
    val aniol = listOf("2025", "2024", "2023", "2022", "2021", "2020", "2019", "2018", "2017", "2016", "2015", "2014", "2013",
        "2012", "2011", "2010", "2009", "2008", "2007", "2006", "2005", "2004", "2003", "2002", "2001", "2000", "1999", "1998",
        "1997", "1996", "1995", "1994", "1993", "1992", "1991", "1990", "1989", "1988", "1987", "1986", "1985", "1984", "1983",
        "1982", "1981", "1980", "1979", "1978", "1977", "1976", "1975", "1974", "1973", "1972", "1971", "1970", "1969", "1968",
        "1967", "1966", "1965", "1964", "1963", "1962", "1961", "1960", "1959", "1958", "1957", "1956", "1955", "1954", "1953",
        "1952", "1951", "1950", "1949", "1948", "1947", "1946", "1945", "1944", "1943", "1942", "1941", "1940", "1939", "1938",
        "1937", "1936", "1935", "1934", "1933", "1932", "1931", "1930", "1929", "1928", "1927", "1926", "1925", "1924", "1923",
        "1922", "1921", "1920", "1919", "1918", "1917", "1916", "1915", "1914", "1913", "1912", "1911", "1910", "1909", "1908",
        "1907", "1906", "1905", "1904", "1003", "1902", "1901", "1900", "1899", "1898", "1897", "1896", "1895", "1894", "1893",
        "1892", "1891", "1890", "1889", "1888", "1887", "1886", "1885", "1884", "1883", "1882", "1881", "1880", "1879", "1878",
        "1877", "1876", "1875", "1874", "1873", "1872", "1871", "1870", "1869", "1868", "1867", "1866", "1865", "1864", "1863",
        "1862", "1861", "1860", "1859", "1858", "1857", "1856", "1855", "1854", "1853", "1852", "1851", "1850", "1849", "1848",
        "1847", "1846", "1845", "1844", "1843", "1842", "1841", "1840", "1839", "1838", "1837", "1836", "1835", "1834", "1833",
        "1832", "1831", "1830", "1829", "1828", "1827", "1826", "1825", "1824", "1823", "1822", "1821", "1820", "1819", "1818",
        "1817", "1816", "1815", "1814", "1813", "1812", "1811", "1810", "1809", "1808", "1807", "1806", "1805", "1804", "1803",
        "1802", "1801", "1800", "1799", "1798", "1797", "1796", "1795", "1794", "1793", "1792", "1791", "1790", "1789", "1788",
        "1787", "1786", "1785", "1784", "1783", "1782", "1781", "1780", "1779", "1778", "1777", "1776", "1775", "1774", "1773",
        "1772", "1771", "1770", "1769", "1768", "1767", "1766", "1765", "1764", "1763", "1762", "1761", "1760")
    //Mes seleccionado
    var mesSeleccionado by remember { mutableStateOf("1") }
    var mesExpandido by remember { mutableStateOf(false) }
    val mesl = listOf("1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12")
    Text("Listado de Lecturas", style = MaterialTheme.typography.titleLarge, modifier = Modifier.fillMaxWidth(), textAlign = TextAlign.Center)
    Column(modifier = Modifier.fillMaxSize(), horizontalAlignment = Alignment.CenterHorizontally) {

        Row(
            modifier = Modifier.width(800.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically)
        {
            campoTextField("ID del Medidor", idMedidor, onChange = { idMedidor = it }, Modifier.weight(1f))
            val dia = cajaDespegable(diaExpandido, diaSeleccionado, "Año", aniol, modifier1 = Modifier.weight(1f))
            val mes = cajaDespegable(mesExpandido, mesSeleccionado, "Mes", mesl, modifier1 = Modifier.weight(1f))
            }
        Spacer(Modifier.width(8.dp))

        Row(modifier = Modifier.width(1280.dp)){
            Text("ID", modifier = Modifier.weight(1f), textAlign = TextAlign.Center)
            Text("Creado", modifier = Modifier.weight(1f), textAlign = TextAlign.Center)
            Text("Actualizado", modifier = Modifier.weight(1f), textAlign = TextAlign.Center)
            Text("ID Medidor", modifier = Modifier.weight(1f), textAlign = TextAlign.Center)
            Text("Año", modifier = Modifier.weight(1f), textAlign = TextAlign.Center)
            Text("Mes", modifier = Modifier.weight(1f), textAlign = TextAlign.Center)
            Text("Kw/h Leidos", modifier = Modifier.weight(1f), textAlign = TextAlign.Center)
        }
        Spacer(Modifier.width(8.dp))
        val repositorioLista = repositorio.listarPorMedidor(idMedidor, 0 ,0)
        LazyColumn {
            items(repositorioLista) { l ->
                Row(modifier = Modifier.width(1280.dp), horizontalArrangement = Arrangement.Center, verticalAlignment = Alignment.CenterVertically) {
                    Text(l.id, modifier = Modifier.weight(1f), textAlign = TextAlign.Center)
                    Text(l.createdAt.toString(), modifier = Modifier.weight(1f), textAlign = TextAlign.Center)
                    Text(l.updatedAt.toString(), modifier = Modifier.weight(1f), textAlign = TextAlign.Center)
                    Text(l.getIdMedidor(), modifier = Modifier.weight(1f), textAlign = TextAlign.Center)
                    Text(l.getAnio().toString(), modifier = Modifier.weight(1f), textAlign = TextAlign.Center)
                    Text(l.getMes().toString(), modifier = Modifier.weight(1f), textAlign = TextAlign.Center)
                    Text(l.getKwhLeidos().toString(), modifier = Modifier.weight(1f), textAlign = TextAlign.Center)
                }
                Spacer(Modifier.width(8.dp))
            }
        }
    }
}