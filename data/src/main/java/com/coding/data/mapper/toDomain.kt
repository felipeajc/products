package com.coding.data.mapper

import com.coding.data.remote.dto.ProductDto
import com.coding.domain.model.ProductDomainModel

fun ProductDto.toDomain(): ProductDomainModel {
    return ProductDomainModel(
        id = id,
        title = title,
        description = description,
        price = price,
        discountPercentage = discountPercentage,
        rating = rating,
        stock = stock,
        brand = brand,
        category = category,
        thumbnail = thumbnail
    )
}
