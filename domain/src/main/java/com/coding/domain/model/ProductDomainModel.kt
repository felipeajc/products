package com.coding.domain.model

data class ProductDomainModel(
    val id: Int,
    val title: String,
    val description: String?,
    val price: Double,
    val discountPercentage: Double,
    val rating: Double,
    val stock: Int?,
    val brand: String?,
    val category: String,
    val thumbnail: String?
)
