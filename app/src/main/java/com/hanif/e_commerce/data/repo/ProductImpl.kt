package com.hanif.e_commerce.data.repo

import com.hanif.e_commerce.data.datasource.localdb.dao.ProductDao
import com.hanif.e_commerce.data.datasource.remote.ApiService
import com.hanif.e_commerce.domain.model.ProductEntity
import com.hanif.e_commerce.domain.repo.Product
import com.hanif.e_commerce.utils.NetworkResult
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class ProductImpl @Inject constructor(
    private val apiService: ApiService,
    private val productDao: ProductDao
) : Product {

    override fun getAllProducts(): Flow<NetworkResult<List<ProductEntity>>> = flow {
        emit(NetworkResult.Loading())
        try {
            productDao.clearProductTable()
            val products = apiService.getProducts()
            productDao.insertAll(products)
            emit(NetworkResult.Success(productDao.getAllProducts()))
        } catch (e: Exception) {
            emit(NetworkResult.Error("Unable to get data. Please check your Internet connection."))
        }
    }

    override fun getProductByCategory(category: String): Flow<List<ProductEntity>> = flow {
        val products = if (category == "All") {
            productDao.getAllProducts()
        } else {
            productDao.getDataBaseOnCategory(category)
        }
        emit(products)
    }

    override fun searchProducts(search: String): Flow<List<ProductEntity>> = flow {
        val products = if (search.isEmpty()) {
            productDao.getAllProducts()
        } else {
            productDao.searchProducts(search)
        }
        emit(products)
    }

    override fun getCategories(): Flow<List<String>> = flow {
        emit(productDao.getCategories())
    }

    override fun addOrUpdateToCart(product: ProductEntity): Flow<List<ProductEntity>> = flow {
        productDao.update(product)
        emit(productDao.getAllProducts())
    }

    override fun getAddToCartItems(): Flow<List<ProductEntity>> = productDao.getAddToCartItems()
}