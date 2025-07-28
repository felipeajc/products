package com.coding.products.productlist

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SearchBar
import androidx.compose.material3.SearchBarDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.coding.products.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProductSearchBar(
    query: String,
    onQueryChange: (String) -> Unit,
    onSearch: (String) -> Unit,
    active: Boolean,
    onActiveChange: (Boolean) -> Unit
) {
    val customColors = SearchBarDefaults.colors(
        dividerColor = Color.Transparent
    )

    val placeholderText = androidx.compose.ui.res.stringResource(R.string.search_placeholder)

    SearchBar(
        inputField = {
            SearchBarDefaults.InputField(
                query = query,
                onQueryChange = onQueryChange,
                onSearch = onSearch,
                expanded = active,
                onExpandedChange = onActiveChange,
                placeholder = { Text(placeholderText) },
                colors = customColors.inputFieldColors,
            )
        },
        expanded = active,
        onExpandedChange = onActiveChange,
        modifier = Modifier
            .fillMaxWidth()
            .heightIn(min = 32.dp, max = 60.dp),
        shape = MaterialTheme.shapes.extraLarge,
        colors = customColors,
        tonalElevation = SearchBarDefaults.TonalElevation,
        shadowElevation = SearchBarDefaults.ShadowElevation,
        windowInsets = SearchBarDefaults.windowInsets,
        content = { },
    )
}
