package com.example.equipos

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.util.Log

class SQLHelperEquipo (
        contexto: Context?
    ) : SQLiteOpenHelper(
        contexto,
        "moviles",
        null,
        1
    ) {

        override fun onCreate(db: SQLiteDatabase?) {
            Log.d("Database", "Creating equipo table")

            val scriptSQLCrearTablaEquipo =
                """
               CREATE TABLE EQUIPO (
                id INTEGER PRIMARY KEY AUTOINCREMENT,
                nombreEquipo VARCHAR(50),
                pais VARCHAR(50),
                fechaDeFundacion VARCHAR(50)
            )
 
            """.trimIndent()
            db?.execSQL(scriptSQLCrearTablaEquipo)
            Log.d("Database", "created equipo table")

        }

        override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {}

        fun crearEquipo(
            nombreEquipo: String,
            pais: String,
            fechaDeFundacion: String,
            jugadores: ArrayList<Jugador>
        ): Boolean {
            val basedatosEscritura = writableDatabase
            val valoresAGuardar = ContentValues()
            valoresAGuardar.put("nombreEquipo", nombreEquipo)
            valoresAGuardar.put("pais", pais)
            valoresAGuardar.put("fechaDeFundacion", fechaDeFundacion)
            val resultadoGuardar = basedatosEscritura.insert(
                "EQUIPO",
                null,
                valoresAGuardar
            )
            basedatosEscritura.close()
            return resultadoGuardar.toInt() != -1
        }

        fun eliminarEquipoFormulario(id: Int): Boolean {
            val conexionEscritura = writableDatabase
            val parametrosConsultaDelete = arrayOf(id.toString())
            val resultadoEliminacion = conexionEscritura.delete(
                "EQUIPO",
                "id=?",
                parametrosConsultaDelete
            )
            conexionEscritura.close()
            return resultadoEliminacion.toInt() != -1
        }

        fun actualizarEquipoFormulario(
            nombreEquipo: String,
            pais: String,
            fechaDeFundacion: String,
            id: Int
        ): Boolean {
            val conexionEscritura = writableDatabase
            val valoresAActualizar = ContentValues()
            valoresAActualizar.put("nombreEquipo", nombreEquipo)
            valoresAActualizar.put("pais", pais)
            valoresAActualizar.put("fechaDeFundacion", fechaDeFundacion)
            val parametrosConsultaActualizar = arrayOf(id.toString())
            val resultadoActualizacion = conexionEscritura.update(
                "EQUIPO",
                valoresAActualizar,
                "id=?",
                parametrosConsultaActualizar
            )
            conexionEscritura.close()
            return resultadoActualizacion.toInt() != -1
        }

        fun consultarEquipoPorID(id: Int): Equipo {
            val baseDatosLectura = readableDatabase
            val scriptConsultaLectura =
                """
                SELECT * FROM EQUIPO WHERE id = ?
                """.trimIndent()
            val parametrosConsultaLectura = arrayOf(id.toString())
            val resultadoConsultaLectura = baseDatosLectura.rawQuery(
                scriptConsultaLectura, //Consulta
                parametrosConsultaLectura //Parámetros
            )

            val existeEquipo = resultadoConsultaLectura.moveToFirst()
            val equipoEncontrado = Equipo(0, "", "", "", ArrayList())
            if (existeEquipo) {
                do {
                    val id = resultadoConsultaLectura.getInt(0)
                    val nombreEquipo = resultadoConsultaLectura.getString(1)
                    val pais = resultadoConsultaLectura.getString(2)
                    val fechaDeFundacion = resultadoConsultaLectura.getString(3)
                    //val Jugadores = resultadoConsultaLectura.getString(4)
                    equipoEncontrado.id = id
                    equipoEncontrado.nombreEquipo = nombreEquipo
                    equipoEncontrado.pais = pais
                    equipoEncontrado.fechaDeFundacion = fechaDeFundacion
                    //equipoEncontrado.jugadores = jugadores
                } while (resultadoConsultaLectura.moveToNext())
            }
            resultadoConsultaLectura.close()
            baseDatosLectura.close()
            return equipoEncontrado
        }

        fun retrieveAllEquipos(): ArrayList<Equipo> {
            val baseDatosLectura = readableDatabase
            val scriptConsultaLectura =
                """
                    SELECT * FROM EQUIPO
                """.trimIndent()

            val resultadoConsultaLectura = baseDatosLectura.rawQuery(
                scriptConsultaLectura,
                null
            )

            val equipoList = ArrayList<Equipo>()

            if (resultadoConsultaLectura.moveToFirst()) {
                do {
                    val id = resultadoConsultaLectura.getInt(0)
                    val nombreEquipo = resultadoConsultaLectura.getString(1)
                    val pais = resultadoConsultaLectura.getString(2)
                    val fechaDeFundacion = resultadoConsultaLectura.getString(3)

                    val equipo = Equipo(id, nombreEquipo, pais, fechaDeFundacion, ArrayList())
                    equipoList.add(equipo)
                } while (resultadoConsultaLectura.moveToNext())
            }

            resultadoConsultaLectura.close()
            baseDatosLectura.close()

            return equipoList
        }

}