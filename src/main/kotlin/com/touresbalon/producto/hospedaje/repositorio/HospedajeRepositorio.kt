package com.touresbalon.producto.hospedaje.repositorio

import com.touresbalon.producto.hospedaje.entidad.Hospedaje
import org.springframework.data.cassandra.repository.CassandraRepository
import org.springframework.data.cassandra.repository.Query
import org.springframework.stereotype.Repository

@Repository
interface HospedajeRepositorio : CassandraRepository<Hospedaje, String> {

    @Query("SELECT * FROM hospedaje WHERE id_ciudad = :idCiudad")
    fun consultaHospedajeXCiudad(idCiudad: String): List<Hospedaje>

}