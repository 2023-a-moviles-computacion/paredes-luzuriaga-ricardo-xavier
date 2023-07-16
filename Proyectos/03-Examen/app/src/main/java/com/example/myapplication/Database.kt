package com.example.myapplication
import androidx.room.RoomDatabase
import androidx.room.Database



@Database(entities = [Jugador::class, Equipo::class], version = 1)

abstract class Database : RoomDatabase() {
    abstract fun daoJugador(): DaoJugador
    abstract fun daoEquipo(): DaoEquipo
}
