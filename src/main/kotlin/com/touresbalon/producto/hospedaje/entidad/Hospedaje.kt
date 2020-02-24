package com.touresbalon.producto.hospedaje.entidad

import org.springframework.data.cassandra.core.cql.PrimaryKeyType
import org.springframework.data.cassandra.core.mapping.Column
import org.springframework.data.cassandra.core.mapping.PrimaryKeyColumn
import org.springframework.data.cassandra.core.mapping.Table
import java.util.*

@Table("hospedaje")
class Hospedaje {
    @Column
    lateinit var id: UUID
    @PrimaryKeyColumn(type = PrimaryKeyType.PARTITIONED, name = "id_ciudad")
    lateinit var idCiudad: String
    @Column
    var estrellas: Int = 0
    @Column
    lateinit var nombre: String
}