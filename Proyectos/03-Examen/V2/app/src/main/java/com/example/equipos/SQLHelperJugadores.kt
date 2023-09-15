package com.example.equipos

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.util.Log

class SQLHelperJugadores(
    contexto: Context?
) : SQLiteOpenHelper(
    contexto,
    "jugadores.db",
    null,
    1
) {
    override fun onCreate(db: SQLiteDatabase?) {
        Log.d("Database", "Creating JUGADOR table")
        val scriptSQLCrearTablaJugador =
            """
            CREATE TABLE JUGADOR (
            id INTEGER PRIMARY KEY AUTOINCREMENT,
            nombre VARCHAR(50),
            edad INT,
            dorsal INT,
            fechaNacimiento VARCHAR(50),
            estatura REAL,
            posicion VARCHAR(50),
            convocado BOOLEAN,
            foranea INT
        )
            """.trimIndent()
        db?.execSQL(scriptSQLCrearTablaJugador)
        Log.d("Database", "JUGADOR table created")
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {}

    fun crearJugador(
        nombre: String,
        edad: Int,
        dorsal: Int,
        fechaNacimiento: String,
        estatura: Double,
        posicion: String,
        convocado: Boolean,
        foranea: Int
    ): Boolean {
        val basedatosEscritura = writableDatabase
        val valoresAGuardar = ContentValues()
        valoresAGuardar.put("nombre", nombre)
        valoresAGuardar.put("edad", edad)
        valoresAGuardar.put("dorsal", dorsal)
        valoresAGuardar.put("fechaNacimiento", fechaNacimiento)
        valoresAGuardar.put("estatura", estatura)
        valoresAGuardar.put("posicion", posicion)
        valoresAGuardar.put("convocado", convocado)
        valoresAGuardar.put("foranea", foranea)

        val resultadoGuardar = basedatosEscritura
            .insert(
                "JUGADOR", // Nombre tabla
                null,
                valoresAGuardar // valores
            )
        basedatosEscritura.close()
        return resultadoGuardar.toInt() != -1
    }

    fun eliminarJugadorFormulario(id: Int): Boolean {
        val conexionEscritura = writableDatabase
        val parametrosConsultaDelete = arrayOf(id.toString())
        val resultadoEliminacion = conexionEscritura
            .delete(
                "JUGADOR", // Nombre tabla
                "id=?", // Consulta Where
                parametrosConsultaDelete
            )
        conexionEscritura.close()
        return resultadoEliminacion.toInt() != -1
    }

    fun actualizarJugadorFormulario(
        nombre: String,
        edad: Int,
        dorsal: Int,
        fechaNacimiento: String,
        estatura: Double,
        posicion: String,
        convocado: Boolean,
        foranea: Int,
        id: Int
    ): Boolean {
        val conexionEscritura = writableDatabase
        val valoresAActualizar = ContentValues()
        valoresAActualizar.put("nombre", nombre)
        valoresAActualizar.put("edad", edad)
        valoresAActualizar.put("dorsal", dorsal)
        valoresAActualizar.put("fechaNacimiento", fechaNacimiento)
        valoresAActualizar.put("estatura", estatura)
        valoresAActualizar.put("posicion", posicion)
        valoresAActualizar.put("convocado", convocado)
        valoresAActualizar.put("foranea", foranea)

        val parametrosConsultaActualizar = arrayOf(id.toString())
        val resultadoActualizacion = conexionEscritura
            .update(
                "JUGADOR", // Nombre tabla
                valoresAActualizar, // Valores
                "id=?", // Consulta Where
                parametrosConsultaActualizar
            )
        conexionEscritura.close()
        return resultadoActualizacion.toInt() != -1
    }
    fun consultarJugadorPorID(id: Int): Jugador {
        val baseDatosLectura = readableDatabase
        val scriptConsultaLectura =
            """
            SELECT * FROM JUGADOR WHERE ID = ?
            """.trimIndent()
        val parametrosConsultaLectura = arrayOf(id.toString())
        val resultadoConsultaLectura = baseDatosLectura.rawQuery(
            scriptConsultaLectura,
            parametrosConsultaLectura,
        )

        val existeJugador = resultadoConsultaLectura.moveToFirst()
        val jugadorEncontrado = Jugador(0,"",0,0,"",0.0,"",false,0)
        val arreglo = arrayListOf<Jugador>()
        if (existeJugador) {
            do {
                var id = resultadoConsultaLectura.getInt(0)
                var nombre = resultadoConsultaLectura.getString(1)
                var edad = resultadoConsultaLectura.getInt(2)
                var dorsal = resultadoConsultaLectura.getInt(3)
                var fechaNacimiento = resultadoConsultaLectura.getString(4)
                var estatura = resultadoConsultaLectura.getDouble(5)
                var posicion = resultadoConsultaLectura.getString(6)
                var convocado = resultadoConsultaLectura.getString(7)
                var foranea = resultadoConsultaLectura.getInt(8)
                if(id != null) {
                    jugadorEncontrado.id = id
                    jugadorEncontrado.nombre = nombre
                    jugadorEncontrado.edad = edad
                    jugadorEncontrado.dorsal = dorsal
                    jugadorEncontrado.fechaNacimiento = fechaNacimiento
                    jugadorEncontrado.estatura = estatura
                    jugadorEncontrado.posicion = posicion
                    jugadorEncontrado.convocado = convocado.toBoolean()
                    jugadorEncontrado.foranea = foranea
                }
            } while (resultadoConsultaLectura.moveToNext())
        }

        resultadoConsultaLectura.close()
        baseDatosLectura.close()
        return jugadorEncontrado
    }

    fun consultarJugadoresPorForanea(foraneaValue: Int): ArrayList<Jugador> {
        val baseDatosLectura = readableDatabase
        val scriptConsultaLectura = """
        SELECT * FROM JUGADOR WHERE FORANEA = ?
         """.trimIndent()
        val parametrosConsultaLectura = arrayOf(foraneaValue.toString())
        val resultadoConsultaLectura = baseDatosLectura.rawQuery(
            scriptConsultaLectura,
            parametrosConsultaLectura,
        )

        val jugadorList = ArrayList<Jugador>()

        if (resultadoConsultaLectura.moveToFirst()) {
            do {
                val id = resultadoConsultaLectura.getInt(0)
                val nombre = resultadoConsultaLectura.getString(1)
                val edad = resultadoConsultaLectura.getInt(2)
                val dorsal = resultadoConsultaLectura.getInt(3)
                val fechaNacimiento = resultadoConsultaLectura.getString(4)
                val estatura = resultadoConsultaLectura.getDouble(5)
                val posicion = resultadoConsultaLectura.getString(6)
                val convocado = resultadoConsultaLectura.getInt(7) != 0
                val foranea = resultadoConsultaLectura.getInt(8)

                val jugador = Jugador(id, nombre, edad, dorsal, fechaNacimiento, estatura, posicion, convocado, foranea)
                jugadorList.add(jugador)
            } while (resultadoConsultaLectura.moveToNext())
        }

        resultadoConsultaLectura.close()
        baseDatosLectura.close()

        return jugadorList
    }


}



