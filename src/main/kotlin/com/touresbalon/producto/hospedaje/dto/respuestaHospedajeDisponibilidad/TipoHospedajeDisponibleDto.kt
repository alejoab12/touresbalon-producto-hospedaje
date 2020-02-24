package com.touresbalon.producto.hospedaje.dto.respuestaHospedajeDisponibilidad

import java.util.*

class TipoHospedajeDisponibleDto {
    lateinit var idTipoHospedaje: UUID
    lateinit var nombre: String
    lateinit var fechaTipoHospedajeDisponibleDto: List<FechaTipoHospedajeDisponibleDto>
}