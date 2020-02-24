package com.touresbalon.producto.hospedaje.entidad

import org.springframework.data.cassandra.core.cql.PrimaryKeyType
import org.springframework.data.cassandra.core.mapping.Column
import org.springframework.data.cassandra.core.mapping.PrimaryKeyColumn
import org.springframework.data.cassandra.core.mapping.Table
import java.util.*

@Table("tipo_hospedaje")
class TipoHospedaje {
    @PrimaryKeyColumn(name = "nro_persona", type = PrimaryKeyType.PARTITIONED)
    var nroPersona: Int = 0
    @PrimaryKeyColumn(name = "id_hospedaje", type = PrimaryKeyType.PARTITIONED)
    lateinit var idHospedaje: UUID
    @PrimaryKeyColumn(type = PrimaryKeyType.CLUSTERED)
    var orden: Int = 0
    @Column
    lateinit var id: UUID
    @Column
    lateinit var nombre: String
}