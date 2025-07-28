package com.coding.data.remote.dto

data class ProductDto(
    val id: Int,
    val title: String,
    val description: String? = null,
    val category: String,
    val price: Double,
    val discountPercentage: Double,
    val rating: Double,
    val stock: Int,
    val brand: String? = null,
    val sku: String? = null,
    val weight: Int? = null,
    val dimensions: DimensionsDto? = null,
    val warrantyInformation: String? = null,
    val shippingInformation: String? = null,
    val availabilityStatus: String? = null,
    val reviews: List<ReviewDto>? = null,
    val returnPolicy: String? = null,
    val minimumOrderQuantity: Int? = null,
    val meta: MetaDto? = null,
    val images: List<String>? = null,
    val thumbnail: String? = null,
    val tags: List<String>? = null
)

data class DimensionsDto(
    val width: Double? = null,
    val height: Double? = null,
    val depth: Double? = null
)

data class ReviewDto(
    val rating: Int? = null,
    val comment: String? = null,
    val date: String? = null,
    val reviewerName: String? = null,
    val reviewerEmail: String? = null
)

data class MetaDto(
    val createdAt: String? = null,
    val updatedAt: String? = null,
    val barcode: String? = null,
    val qrCode: String? = null
)
