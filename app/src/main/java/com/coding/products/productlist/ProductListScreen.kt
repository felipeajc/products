package com.coding.products.productlist

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.paging.LoadState
import androidx.paging.PagingData
import androidx.paging.compose.collectAsLazyPagingItems
import com.coding.products.R
import com.coding.products.model.ProductUiModel
import kotlinx.coroutines.flow.MutableStateFlow

@Composable
fun ProductListScreen(
    viewModel: ProductListViewModel = hiltViewModel(),
    onItemClick: (productId: Int) -> Unit
) {
    val uiState by viewModel.uiState.collectAsState()

    ProductListScreenContent(
        uiState = uiState,
        onSearchQueryChanged = viewModel::onSearchQueryChanged,
        onItemClick = onItemClick
    )
}

@Composable
fun ProductListScreenContent(
    uiState: ProductListUiState,
    onSearchQueryChanged: (String) -> Unit,
    onItemClick: (productId: Int) -> Unit
) {
    val pagingItems = uiState.products.collectAsLazyPagingItems()

    var query by remember { mutableStateOf(uiState.searchQuery) }
    var active by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        ProductSearchBar(
            query = query,
            onQueryChange = {
                query = it
                if (it.trim().length >= 3 || it.isBlank()) {
                    onSearchQueryChanged(it)
                }
            },
            onSearch = {
                onSearchQueryChanged(it)
                active = false
            },
            active = active,
            onActiveChange = { active = it }
        )

        Spacer(modifier = Modifier.height(8.dp))

        ProductListStateWrapper(
            isLoading = uiState.isLoading || pagingItems.loadState.refresh is LoadState.Loading,
            isError = pagingItems.loadState.refresh is LoadState.Error,
            isEmpty = pagingItems.itemCount == 0 &&
                    pagingItems.loadState.refresh is LoadState.NotLoading &&
                    !uiState.isLoading &&
                    uiState.searchQuery.isNotBlank(),
            pagingItems = pagingItems,
            onItemClick = onItemClick
        )
    }
}

@Composable
private fun ProductListStateWrapper(
    isLoading: Boolean,
    isError: Boolean,
    isEmpty: Boolean,
    pagingItems: androidx.paging.compose.LazyPagingItems<ProductUiModel>,
    onItemClick: (productId: Int) -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
    ) {
        when {
            isLoading -> {
                CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
            }

            isError -> {
                Text(
                    text = stringResource(R.string.error_loading_products),
                    modifier = Modifier.align(Alignment.Center)
                )
            }

            isEmpty -> {
                Text(
                    text = stringResource(R.string.no_products_found),
                    modifier = Modifier.align(Alignment.Center)
                )
            }

            else -> {
                LazyColumn {
                    items(pagingItems.itemSnapshotList.items) { product ->
                        ProductItem(
                            product = product,
                            onClick = { onItemClick(product.id) }
                        )
                    }

                    item {
                        when (val appendState = pagingItems.loadState.append) {
                            is LoadState.Loading -> {
                                Box(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(16.dp),
                                    contentAlignment = Alignment.Center
                                ) {
                                    CircularProgressIndicator()
                                }
                            }

                            is LoadState.Error -> {
                                Text(
                                    text = stringResource(
                                        R.string.error_loading_more_products,
                                        appendState.error.message ?: ""
                                    ),
                                    modifier = Modifier.padding(16.dp)
                                )
                            }

                            else -> Unit
                        }
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ProductListScreenPreview() {
    val dummyProducts = listOf(
        ProductUiModel(
            id = 1,
            title = "Smartphone Samsung Galaxy",
            description = "Top smartphone com excelente câmera",
            priceFormatted = "R$ 1.999,00",
            discountFormatted = "10% OFF",
            rating = 4.5,
            ratingText = "4.5",
            ratingIcon = androidx.compose.material.icons.Icons.Default.Star,
            stockText = "Estoque: 15 unidades",
            thumbnailUrl = ""
        )
    )

    val dummyPaging = PagingData.from(dummyProducts)
    val dummyStateFlow = remember { MutableStateFlow(dummyPaging) }

    ProductListScreenContent(
        uiState = ProductListUiState(products = dummyStateFlow),
        onSearchQueryChanged = {},
        onItemClick = {}
    )
}
