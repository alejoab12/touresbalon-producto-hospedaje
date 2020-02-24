package com.touresbalon.producto.hospedaje.dto.respuestaHospedajeDisponibilidad

import com.touresbalon.producto.hospedaje.entidad.DisponibilidadHospedaje

class FechaTipoHospedajeDisponibleDto {
    lateinit var fecha: String
    lateinit var listFechaDisponible: List<DisponibilidadHospedaje>
}