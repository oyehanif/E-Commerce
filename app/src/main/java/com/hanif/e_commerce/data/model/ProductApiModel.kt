package com.hanif.e_commerce.data.model

data class ProductApiModel(
    val category: String,
    val description: String,
    val  id: Int,
    val image: String,
    val price: Double,
    val rating: Rating?,
    val title: String
)