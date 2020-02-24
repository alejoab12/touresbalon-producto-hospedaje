package com.touresbalon.producto.hospedaje.controlador

import com.touresbalon.producto.hospedaje.dto.FiltroConsultaHospedajeDisponibleDto
import com.touresbalon.producto.hospedaje.servicio.ServicioHospedaje
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RestController

@RestController
class ConsultaHospedajeDisponibleControlador {

    @Autowired
    lateinit var servicioHospedaje: ServicioHospedaje

    @GetMapping("/producto/hospedaje/{idCiudad}/{fechaDesde}/{fechaHasta}/{personaXHabitacion}")
    fun consultarHospedajeDisponibleCon(@PathVariable idCiudad: String,
                                        @PathVariable fechaDesde: String,
                                        @PathVariable fechaHasta: String,
                                        @PathVariable personaXHabitacion: String): ResponseEntity<Any?>{
        var filtroConsultaHospedajeDisponibleDto = FiltroConsultaHospedajeDisponibleDto()
        filtroConsultaHospedajeDisponibleDto.idCiudad = idCiudad
        filtroConsultaHospedajeDisponibleDto.fechaDesde = fechaDesde
        filtroConsultaHospedajeDisponibleDto.fechaHasta = fechaHasta
        filtroConsultaHospedajeDisponibleDto.personaXHabitacion = personaXHabitacion
        return servicioHospedaje.consultaHospedajePor(filtroConsultaHospedajeDisponibleDto)
    }

}