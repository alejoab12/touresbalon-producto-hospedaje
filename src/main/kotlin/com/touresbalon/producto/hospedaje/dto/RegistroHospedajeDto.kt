package com.touresbalon.producto.hospedaje.dto

class RegistroHospedajeDto {
    lateinit var idCiudad: String
    var estrellas: Int = 0
    lateinit var nombre: String
    lateinit var tipoHospedaje: List<RegistroTipoHospedajeDto>
}