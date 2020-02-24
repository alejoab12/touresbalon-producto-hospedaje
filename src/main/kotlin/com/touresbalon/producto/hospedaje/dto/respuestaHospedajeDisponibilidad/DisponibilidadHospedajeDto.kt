package com.touresbalon.producto.hospedaje.dto.respuestaHospedajeDisponibilidad

import java.util.*

class DisponibilidadHospedajeDto {
    lateinit var id: UUID
    lateinit var nombre: String
    var estrellas: Int = 0
    var disponible: Boolean = false
    var tipoHospedajeDisponible = mutableListOf<HabitacionDisponibilidadHospedajeDto>()
}