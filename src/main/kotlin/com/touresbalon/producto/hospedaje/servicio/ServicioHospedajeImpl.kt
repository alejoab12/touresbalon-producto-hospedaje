package com.touresbalon.producto.hospedaje.servicio

import com.datastax.driver.core.LocalDate
import com.touresbalon.producto.hospedaje.componente.UtilidadFecha
import com.touresbalon.producto.hospedaje.dto.FiltroConsultaHospedajeDisponibleDto
import com.touresbalon.producto.hospedaje.dto.RegistroHospedajeDto
import com.touresbalon.producto.hospedaje.dto.respuestaHospedajeDisponibilidad.DisponibilidadHospedajeDto
import com.touresbalon.producto.hospedaje.dto.DisponibilidadHospedajeDto as ParmDisponibilidadHospedajeDto
import com.touresbalon.producto.hospedaje.dto.respuestaHospedajeDisponibilidad.FechaTipoHospedajeDisponibleDto
import com.touresbalon.producto.hospedaje.dto.respuestaHospedajeDisponibilidad.HabitacionDisponibilidadHospedajeDto
import com.touresbalon.producto.hospedaje.dto.respuestaHospedajeDisponibilidad.TipoHospedajeDisponibleDto
import com.touresbalon.producto.hospedaje.entidad.DisponibilidadHospedaje
import com.touresbalon.producto.hospedaje.entidad.Hospedaje
import com.touresbalon.producto.hospedaje.entidad.TipoHospedaje
import com.touresbalon.producto.hospedaje.repositorio.DisponibilidadHospedajeRepositorio
import com.touresbalon.producto.hospedaje.repositorio.HospedajeRepositorio
import com.touresbalon.producto.hospedaje.repositorio.TipoHospedajeRepositorio
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Service
import java.text.SimpleDateFormat
import java.util.*


@Service
class ServicioHospedajeImpl : ServicioHospedaje {

    @Autowired
    lateinit var hospedajeRepositorio: HospedajeRepositorio

    @Autowired
    lateinit var tipoHospedajeRepositorio: TipoHospedajeRepositorio

    @Autowired
    lateinit var disponibilidadHospedajeRepositorio: DisponibilidadHospedajeRepositorio

    var listFecha = mutableListOf<String>()
    lateinit var filtroConsultaHospedajeDisponibleDto: FiltroConsultaHospedajeDisponibleDto

    /**
     * Metodo para registrar una hotel o hospedaje desde un dto o valueObject
     */
    override fun registrarHospedajeDesde(registroHospedajeDto: RegistroHospedajeDto) {
        var hospedaje = Hospedaje()
        hospedaje.id = UUID.randomUUID()
        hospedaje.idCiudad = registroHospedajeDto.idCiudad
        hospedaje.estrellas = registroHospedajeDto.estrellas
        hospedaje.nombre = registroHospedajeDto.nombre
        hospedajeRepositorio.insert(hospedaje)
        registroHospedajeDto.tipoHospedaje.forEach {
            var tipoHospedaje = TipoHospedaje()
            tipoHospedaje.id = UUID.randomUUID()
            tipoHospedaje.idHospedaje = hospedaje.id
            tipoHospedaje.nombre = it.nombre
            tipoHospedaje.nroPersona = it.nroPersona
            tipoHospedaje.orden = it.orden
            tipoHospedajeRepositorio.insert(tipoHospedaje)
        }
    }

    /**
     * Metodo para registrar una disponibilidad de hospedaje
     */
    override fun registrarDisponibilidadHospedajeDesde(disponibilidadHospedajeDto: ParmDisponibilidadHospedajeDto) {
        var disponibilidadHospedaje = DisponibilidadHospedaje()
        disponibilidadHospedaje.id = UUID.randomUUID()
        disponibilidadHospedaje.disponibilidad = disponibilidadHospedajeDto.disponibilidad
        disponibilidadHospedaje.disponible = disponibilidadHospedajeDto.disponible
        disponibilidadHospedaje.fecha = disponibilidadHospedajeDto.fecha.getFechaLocalDate()
        disponibilidadHospedaje.idHospedaje = disponibilidadHospedajeDto.idHospedaje
        disponibilidadHospedaje.idTipoHospedaje = disponibilidadHospedajeDto.idTipoHospedaje
        disponibilidadHospedaje.valorHabitacion = disponibilidadHospedajeDto.valorHabitacion
        disponibilidadHospedajeRepositorio.insert(disponibilidadHospedaje)
    }

    /**
     * Metodo para consultar los hoteles de la ciudad
     */
    override fun consultaHospedajePor(filtroConsultaHospedajeDisponibleDto: FiltroConsultaHospedajeDisponibleDto): ResponseEntity<Any?> {
        this.filtroConsultaHospedajeDisponibleDto = filtroConsultaHospedajeDisponibleDto
        val list = hospedajeRepositorio.consultaHospedajeXCiudad(filtroConsultaHospedajeDisponibleDto.idCiudad)
        var listDisponibilidadHospedajeDto = mutableListOf<DisponibilidadHospedajeDto>()
        val firstOrNull = list.firstOrNull()
        when {
            firstOrNull is Hospedaje -> {
                listFecha = UtilidadFecha.obtenerListaFechaDesde(filtroConsultaHospedajeDisponibleDto.fechaDesde,
                        filtroConsultaHospedajeDisponibleDto.fechaHasta)
                for (hospedaje in list) {
                    val respuesta = consultaTipoXHabitacionHospedaje(hospedaje)
                    listDisponibilidadHospedajeDto.add(respuesta)
                }
            }
            else -> ResponseEntity<Any?>(null, HttpStatus.NO_CONTENT)
        }
        return ResponseEntity<Any?>(listDisponibilidadHospedajeDto, HttpStatus.OK)
    }

    /**
     * Metodo para consultar los tipos de habitaciones de un hotel especifico
     */
    private fun consultaTipoXHabitacionHospedaje(hospedaje: Hospedaje): DisponibilidadHospedajeDto {
        val splitHabitacion = filtroConsultaHospedajeDisponibleDto.personaXHabitacion.split("|")
        var listaHabitacionDisponibilidadHospedajeDto = mutableListOf<HabitacionDisponibilidadHospedajeDto>()
        for (cantPersona in splitHabitacion) {
            var listTipoHospedaje = mutableListOf<TipoHospedaje>()
            when {
                cantPersona.toInt() % 2 == 0 -> {
                    listTipoHospedaje = tipoHospedajeRepositorio.consultarTipoHospedajeDonde(hospedaje.id,
                            cantPersona.toInt()).toMutableList()
                }
                else -> {
                    val peronsaHabitacionAdicional = cantPersona.toInt() + 1
                    listTipoHospedaje = tipoHospedajeRepositorio.consultarTipoHospedajeDonde(hospedaje.id,
                            cantPersona.toInt(), peronsaHabitacionAdicional).toMutableList()
                }
            }

            listaHabitacionDisponibilidadHospedajeDto = validaDisponibilidadDe(listTipoHospedaje,
                    cantPersona,
                    listaHabitacionDisponibilidadHospedajeDto)

        }

        return validaDisponibilidadDe(splitHabitacion,
                hospedaje,
                listaHabitacionDisponibilidadHospedajeDto)
    }

    /**
     * Metodo que valida si existe disponibilidad en las fechas asignadas por tipo de habitacion
     */
    private fun validaDisponibilidadDe(listTipoHospedaje: MutableList<TipoHospedaje>, cantPersona: String,
                                       listaHabitacionDisponibilidadHospedajeDto: MutableList<HabitacionDisponibilidadHospedajeDto>):
            MutableList<HabitacionDisponibilidadHospedajeDto> {
        when {
            listTipoHospedaje.isNotEmpty() -> {
                val listTipoHospedajeDisponibleDto = consultaDisponibilidadDe(listTipoHospedaje)
                if (listTipoHospedajeDisponibleDto.isNotEmpty()) {
                    val habitacionDisponibilidadHospedajeDto = HabitacionDisponibilidadHospedajeDto()
                    habitacionDisponibilidadHospedajeDto.cantidadPersona = cantPersona
                    habitacionDisponibilidadHospedajeDto.disponibilidadTipoHospedaje = listTipoHospedajeDisponibleDto
                    listaHabitacionDisponibilidadHospedajeDto.add(habitacionDisponibilidadHospedajeDto)
                }
            }
        }
        return listaHabitacionDisponibilidadHospedajeDto
    }

    /**
     * Metodo que valida si las habitaciones seleccionadas tienen disponibilidad de hospedaje
     */
    private fun validaDisponibilidadDe(splitHabitacion: List<String>,
                                       hospedaje: Hospedaje,
                                       listaHabitacionDisponibilidadHospedajeDto: MutableList<HabitacionDisponibilidadHospedajeDto>):
            DisponibilidadHospedajeDto {
        var disponibilidadHospedajeDto = DisponibilidadHospedajeDto()
        disponibilidadHospedajeDto.id = hospedaje.id
        disponibilidadHospedajeDto.estrellas = hospedaje.estrellas
        disponibilidadHospedajeDto.nombre = hospedaje.nombre
        when {
            listaHabitacionDisponibilidadHospedajeDto.size.equals(splitHabitacion.size) -> {
                disponibilidadHospedajeDto.disponible = true
                disponibilidadHospedajeDto.tipoHospedajeDisponible = listaHabitacionDisponibilidadHospedajeDto
            }
        }
        return disponibilidadHospedajeDto
    }


    /**
     * Metodo para consultar la disponibilidad por fecha de un tipo de habitacion sobre un hotel especifico
     */
    private fun consultaDisponibilidadDe(listaTipoHospedaje: List<TipoHospedaje>): MutableList<TipoHospedajeDisponibleDto> {
        var listaTipoHospedajeDisponible = mutableListOf<TipoHospedajeDisponibleDto>()
        for (tipoHospedaje in listaTipoHospedaje) {

            val listFechaTipoHospedajeDisponible = mutableListOf<FechaTipoHospedajeDisponibleDto>()
            for (fecha in listFecha) {
                val fechaTipoHospedajeDisponibleDto = FechaTipoHospedajeDisponibleDto()
                val fechaParse = UtilidadFecha.stringADate(fecha)
                val listDisponibilidad = disponibilidadHospedajeRepositorio.consultaDisponibilidadPor(tipoHospedaje.id,
                        LocalDate.fromMillisSinceEpoch(fechaParse.time))
                when {
                    listDisponibilidad.isNotEmpty() -> {
                        fechaTipoHospedajeDisponibleDto.fecha = fecha
                        fechaTipoHospedajeDisponibleDto.listFechaDisponible = listDisponibilidad
                        listFechaTipoHospedajeDisponible.add(fechaTipoHospedajeDisponibleDto)
                    }
                }
            }
            listaTipoHospedajeDisponible = validaFechaTipoHospedajeDisponible(listaTipoHospedajeDisponible,
                    listFechaTipoHospedajeDisponible,
                    tipoHospedaje)

        }
        return listaTipoHospedajeDisponible
    }

    /**
     * Metodo que valida y compara si las fechas registradas equivalen a las fechas recorridas en la disponibilidad
     * TipoHospedaje/Hospedaje
     */
    private fun validaFechaTipoHospedajeDisponible(listaTipoHospedajeDisponible: MutableList<TipoHospedajeDisponibleDto>,
                                           listFechaTipoHospedajeDisponible: MutableList<FechaTipoHospedajeDisponibleDto>,
                                           tipoHospedaje: TipoHospedaje):
            MutableList<TipoHospedajeDisponibleDto> {
        val tipoHospedajeDisponibleDto = TipoHospedajeDisponibleDto()
        when {
            listFechaTipoHospedajeDisponible.size.equals(listFecha.size) -> {
                tipoHospedajeDisponibleDto.idTipoHospedaje = tipoHospedaje.id
                tipoHospedajeDisponibleDto.nombre = tipoHospedaje.nombre
                tipoHospedajeDisponibleDto.fechaTipoHospedajeDisponibleDto = listFechaTipoHospedajeDisponible
                listaTipoHospedajeDisponible.add(tipoHospedajeDisponibleDto)
            }
        }
        return listaTipoHospedajeDisponible
    }

}