package com.example.deberricardoparedes
import androidx.room.Database
import androidx.room.RoomDatabase
@Database(
    entities = [Jugador::class],
    version=1
)

abstract class Database: RoomDatabase(){
    abstract fun daoJugador(): DaoJugador
}