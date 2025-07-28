package com.coding.products.productlist

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import androidx.paging.map
import com.coding.domain.usecase.FetchProductsIfNeededUseCase
import com.coding.domain.usecase.GetPagedProductsUseCase
import com.coding.products.model.toUiModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@OptIn(ExperimentalCoroutinesApi::class, FlowPreview::class)
@HiltViewModel
class ProductListViewModel @Inject constructor(
    private val fetchProductsIfNeededUseCase: FetchProductsIfNeededUseCase,
    private val getPagedProductsUseCase: GetPagedProductsUseCase
) : ViewModel() {

    private val searchQuery = MutableStateFlow("")

    private val _uiState = MutableStateFlow(
        ProductListUiState(
            products = MutableStateFlow(PagingData.empty()),
            searchQuery = "",
            isLoading = true
        )
    )
    val uiState: StateFlow<ProductListUiState> = _uiState.asStateFlow()

    init {
        viewModelScope.launch {
            fetchProductsIfNeededUseCase()

            val pagingFlow = searchQuery
                .debounce(300)
                .flatMapLatest { query ->
                    getPagedProductsUseCase(query)
                        .map { it.map { domain -> domain.toUiModel() } }
                }
                .cachedIn(viewModelScope)

            _uiState.update {
                it.copy(
                    products = pagingFlow.stateIn(
                        viewModelScope,
                        SharingStarted.WhileSubscribed(5_000),
                        PagingData.empty()
                    ),
                    isLoading = false
                )
            }
        }
    }

    fun onSearchQueryChanged(query: String) {
        searchQuery.value = query
        _uiState.update { it.copy(searchQuery = query) }
    }
}
