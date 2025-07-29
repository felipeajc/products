package com.coding.products.productdetails

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.coding.domain.usecase.GetProductByIdUseCase
import com.coding.products.model.toUiModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProductDetailViewModel @Inject constructor(
    private val getProductByIdUseCase: GetProductByIdUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow(ProductDetailUiState())
    val uiState: StateFlow<ProductDetailUiState> = _uiState.asStateFlow()

    fun loadProduct(errorMessage: String, id: Int) {
        _uiState.value = _uiState.value.copy(isLoading = true, errorMessage = null)

        viewModelScope.launch {
            try {
                val product = getProductByIdUseCase(id)?.toUiModel()
                _uiState.value = ProductDetailUiState(product = product, isLoading = false)
            } catch (e: Exception) {
                _uiState.value = ProductDetailUiState(
                    product = null,
                    isLoading = false,
                    errorMessage = errorMessage
                )
            }
        }
    }
}
