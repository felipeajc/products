package com.coding.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.coding.data.remote.dto.ProductDto
import com.coding.domain.model.ProductDomainModel
import java.text.Normalizer
import java.util.Locale

@Entity(tableName = "products")
data class ProductEntity(
    @PrimaryKey val id: Int,
    val title: String,
    val description: String,
    val price: Double,
    val discountPercentage: Double,
    val rating: Double,
    val stock: Int,
    val brand: String,
    val category: String,
    val thumbnail: String,
    val searchable: String
)

fun ProductDto.toEntity() = ProductEntity(
    id = id,
    title = title,
    description = description ?: "",
    category = category,
    price = price,
    discountPercentage = discountPercentage,
    rating = rating,
    stock = stock,
    brand = brand ?: "",
    thumbnail = thumbnail ?: "",
    searchable = normalizeText("$title $description")
)

fun ProductEntity.toDomain(): ProductDomainModel {
    return ProductDomainModel(
        id = this.id,
        title = this.title,
        description = this.description,
        category = this.category,
        price = this.price,
        discountPercentage = this.discountPercentage,
        rating = this.rating,
        stock = this.stock,
        brand = this.brand,
        thumbnail = this.thumbnail
    )
}

fun normalizeText(input: String): String {
    return Normalizer.normalize(input, Normalizer.Form.NFD)
        .replace("[\\p{InCombiningDiacriticalMarks}]".toRegex(), "")
        .lowercase(Locale.getDefault())
        .replace("[^a-z0-9\\s]".toRegex(), "")
        .replace("\\s+".toRegex(), " ")
        .trim()
}
