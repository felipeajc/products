package com.coding.products

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.coding.products.form.FormScreen
import dagger.hilt.android.AndroidEntryPoint

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

@Composable
fun FormApp(modifier: Modifier = Modifier) {
    FormScreen(modifier)
}
