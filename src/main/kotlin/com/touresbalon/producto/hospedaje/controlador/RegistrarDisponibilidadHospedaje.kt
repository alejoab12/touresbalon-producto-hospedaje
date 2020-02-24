package com.touresbalon.producto.hospedaje.controlador

import com.touresbalon.producto.hospedaje.dto.DisponibilidadHospedajeDto
import com.touresbalon.producto.hospedaje.dto.RegistroDisponibilidadHospedajeDto
import com.touresbalon.producto.hospedaje.dto.RespuestaDto
import com.touresbalon.producto.hospedaje.servicio.ServicioHospedaje
import com.touresbalon.producto.hospedaje.valueObject.FechaVo
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController

@RestController
class RegistrarDisponibilidadHospedaje {

    @Autowired
    lateinit var servicioHospedaje: ServicioHospedaje

    @PostMapping("producto/hospedaje/disponibilidad")
    fun registrarCiudadDesde(@RequestBody registroDisponibilidadHospedajeDto: RegistroDisponibilidadHospedajeDto): ResponseEntity<Any?> {
        return validaDisponibilidadHospedaje(registroDisponibilidadHospedajeDto)
    }


    /**
     * Metodo que valida los atributos de request sobre #RegistroDto
     * atributos validados
     * ciudad
     */
    private fun validaDisponibilidadHospedaje(registroDisponibilidadHospedajeDto: RegistroDisponibilidadHospedajeDto): ResponseEntity<Any?> {
        var fecha = FechaVo(registroDisponibilidadHospedajeDto.fecha)
        var validaFecha = fecha.validaFecha()
        return when {
            validaFecha is String -> {
                val respuesta = RespuestaDto()
                respuesta.mensaje = validaFecha
                ResponseEntity<Any?>(respuesta, HttpStatus.BAD_REQUEST)
            }
            else -> {
                val disponibilidadHospedajeDto = DisponibilidadHospedajeDto()
                disponibilidadHospedajeDto.disponibilidad = registroDisponibilidadHospedajeDto.disponibilidad
                disponibilidadHospedajeDto.disponible = registroDisponibilidadHospedajeDto.disponible
                disponibilidadHospedajeDto.fecha = fecha
                disponibilidadHospedajeDto.idHospedaje = registroDisponibilidadHospedajeDto.idHospedaje
                disponibilidadHospedajeDto.idTipoHospedaje = registroDisponibilidadHospedajeDto.idTipoHospedaje
                disponibilidadHospedajeDto.valorHabitacion = registroDisponibilidadHospedajeDto.valorHabitacion
                servicioHospedaje.registrarDisponibilidadHospedajeDesde(disponibilidadHospedajeDto)
                ResponseEntity<Any?>(null, HttpStatus.CREATED)
            }
        }
    }
}