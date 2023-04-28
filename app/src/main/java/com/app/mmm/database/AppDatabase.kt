package com.app.mmm.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.app.mmm.database.dao.ProductDao
import com.app.mmm.database.entity.ProductStorageItem

@Database(
    entities = [ProductStorageItem::class],
    version = 1,
    exportSchema = true,
)
abstract class AppDatabase : RoomDatabase() {

    abstract fun productDao(): ProductDao

    companion object {
        private const val DB_NAME = "products.db"

        @Volatile
        private var instance: AppDatabase? = null

        operator fun invoke(context: Context): AppDatabase {
            return instance ?: synchronized(AppDatabase::class) {
                instance ?: buildDatabase(context).also { instance = it }
            }
        }

        private fun buildDatabase(context: Context): AppDatabase =
            Room.databaseBuilder(context.applicationContext, AppDatabase::class.java, DB_NAME).build()
    }
}
