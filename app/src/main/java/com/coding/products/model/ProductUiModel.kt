package com.coding.products.model

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material.icons.filled.Star
import androidx.compose.ui.graphics.vector.ImageVector
import com.coding.domain.model.ProductDomainModel

data class ProductUiModel(
    val id: Int,
    val title: String,
    val description: String?,
    val priceFormatted: String,
    val discountFormatted: String,
    val rating: Double,
    val ratingText: String,
    val ratingIcon: ImageVector,
    val stockText: String,
    val thumbnailUrl: String?
)

fun ProductDomainModel.toUiModel(): ProductUiModel {
    val icon = when {
        rating < 3 -> Icons.Default.KeyboardArrowDown
        rating < 4 -> Icons.Default.KeyboardArrowUp
        else -> Icons.Default.Star
    }

    return ProductUiModel(
        id = id,
        title = title,
        description = description,
        priceFormatted = "R$ %.2f".format(price),
        discountFormatted = "%.0f%% OFF".format(discountPercentage),
        rating = rating,
        ratingText = "⭐ %.1f".format(rating),
        ratingIcon = icon,
        stockText = "Estoque: $stock unidades",
        thumbnailUrl = thumbnail
    )
}
