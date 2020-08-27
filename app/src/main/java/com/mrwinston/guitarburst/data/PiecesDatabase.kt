package com.mrwinston.guitarburst.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.mrwinston.guitarburst.data.model.Piece

@Database(entities = [Piece::class], version = 2)
public abstract class PiecesDatabase: RoomDatabase() {
    abstract fun PiecesDao(): PiecesDao

    companion object {
        @Volatile
        private var INSTANCE: PiecesDatabase? = null

        fun getDatabase(context: Context): PiecesDatabase {
            val tempInstance = INSTANCE
            if (tempInstance != null) {
                return tempInstance
            }
            synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    PiecesDatabase::class.java, "pieces_database"
                )
                    .fallbackToDestructiveMigration()
                    .createFromAsset("pieces.db")
                    .build()
                INSTANCE = instance
                return instance
            }
        }
    }
}