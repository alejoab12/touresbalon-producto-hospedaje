package com.touresbalon.producto.hospedaje.entidad

import com.datastax.driver.core.LocalDate
import org.springframework.data.cassandra.core.cql.PrimaryKeyType
import org.springframework.data.cassandra.core.mapping.Column
import org.springframework.data.cassandra.core.mapping.PrimaryKeyColumn
import org.springframework.data.cassandra.core.mapping.Table
import java.util.*

@Table("disponibilidad_hospedaje")
class DisponibilidadHospedaje {
    @Column("id_hospedaje")
    lateinit var idHospedaje: UUID
    @PrimaryKeyColumn(type = PrimaryKeyType.PARTITIONED)
    var disponible: Boolean = false
    @PrimaryKeyColumn(type = PrimaryKeyType.PARTITIONED)
    lateinit var fecha: LocalDate
    @Column
    var disponibilidad: Int = 0
    @Column
    lateinit var id: UUID
    @PrimaryKeyColumn(type = PrimaryKeyType.PARTITIONED, name = "id_tipo_hospedaje")
    lateinit var idTipoHospedaje: UUID
    @Column("valor_habitacion")
    var valorHabitacion: Float = 0f
}