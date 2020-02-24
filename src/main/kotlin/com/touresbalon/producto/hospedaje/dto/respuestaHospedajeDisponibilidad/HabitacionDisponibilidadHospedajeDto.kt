package com.touresbalon.producto.hospedaje.dto.respuestaHospedajeDisponibilidad

class HabitacionDisponibilidadHospedajeDto {
    lateinit var cantidadPersona: String
    lateinit var disponibilidadTipoHospedaje: MutableList<TipoHospedajeDisponibleDto>
}