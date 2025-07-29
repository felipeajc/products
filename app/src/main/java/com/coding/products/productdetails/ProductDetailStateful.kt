package com.coding.products.productdetails

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImagePainter
import coil.compose.SubcomposeAsyncImage
import coil.compose.SubcomposeAsyncImageContent
import coil.request.CachePolicy
import coil.request.ImageRequest
import com.coding.products.R
import com.coding.products.model.ProductUiModel

@Composable
fun ProductDetailStateful(
    productId: Int,
    viewModel: ProductDetailViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsState()
    val context = LocalContext.current

    LaunchedEffect(productId) {
        val errorMessage = context.getString(R.string.error_loading_products)
        viewModel.loadProduct(errorMessage, productId)
    }

    when {
        uiState.isLoading -> {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator()
            }
        }

        uiState.errorMessage != null -> {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Text(uiState.errorMessage ?: stringResource(R.string.error_unknown))
            }
        }

        uiState.product != null -> {
            ProductDetailStateless(product = uiState.product!!)
        }
    }
}

@Composable
fun ProductDetailStateless(product: ProductUiModel) {
    val scrollState = rememberScrollState()
    val context = LocalContext.current
    val config = LocalConfiguration.current
    val density = LocalDensity.current

    val screenHeight = config.screenHeightDp.dp
    val imageHeightPx = with(density) { (screenHeight * 0.4f).roundToPx() }

    val screenWidth = config.screenWidthDp.dp
    val imageWidthPx = with(density) { screenWidth.roundToPx() }

    val imageRequest = ImageRequest.Builder(context)
        .data(product.thumbnailUrl)
        .size(imageWidthPx, imageHeightPx)
        .crossfade(true)
        .memoryCachePolicy(CachePolicy.ENABLED)
        .diskCachePolicy(CachePolicy.ENABLED)
        .build()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(scrollState)
    ) {
        SubcomposeAsyncImage(
            model = imageRequest,
            contentDescription = stringResource(R.string.product_image_content_description),
            contentScale = ContentScale.Fit,
            modifier = Modifier
                .fillMaxWidth()
                .height(screenHeight * 0.4f)
        ) {
            when (painter.state) {
                is AsyncImagePainter.State.Loading -> {
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .background(MaterialTheme.colorScheme.surfaceVariant),
                        contentAlignment = Alignment.Center
                    ) {
                        CircularProgressIndicator()
                    }
                }

                is AsyncImagePainter.State.Error -> {
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .background(MaterialTheme.colorScheme.errorContainer),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = stringResource(R.string.error_loading_image),
                            color = MaterialTheme.colorScheme.onErrorContainer,
                            style = MaterialTheme.typography.bodyMedium
                        )
                    }
                }

                else -> {
                    SubcomposeAsyncImageContent()
                }
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        Column(modifier = Modifier.padding(horizontal = 16.dp)) {
            Text(
                text = product.title,
                style = MaterialTheme.typography.headlineSmall.copy(fontWeight = FontWeight.Bold),
                color = MaterialTheme.colorScheme.onBackground
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = product.priceFormatted,
                style = MaterialTheme.typography.bodyLarge
            )

            Text(
                text = product.discountFormatted,
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.primary
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = product.stockText,
                style = MaterialTheme.typography.bodyMedium
            )

            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.padding(top = 8.dp)
            ) {
                Icon(
                    imageVector = product.ratingIcon,
                    contentDescription = stringResource(R.string.rating_icon_content_description),
                    tint = MaterialTheme.colorScheme.secondary
                )
                Spacer(modifier = Modifier.width(4.dp))
                Text(
                    text = product.ratingText,
                    style = MaterialTheme.typography.bodyMedium
                )
            }
        }

        Spacer(modifier = Modifier.height(16.dp))
    }
}

@Preview(showBackground = true)
@Composable
fun ProductDetailPreview() {
    ProductDetailStateless(
        product = ProductUiModel(
            id = 1,
            title = "Macbook Pro M3",
            description = "High-performance laptop",
            priceFormatted = "$2,499.00",
            discountFormatted = "15% OFF",
            rating = 4.8,
            ratingText = "4.8",
            ratingIcon = Icons.Default.Star,
            stockText = "In stock: 5 units",
            thumbnailUrl = "" // Or use a mock URL
        )
    )
}
