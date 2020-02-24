package com.touresbalon.producto.hospedaje.componente

import java.text.SimpleDateFormat
import java.time.LocalDate
import java.util.*

class UtilidadFecha {

    companion object{

        /**
         * Metodo que obtiene una lista de fechas por medio de un rango deasde y hasta
         */
        fun obtenerListaFechaDesde(fechaDesde: String, fechaHasta: String): MutableList<String>{
            var desde = LocalDate.parse(fechaDesde)
            var hasta = LocalDate.parse(fechaHasta)
            var listFecha = mutableListOf<String>()
            while (!desde.isAfter(hasta)){
                listFecha.add(desde.toString())
                desde = desde.plusDays(1)
            }
            return listFecha
        }

        /**
         * Metodo que parsea un string a date
         */
        fun stringADate(fecha: String): Date = SimpleDateFormat("yyyy-MM-dd").parse(fecha)

    }

}