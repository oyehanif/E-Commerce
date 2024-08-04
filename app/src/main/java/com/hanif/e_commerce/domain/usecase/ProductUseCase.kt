package com.hanif.e_commerce.domain.usecase

import com.hanif.e_commerce.data.model.ProductApiModel
import com.hanif.e_commerce.domain.model.ProductEntity
import com.hanif.e_commerce.domain.repo.Product
import com.hanif.e_commerce.presentation.products.Category
import com.hanif.e_commerce.utils.NetworkResult
import kotlinx.coroutines.flow.Flow

class ProductUseCase(
    private val repository: Product
) {
    fun getAllProducts(): Flow<NetworkResult<List<ProductEntity>>> {
        return repository.getAllProducts()
    }

    fun getProductByCategory(category: String): Flow<List<ProductEntity>> {
        return repository.getProductByCategory(category)
    }

    fun searchProducts(search: String): Flow<List<ProductEntity>> {
        return repository.searchProducts(search)
    }

    fun getCategorys(): Flow<List<String>> {
        return repository.getCategories()
    }

    fun addOrUpdateModel(model: ProductEntity) : Flow<List<ProductEntity>> {
        return repository.addOrUpdateToCart(model)
    }

    fun getAddToCardItems():Flow<List<ProductEntity>> {
        return repository.getAddToCartItems()
    }

}