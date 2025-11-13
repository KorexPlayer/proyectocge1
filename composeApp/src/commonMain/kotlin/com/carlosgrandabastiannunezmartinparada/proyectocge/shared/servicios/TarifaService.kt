package com.carlosgrandabastiannunezmartinparada.proyectocge.shared.servicios

import com.carlosgrandabastiannunezmartinparada.proyectocge.shared.dominio.Cliente
import com.carlosgrandabastiannunezmartinparada.proyectocge.shared.dominio.Tarifa
import com.carlosgrandabastiannunezmartinparada.proyectocge.shared.dominio.TarifaComercial
import com.carlosgrandabastiannunezmartinparada.proyectocge.shared.dominio.TarifaResidencial

/**
 * Servicio para determinar
 * la [Tarifa] aplicable a un [Cliente].
 *
 * Su función principal es seleccionar el algoritmo de cálculo de tarifa
 * correcto basándose en las propiedades del cliente.
 */
class TarifaService {

    /**
     * Selecciona y devuelve la instancia de [Tarifa] apropiada para un [Cliente] dado.
     *
     * Utiliza el [Cliente.getTipoLugar] para decidir qué estrategia de
     * tarificación aplicar (Residencial o Comercial).
     *
     * @param cliente El [Cliente] para el cual se determinará la tarifa.
     * @return Una instancia de [Tarifa] ([TarifaResidencial] o [TarifaComercial])
     * según corresponda.
     */
    fun tarifaPara(cliente: Cliente): Tarifa {
        if (cliente.getTipoLugar() == "Residencial") {
            return TarifaResidencial()
        }
        // Retorna TarifaComercial por defecto si no es "Residencial"
        return TarifaComercial()
    }
}