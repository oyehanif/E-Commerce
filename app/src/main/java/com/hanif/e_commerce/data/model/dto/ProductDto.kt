package com.hanif.e_commerce.data.model.dto

import com.hanif.e_commerce.data.model.ProductApiModel
import com.hanif.e_commerce.domain.model.ProductEntity

fun ProductApiModel.toProductEntity(isInsideTheCart: Boolean = false, unit: Int = 0) =
    ProductEntity(
        id = id,
        title,
        price,
        description,
        category,
        image,
        isInsideTheCart = isInsideTheCart,
        unit = unit
    )