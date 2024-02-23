package com.products.core.pagination.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.products.core.pagination.dao.ProductDao
import com.products.core.pagination.dao.RemoteKeysDao
import com.products.core.pagination.model.Product
import com.products.core.pagination.model.RemoteKeys


@Database(version = 1, entities = [Product::class, RemoteKeys::class])
abstract class AppDatabase : RoomDatabase() {

    abstract fun getRepoDao(): RemoteKeysDao
    abstract fun getProductDao(): ProductDao

    companion object {

        private const val PRODUCT_DB = "product.db"

        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getInstance(context: Context): AppDatabase =
            INSTANCE ?: synchronized(this) {
                INSTANCE
                    ?: buildDatabase(context).also { INSTANCE = it }
            }

        private fun buildDatabase(context: Context) =
            Room.databaseBuilder(context.applicationContext, AppDatabase::class.java, PRODUCT_DB)
                .build()
    }

}