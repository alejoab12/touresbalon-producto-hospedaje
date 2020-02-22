package com.touresbalon.producto.hospedaje.repositorio

import com.touresbalon.producto.hospedaje.entidad.Hospedaje
import org.springframework.data.cassandra.repository.CassandraRepository
import org.springframework.stereotype.Repository

@Repository
interface HospedajeRepositorio: CassandraRepository<Hospedaje, String> {
}