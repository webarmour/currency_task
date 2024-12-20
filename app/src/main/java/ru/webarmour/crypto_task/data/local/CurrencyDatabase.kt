package ru.webarmour.crypto_task.data.local

import android.app.Application
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import dagger.hilt.android.qualifiers.ApplicationContext

@Database(entities = [RatesModelDb::class], version = 1, exportSchema = false)
abstract class CurrencyDatabase : RoomDatabase() {


    abstract fun getDao(): CurrencyDao

    companion object {

        private var INSTANCE: CurrencyDatabase? = null
        private val LOCK = Any()
        private val DB_NAME = "favourites_db"


        fun getInstance(
            @ApplicationContext application: Application,
        ): CurrencyDatabase {
            INSTANCE?.let {
                return it
            }
            synchronized(LOCK) {
                INSTANCE?.let {
                    return it
                }
                val db = Room.databaseBuilder(
                    application, CurrencyDatabase::class.java,
                    DB_NAME
                )
                    .fallbackToDestructiveMigration()
                    .build()
                INSTANCE = db
                return db
            }

        }
    }

}