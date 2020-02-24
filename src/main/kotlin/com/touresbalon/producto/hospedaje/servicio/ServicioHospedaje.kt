package com.touresbalon.producto.hospedaje.servicio

import com.touresbalon.producto.hospedaje.dto.DisponibilidadHospedajeDto
import com.touresbalon.producto.hospedaje.dto.FiltroConsultaHospedajeDisponibleDto
import com.touresbalon.producto.hospedaje.dto.RegistroHospedajeDto
import org.springframework.http.ResponseEntity

interface ServicioHospedaje {

    /**
     * Metodo para registrar una hotel o hospedaje desde un dto o valueObject
     */
    fun registrarHospedajeDesde(registroHospedajeDto: RegistroHospedajeDto)

    /**
     * Metodo para registrar una disponibilidad de hospedaje
     */
    fun registrarDisponibilidadHospedajeDesde(disponibilidadHospedajeDto: DisponibilidadHospedajeDto)

    /**
     * Metodo para consultar los hoteles de la ciudad
     */
    fun consultaHospedajePor(filtroConsultaHospedajeDisponibleDto: FiltroConsultaHospedajeDisponibleDto): ResponseEntity<Any?>
}