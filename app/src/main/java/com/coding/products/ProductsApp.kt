package com.coding.products

import android.app.Application
import dagger.hilt.android.HiltAndroidApp

/**
 * Application class used to bootstrap Hilt for dependency injection.
 * Make sure it's declared in AndroidManifest.xml.
 */
@HiltAndroidApp
class ProductsApp : Application()