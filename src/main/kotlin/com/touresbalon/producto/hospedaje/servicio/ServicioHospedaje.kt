package com.touresbalon.producto.hospedaje.servicio

import com.touresbalon.producto.hospedaje.dto.RegistroHospedajeDto

interface ServicioHospedaje {

    /**
     * Metodo para registrar una hotel o hospedaje desde un dto o valueObject
     */
    fun registrarHospedajeDesde(registroHospedajeDto: RegistroHospedajeDto)
}