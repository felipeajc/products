package com.coding.data.mapper

import com.coding.data.remote.dto.ProductDto
import com.coding.domain.model.ProductDomainModel

/**
 * Converts a ProductDto (from the network layer) into a ProductDomainModel (used in the domain layer).
 * Keeps domain clean and decoupled from API specifics.
 */
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
