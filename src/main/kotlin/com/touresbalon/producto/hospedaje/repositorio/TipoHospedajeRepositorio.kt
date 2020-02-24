package com.touresbalon.producto.hospedaje.repositorio

import com.touresbalon.producto.hospedaje.entidad.TipoHospedaje
import org.springframework.data.cassandra.repository.CassandraRepository
import org.springframework.data.cassandra.repository.Query
import org.springframework.stereotype.Repository
import java.util.*

@Repository
interface TipoHospedajeRepositorio: CassandraRepository<TipoHospedaje, String> {

    @Query("SELECT * FROM tipo_hospedaje WHERE id_hospedaje = :idHospedaje AND nro_persona IN (:nroPersonaUno,:nroPersonaDos)")
    fun consultarTipoHospedajeDonde(idHospedaje: UUID, nroPersonaUno: Int, nroPersonaDos: Int): List<TipoHospedaje>

    @Query("SELECT * FROM tipo_hospedaje WHERE id_hospedaje = :idHospedaje AND nro_persona = :nroPersona")
    fun consultarTipoHospedajeDonde(idHospedaje: UUID, nroPersona: Int): List<TipoHospedaje>

}