package com.touresbalon.producto.hospedaje.servicio

import com.touresbalon.producto.hospedaje.dto.RegistroHospedajeDto
import com.touresbalon.producto.hospedaje.entidad.Hospedaje
import com.touresbalon.producto.hospedaje.repositorio.HospedajeRepositorio
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import java.util.*

@Service
class ServicioHospedajeImpl: ServicioHospedaje {

    @Autowired
    lateinit var hospedajeRepositorio: HospedajeRepositorio

    /**
     * Metodo para registrar una hotel o hospedaje desde un dto o valueObject
     */
    override fun registrarHospedajeDesde(registroHospedajeDto: RegistroHospedajeDto) {
        var hospedaje = Hospedaje()
        hospedaje.id = UUID.randomUUID().toString()
        hospedaje.idCiudad = registroHospedajeDto.idCiudad
        hospedaje.estrellas = registroHospedajeDto.estrellas
        hospedaje.nombre = registroHospedajeDto.nombre
        hospedajeRepositorio.insert(hospedaje)
    }

}