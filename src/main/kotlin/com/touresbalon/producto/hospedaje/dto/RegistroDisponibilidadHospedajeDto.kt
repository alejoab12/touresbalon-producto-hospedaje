package com.touresbalon.producto.hospedaje.dto

import com.datastax.driver.core.LocalDate
import org.springframework.data.cassandra.core.cql.PrimaryKeyType
import org.springframework.data.cassandra.core.mapping.Column
import org.springframework.data.cassandra.core.mapping.PrimaryKeyColumn
import java.util.*

class RegistroDisponibilidadHospedajeDto {
    lateinit var idHospedaje: UUID
    var disponible: Boolean = false
    lateinit var fecha: String
    var disponibilidad: Int = 0
    lateinit var idTipoHospedaje: UUID
    var valorHabitacion: Float = 0f
}