import org.sqlite.SQLiteDataSource

data class Equipo(
    val id: Int,
    var nombreEquipo: String,
    var pais: String,
    var fechaDeFundacion: String,
)

class Jugador(
    val id: Int,
    var nombre: String?,
    var edad: Int?,
    var dorsal: Int?,
    var fechaNacimiento: String?,
    var estatura: Double?,
    var posicion: String?,
    var convocado: Boolean,
    var equipoId: Int
)

class Control_Equipos {
    private val dataSource: SQLiteDataSource

    init {
        dataSource = SQLiteDataSource()
        dataSource.url = "jdbc:sqlite:futbol.db"
        createEquipoTable()
    }

    private fun createEquipoTable() {
        dataSource.connection.use { connection ->
            val statement = connection.createStatement()
            val createTableSQL = """
                CREATE TABLE IF NOT EXISTS equipos (
                    id INTEGER PRIMARY KEY,
                    nombreEquipo TEXT,
                    pais TEXT,
                    fechaDeFundacion TEXT
                );
            """
            statement.executeUpdate(createTableSQL)
        }
    }

    fun existsEquipo(id: Int): Boolean {
        dataSource.connection.use { connection ->
            val selectSQL = "SELECT COUNT(*) FROM equipos WHERE id = ?"
            connection.prepareStatement(selectSQL).use { preparedStatement ->
                preparedStatement.setInt(1, id)
                val resultSet = preparedStatement.executeQuery()
                return resultSet.getInt(1) > 0
            }
        }
    }

    fun create(equipo: Equipo) {
        if (existsEquipo(equipo.id)) {
            println("El identificador del equipo ya está en uso.")
            return
        }
        dataSource.connection.use { connection ->
            val insertSQL = """
                INSERT INTO equipos(id, nombreEquipo, pais, fechaDeFundacion)
                VALUES (?, ?, ?, ?);
            """
            connection.prepareStatement(insertSQL).use { preparedStatement ->
                preparedStatement.setInt(1, equipo.id)
                preparedStatement.setString(2, equipo.nombreEquipo)
                preparedStatement.setString(3, equipo.pais)
                preparedStatement.setString(4, equipo.fechaDeFundacion)
                preparedStatement.executeUpdate()
            }
        }
        println("Equipo creado correctamente.")
    }

    fun readAll(): List<Equipo> {
        val equipos = mutableListOf<Equipo>()
        dataSource.connection.use { connection ->
            val selectSQL = "SELECT * FROM equipos"
            connection.prepareStatement(selectSQL).use { preparedStatement ->
                val resultSet = preparedStatement.executeQuery()
                while (resultSet.next()) {
                    equipos.add(
                        Equipo(
                            resultSet.getInt("id"),
                            resultSet.getString("nombreEquipo"),
                            resultSet.getString("pais"),
                            resultSet.getString("fechaDeFundacion")
                        )
                    )
                }
            }
        }
        return equipos
    }

    fun update(equipo: Equipo) {
        dataSource.connection.use { connection ->
            val updateSQL = """
                UPDATE equipos
                SET nombreEquipo = ?, pais = ?, fechaDeFundacion = ?
                WHERE id = ?;
            """
            connection.prepareStatement(updateSQL).use { preparedStatement ->
                preparedStatement.setString(1, equipo.nombreEquipo)
                preparedStatement.setString(2, equipo.pais)
                preparedStatement.setString(3, equipo.fechaDeFundacion)
                preparedStatement.setInt(4, equipo.id)
                preparedStatement.executeUpdate()
            }
        }
        println("Equipo actualizado correctamente.")
    }

    fun delete(id: Int) {
        dataSource.connection.use { connection ->
            val deleteSQL = "DELETE FROM equipos WHERE id = ?"
            connection.prepareStatement(deleteSQL).use { preparedStatement ->
                preparedStatement.setInt(1, id)
                preparedStatement.executeUpdate()
            }
        }
        println("Equipo eliminado.")
    }
}

class Control_Jugadores {
    private val dataSource: SQLiteDataSource

    init {
        dataSource = SQLiteDataSource()
        dataSource.url = "jdbc:sqlite:futbol.db"
        createJugadorTable()
    }

    private fun createJugadorTable() {
        dataSource.connection.use { connection ->
            val statement = connection.createStatement()
            val createTableSQL = """
                CREATE TABLE IF NOT EXISTS jugadores (
                    id INTEGER PRIMARY KEY,
                    nombre TEXT,
                    edad INTEGER,
                    dorsal INTEGER,
                    fechaNacimiento TEXT,
                    estatura DOUBLE,
                    posicion TEXT,
                    convocado BOOLEAN,
                    equipoId INTEGER,
                    FOREIGN KEY (equipoId) REFERENCES equipos(id)
                );
            """
            statement.executeUpdate(createTableSQL)
        }
    }

    fun existsJugador(id: Int): Boolean {
        dataSource.connection.use { connection ->
            val selectSQL = "SELECT COUNT(*) FROM jugadores WHERE id = ?"
            connection.prepareStatement(selectSQL).use { preparedStatement ->
                preparedStatement.setInt(1, id)
                val resultSet = preparedStatement.executeQuery()
                return resultSet.getInt(1) > 0
            }
        }
    }

    fun create(jugador: Jugador, equipoId: Int) {
        if (existsJugador(jugador.id)) {
            println("El identificador del jugador ya está en uso.")
            return
        }
        dataSource.connection.use { connection ->
            val insertSQL = """
                INSERT INTO jugadores(id, nombre, edad, dorsal, fechaNacimiento, estatura, posicion, convocado, equipoId)
                VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?);
            """
            connection.prepareStatement(insertSQL).use { preparedStatement ->
                preparedStatement.setInt(1, jugador.id)
                preparedStatement.setString(2, jugador.nombre)
                preparedStatement.setInt(3, jugador.edad!!)
                preparedStatement.setInt(4, jugador.dorsal!!)
                preparedStatement.setString(5, jugador.fechaNacimiento)
                preparedStatement.setDouble(6, jugador.estatura!!)
                preparedStatement.setString(7, jugador.posicion)
                preparedStatement.setBoolean(8, jugador.convocado)
                preparedStatement.setInt(9, equipoId)
                preparedStatement.executeUpdate()
            }
        }
        println("Jugador creado correctamente.")
    }

    fun readAll(): List<Jugador> {
        val jugadores = mutableListOf<Jugador>()
        dataSource.connection.use { connection ->
            val selectSQL = "SELECT * FROM jugadores"
            connection.prepareStatement(selectSQL).use { preparedStatement ->
                val resultSet = preparedStatement.executeQuery()
                while (resultSet.next()) {
                    jugadores.add(
                        Jugador(
                            resultSet.getInt("id"),
                            resultSet.getString("nombre"),
                            resultSet.getInt("edad"),
                            resultSet.getInt("dorsal"),
                            resultSet.getString("fechaNacimiento"),
                            resultSet.getDouble("estatura"),
                            resultSet.getString("posicion"),
                            resultSet.getBoolean("convocado"),
                            resultSet.getInt("equipoId")
                        )
                    )
                }
            }
        }
        return jugadores
    }

    fun update(jugador: Jugador) {
        dataSource.connection.use { connection ->
            val updateSQL = """
                UPDATE jugadores
                SET nombre = ?, edad = ?, dorsal = ?, fechaNacimiento = ?, estatura = ?, posicion = ?, convocado = ?
                WHERE id = ?;
            """
            connection.prepareStatement(updateSQL).use { preparedStatement ->
                preparedStatement.setString(1, jugador.nombre)
                preparedStatement.setInt(2, jugador.edad!!)
                preparedStatement.setInt(3, jugador.dorsal!!)
                preparedStatement.setString(4, jugador.fechaNacimiento)
                preparedStatement.setDouble(5, jugador.estatura!!)
                preparedStatement.setString(6, jugador.posicion)
                preparedStatement.setBoolean(7, jugador.convocado)
                preparedStatement.setInt(8, jugador.id)
                preparedStatement.executeUpdate()
            }
        }
        println("Jugador actualizado correctamente.")
    }

    fun delete(id: Int) {
        dataSource.connection.use { connection ->
            val deleteSQL = "DELETE FROM jugadores WHERE id = ?"
            connection.prepareStatement(deleteSQL).use { preparedStatement ->
                preparedStatement.setInt(1, id)
                preparedStatement.executeUpdate()
            }
        }
        println("Jugador eliminado.")
    }
}

    fun requestInt(message: String): Int {
        println(message)
        return readLine()?.toIntOrNull() ?: run {
            println("Por favor, introduce un valor válido.")
            return requestInt(message)
        }
    }

    fun requestDouble(message: String): Double {
        println(message)
        return readLine()?.toDoubleOrNull() ?: run {
            println("Por favor, introduce un valor válido.")
            return requestDouble(message)
        }
    }

    fun requestTeamId(controlEquipos: Control_Equipos): Int {
        val teamId = requestInt("Introduce el ID del equipo:")
        if (!controlEquipos.existsEquipo(teamId)) {
            println("No existe un equipo con el ID proporcionado.")
            return requestTeamId(controlEquipos)
        }
        return teamId
    }

fun main() {
    val controlEquipos = Control_Equipos()
    val controlJugadores = Control_Jugadores()

    while (true) {
        println("--- Seleccione una de las siguientes opciones ---")
        println("1. Equipos")
        println("2. Jugadores")
        println("3. Salir")

        when (readLine()?.toIntOrNull()) {
            1 -> {
                while (true) {
                    println("--- Operaciones CRUD de Equipos ---")
                    println("1. Crear equipo")
                    println("2. Mostrar equipos")
                    println("3. Actualizar equipo")
                    println("4. Eliminar equipo")
                    println("5. Regresar al menú principal")

                    when (readLine()?.toIntOrNull()) {
                        1 -> {
                            println("Introduce el ID del equipo:")
                            val id = readLine()?.toIntOrNull() ?: continue
                            println("Introduce el nombre del equipo:")
                            val nombre = readLine() ?: continue
                            println("Introduce el país del equipo:")
                            val pais = readLine() ?: continue
                            println("Introduce la fecha de fundación del equipo:")
                            val fechaFundacion = readLine() ?: continue

                            val equipo = Equipo(id, nombre, pais, fechaFundacion)
                            controlEquipos.create(equipo)
                        }
                        2 -> {
                            val equipos = controlEquipos.readAll()
                            equipos.forEach { equipo ->
                                println("""
                                    ID: ${equipo.id}
                                    Nombre del Equipo: ${equipo.nombreEquipo}
                                    País: ${equipo.pais}
                                    Fecha de Fundación: ${equipo.fechaDeFundacion}
                                """.trimIndent())
                                println("--------------------------------------")
                            }
                        }
                        3 -> {
                            println("Introduce el ID del equipo a actualizar:")
                            val id = readLine()?.toIntOrNull() ?: continue
                            if (!controlEquipos.existsEquipo(id)) {
                                println("No existe un equipo con el ID proporcionado.")
                                continue
                            }
                            println("Introduce el nuevo nombre del equipo:")
                            val nombre = readLine() ?: continue
                            println("Introduce el nuevo país del equipo:")
                            val pais = readLine() ?: continue
                            println("Introduce la nueva fecha de fundación del equipo:")
                            val fechaFundacion = readLine() ?: continue

                            val equipoActualizado = Equipo(id, nombre, pais, fechaFundacion)
                            controlEquipos.update(equipoActualizado)
                        }
                        4 -> {
                            println("Introduce el ID del equipo a eliminar:")
                            val id = readLine()?.toIntOrNull() ?: continue

                            if (!controlEquipos.existsEquipo(id)) {
                                println("No existe un equipo con el ID proporcionado.")
                                continue
                            }

                            controlEquipos.delete(id)
                        }
                        5 -> break
                        else -> println("Opción no válida. Por favor, intente de nuevo.")
                    }
                }
            }
            2 -> {
                while (true) {
                    println("--- Operaciones CRUD de Jugadores ---")
                    println("1. Crear jugador")
                    println("2. Mostrar jugadores")
                    println("3. Actualizar jugador")
                    println("4. Eliminar jugador")
                    println("5. Regresar al menú principal")

                    when (readLine()?.toIntOrNull()) {
                        1 -> {
                            println("Introduce el ID del equipo al que pertenece el jugador: ")
                            val idEquipo = readLine()?.toIntOrNull() ?: continue
                            if (!controlEquipos.existsEquipo(idEquipo)) {
                                println("No existe un equipo con el ID proporcionado.")
                                continue
                            }

                            println("Introduce el ID del jugador:")
                            val id = readLine()?.toIntOrNull() ?: continue
                            println("Introduce el nombre del jugador:")
                            val nombre = readLine() ?: continue
                            println("Introduce la edad del jugador:")
                            val edad = readLine()?.toIntOrNull() ?: continue
                            println("Introduce el dorsal del jugador:")
                            val dorsal = readLine()?.toIntOrNull() ?: continue
                            println("Introduce la fecha de nacimiento del jugador:")
                            val fechaNacimiento = readLine() ?: continue
                            println("Introduce la estatura del jugador:")
                            val estatura = readLine()?.toDoubleOrNull() ?: continue
                            println("Introduce la posición del jugador:")
                            val posicion = readLine() ?: continue
                            println("¿Está convocado? (true/false):")
                            val convocado = readLine()?.toBooleanStrictOrNull() ?: continue

                            val jugador = Jugador(id, nombre, edad, dorsal, fechaNacimiento, estatura, posicion, convocado, idEquipo)
                            controlJugadores.create(jugador, idEquipo)
                        }
                        2 -> {
                            val jugadores = controlJugadores.readAll()
                            jugadores.forEach { jugador ->
                                println("""
                                    ID: ${jugador.id}
                                    Nombre: ${jugador.nombre}
                                    Edad: ${jugador.edad}
                                    Dorsal: ${jugador.dorsal}
                                    Fecha de Nacimiento: ${jugador.fechaNacimiento}
                                    Estatura: ${jugador.estatura}
                                    Posición: ${jugador.posicion}
                                    Convocado: ${jugador.convocado}
                                    ID Equipo: ${jugador.equipoId}
                                """.trimIndent())
                                println("--------------------------------------")
                            }
                        }
                        3 -> {
                            println("Introduce el ID del equipo al que pertenece el jugador: ")
                            val idEquipo = readLine()?.toIntOrNull() ?: continue
                            if (!controlEquipos.existsEquipo(idEquipo)) {
                                println("No existe un equipo con el ID proporcionado.")
                                continue
                            }
                            println("Introduce el ID del jugador a actualizar:")
                            val id = readLine()?.toIntOrNull() ?: continue

                            if (!controlJugadores.existsJugador(id)) {
                                println("No existe un jugador con el ID proporcionado.")
                                continue
                            }

                            println("Introduce el nuevo nombre del jugador:")
                            val nombre = readLine() ?: continue
                            println("Introduce la nueva edad del jugador:")
                            val edad = readLine()?.toIntOrNull() ?: continue
                            println("Introduce el nuevo dorsal del jugador:")
                            val dorsal = readLine()?.toIntOrNull() ?: continue
                            println("Introduce la nueva fecha de nacimiento del jugador:")
                            val fechaNacimiento = readLine() ?: continue
                            println("Introduce la nueva estatura del jugador:")
                            val estatura = readLine()?.toDoubleOrNull() ?: continue
                            println("Introduce la nueva posición del jugador:")
                            val posicion = readLine() ?: continue
                            println("¿Está convocado? (true/false):")
                            val convocado = readLine()?.toBooleanStrictOrNull() ?: continue

                            val jugadorActualizado = Jugador(id, nombre, edad, dorsal, fechaNacimiento, estatura, posicion, convocado, idEquipo)
                            controlJugadores.update(jugadorActualizado)
                        }
                        4 -> {
                            println("Introduce el ID del jugador a eliminar:")
                            val id = readLine()?.toIntOrNull() ?: continue

                            if (!controlJugadores.existsJugador(id)) {
                                println("No existe un jugador con el ID proporcionado.")
                                continue
                            }

                            controlJugadores.delete(id)
                        }
                        5 -> break
                        else -> println("Opción no válida. Por favor, intente de nuevo.")
                    }
                }
            }
            3 -> {
                println("Saliendo del programa.")
                return
            }
            else -> println("Opción no válida. Por favor, intente de nuevo.")
        }
        println()
    }
}

