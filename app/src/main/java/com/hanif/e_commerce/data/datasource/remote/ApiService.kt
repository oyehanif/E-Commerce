package com.hanif.e_commerce.data.datasource.remote

import com.hanif.e_commerce.data.model.ProductApiModel
import com.hanif.e_commerce.domain.model.ProductEntity
import retrofit2.http.GET

interface ApiService {
    @GET("/products")
    suspend fun getProducts(): List<ProductEntity>
}