package com.touresbalon.producto.hospedaje.dto

import org.springframework.web.bind.annotation.PathVariable

class FiltroConsultaHospedajeDisponibleDto {
    lateinit var idCiudad: String
    lateinit var fechaDesde: String
    lateinit var fechaHasta: String
    lateinit var personaXHabitacion: String
}