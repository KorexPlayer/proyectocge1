package com.carlosgrandabastiannunezmartinparada.proyectocge.shared.servicios

import com.carlosgrandabastiannunezmartinparada.proyectocge.shared.dominio.Cliente
import com.carlosgrandabastiannunezmartinparada.proyectocge.shared.dominio.Tarifa
import com.carlosgrandabastiannunezmartinparada.proyectocge.shared.dominio.TarifaComercial
import com.carlosgrandabastiannunezmartinparada.proyectocge.shared.dominio.TarifaResidencial

class TarifaService {
    fun tarifaPara(cliente: Cliente): Tarifa {
        if (cliente.getTipoLugar() == "Residencial") {
            return TarifaResidencial()
        }
        return TarifaComercial()
    }
}