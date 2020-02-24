package com.touresbalon.producto.hospedaje.valueObject

import com.datastax.driver.core.LocalDate
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*

class FechaVo(var parm: String) {

    lateinit var fecha: Date

    val REPLACE = "Fecha"
    val FORMATO = "yyyy-MM-dd"
    var ERROR_NO_FECHA = "El parametro $REPLACE no concuerda con el formato aaaa-MM-dd"
    var ERROR_PARAMETRO_VACIO = "El parametro $REPLACE esta vacio"

    /**
     * Metodo que valida si el string contiene numeros
     */
    fun validaFecha(): Any =
            when {
                parm.isEmpty() -> ERROR_PARAMETRO_VACIO
                !parseStringADate() -> ERROR_NO_FECHA
                else -> true
            }

    /**
     * Metodo que parsea el parametro
     */
    private fun parseStringADate(): Boolean{
        var formato = SimpleDateFormat(FORMATO)
        formato.isLenient = false
        try {
            fecha = formato.parse(parm)
            return true
        }catch (e: ParseException){
            return false
        }
    }

    /**
     * metodo que retorna el parametro ajustado a minusculas
     */
    fun getFechaAjustada(): Date = fecha

    /**
     * Metodo que retorna el parametro de fecha ajustado a LocalDate DE CASSANDRA BD
     */
    fun getFechaLocalDate(): LocalDate = LocalDate.fromMillisSinceEpoch(fecha.time)


    /**
     * Metodo que retorna la fecha ajustada pero en string
     */
    override fun toString(): String{
        val formato = SimpleDateFormat(FORMATO)
        return formato.format(fecha)
    }

}