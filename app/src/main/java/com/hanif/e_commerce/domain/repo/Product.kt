package com.hanif.e_commerce.domain.repo

import com.hanif.e_commerce.domain.model.ProductEntity
import com.hanif.e_commerce.utils.NetworkResult
import kotlinx.coroutines.flow.Flow

interface Product {
    fun getAllProducts():  Flow<NetworkResult<List<ProductEntity>>>
    fun getProductByCategory(category: String): Flow<List<ProductEntity>>
    fun searchProducts(search: String): Flow<List<ProductEntity>>
    fun getCategories(): Flow<List<String>>
    fun getAddToCartItems(): Flow<List<ProductEntity>>
    fun addOrUpdateToCart(model: ProductEntity): Flow<List<ProductEntity>>
}