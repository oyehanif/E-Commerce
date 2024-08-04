package com.hanif.e_commerce.presentation.products

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.toMutableStateList
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hanif.e_commerce.domain.model.ProductEntity
import com.hanif.e_commerce.domain.usecase.ProductUseCase
import com.hanif.e_commerce.utils.NetworkResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

data class Category(val name: String, var isSelection: Boolean)

@HiltViewModel
class ProductViewModel @Inject constructor(
    private val productUseCases: ProductUseCase
) : ViewModel() {


    var productsList = mutableStateListOf<ProductEntity>()
    var addToCardList = mutableStateListOf<ProductEntity>()
    var searchText by mutableStateOf("")
    var categoryList = mutableStateListOf<Category>()
    var isLoading by mutableStateOf(false)
    var isError by mutableStateOf("")

    init {
        getValues()
        getAddToCardItems()
    }

    fun searchItems() {
        viewModelScope.launch(Dispatchers.IO) {
            productUseCases.searchProducts(searchText).collect {
                productsList.clear()
                productsList.addAll(it)
            }
        }
    }

    fun changeCategory(category: Category) {
        viewModelScope.launch(Dispatchers.IO) {
            productUseCases.getProductByCategory(category.name).collect {
                productsList.clear()
                productsList.addAll(it)
            }
        }
        categoryList = categoryList.map { item ->
            item.copy(isSelection = category == item)
        }.toMutableStateList()
    }

        fun getValues() {
        viewModelScope.launch(Dispatchers.IO) {
            productUseCases.getAllProducts().collect{
                when(it){
                    is NetworkResult.Error -> {
                        isError = it.message!!
                        isLoading = false
                    }
                    is NetworkResult.Loading -> {
                        isLoading = true
                    }
                    is NetworkResult.Success -> {
                        isError = ""
                        productsList.addAll(it.data!!)
                        productUseCases.getCategorys().onEach {
                            categoryList.add(Category("All", true))
                            it.forEach {
                                categoryList.add(Category(it, false))
                            }
                        }.collect()
                        isLoading = false
                    }
                }
            }
            productUseCases.getAllProducts().onEach {

            }.collect()
//            productUseCases.getCategorys().onEach {
//                categoryList.add(Category("All", true))
//                it.forEach {
//                    categoryList.add(Category(it, false))
//                }
//            }.collect()
        }
    }

    fun updateModel(model: ProductEntity) {
        viewModelScope.launch {
            productUseCases.addOrUpdateModel(model).collect {
                updateProductList(it)
            }
        }
    }


    fun updateProductList(list: List<ProductEntity>) {
        productsList.clear()
        productsList.addAll(list)
    }

    fun getAddToCardItems() {
        viewModelScope.launch(Dispatchers.IO) {
            productUseCases.getAddToCardItems().collect {
                addToCardList.clear()
                addToCardList.addAll(it)
            }
        }
    }
}
