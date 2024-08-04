package com.hanif.e_commerce.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.hanif.e_commerce.presentation.products.ProductScreen
import com.hanif.e_commerce.presentation.products.ProductViewModel
import com.hanif.e_commerce.presentation.ui.theme.ECommerceTheme
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.serialization.Serializable

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            ECommerceTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    val p = innerPadding
                    val navController = rememberNavController()
                    val sharedViewModel : ProductViewModel = hiltViewModel()
                    NavHost(navController = navController, startDestination = ProductScreen) {
                        composable<ProductScreen> {
                            ProductScreen(
                                viewModel = sharedViewModel,
                                modifier = Modifier.padding(p),
                                navController = navController
                            )
                        }
                        composable<AddToCardScreen> {
                            AddToCardScreen(
                                viewModel = sharedViewModel,
                                modifier = Modifier.padding(p),
                                navController = navController
                            )
                        }
                    }
                }
            }
        }
    }
}


@Serializable
object ProductScreen

@Serializable
object AddToCardScreen