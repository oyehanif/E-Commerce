package com.hanif.e_commerce.data.datasource.localdb

import androidx.room.Database
import androidx.room.RoomDatabase
import com.hanif.e_commerce.data.datasource.localdb.dao.ProductDao
import com.hanif.e_commerce.data.model.ProductApiModel
import com.hanif.e_commerce.domain.model.ProductEntity

@Database(entities = [ProductEntity::class], version = 1, exportSchema = false)
abstract class MyDatabase : RoomDatabase() {

    abstract fun productDao(): ProductDao
}