package com.coding.products

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.coding.products.form.FormScreen
import dagger.hilt.android.AndroidEntryPoint

/**
 * Entry point for the 'form' flavor of the app.
 * Uses Hilt for dependency injection and sets up the Compose UI with Material3 theme.
 */
@AndroidEntryPoint
class FormMainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MaterialTheme {
                FormApp()
            }
        }
    }
}

/**
 * Renders the root composable for the form feature.
 * Right now, it's just the FormScreen wrapped in the passed modifier.
 */
@Composable
fun FormApp(modifier: Modifier = Modifier) {
    FormScreen(modifier)
}
