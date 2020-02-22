package com.touresbalon.producto.hospedaje.controlador

import com.touresbalon.producto.hospedaje.dto.RegistroHospedajeDto
import com.touresbalon.producto.hospedaje.servicio.ServicioHospedaje
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController

@RestController
class RegistrarHospedajeControlador {

    @Autowired
    lateinit var servicioHospedaje: ServicioHospedaje

    @PostMapping("producto/hospedaje")
    fun registrarHospedajeDesde(@RequestBody registroHospedajeDto: RegistroHospedajeDto): ResponseEntity<Any?> {
        servicioHospedaje.registrarHospedajeDesde(registroHospedajeDto)
        return ResponseEntity<Any?>(null, HttpStatus.CREATED)
    }

}