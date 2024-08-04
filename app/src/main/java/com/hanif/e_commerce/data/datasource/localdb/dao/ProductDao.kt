package com.hanif.e_commerce.data.datasource.localdb.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.hanif.e_commerce.domain.model.ProductEntity
import kotlinx.coroutines.flow.Flow


@Dao
interface ProductDao {
    @Query("SELECT * FROM products")
    fun getAllProducts(): List<ProductEntity>

    @Query("Delete from products")
    suspend fun clearProductTable()

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(products: List<ProductEntity>)

    @Query("SELECT * FROM products where category= :category")
    fun getDataBaseOnCategory(category: String): List<ProductEntity>

    @Query("SELECT * FROM products WHERE title LIKE '%' || :search || '%' OR CAST(id AS TEXT) = :search")
    fun searchProducts(search: String): List<ProductEntity>

    @Query("SELECT DISTINCT category from products")
    fun getCategories(): List<String>

    @Update
    fun update(order: ProductEntity)

    @Query("SELECT * FROM PRODUCTS WHERE isInsideTheCart==true")
    fun getAddToCartItems() : Flow<List<ProductEntity>>
}