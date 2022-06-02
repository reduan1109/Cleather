package com.example.cleather.database.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.cleather.database.data.clothes.Clothes
import com.example.cleather.database.data.clothes.ClothesDao
import com.example.cleather.database.data.clothes.Converter

/* class that contains the database holder and serve as the main access point for the underlying
 * connection to our apps persisted, relational data. exportSchema is set to true, check
 * Project -> app -> schemas
 */
@Database(entities = [Clothes::class], version = 1, exportSchema = true)
@TypeConverters(Converter::class) // TypeConverters is used for the enum-classes
abstract class CleatherDatabase: RoomDatabase() {

    abstract fun clothesDao(): ClothesDao

    companion object {
        @Volatile
        private var INSTANCE: CleatherDatabase? = null

        fun getDatabase(context: Context): CleatherDatabase {
            val tempInstance = INSTANCE
            if (tempInstance != null) {
                return tempInstance
            }

            // createFromAsset() is used to get the default values in the database
            synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    CleatherDatabase::class.java,
                    "cleather_database"
                ).createFromAsset("database/cleather_database.db").build()
                INSTANCE = instance
                return instance
            }
        }
    }
}