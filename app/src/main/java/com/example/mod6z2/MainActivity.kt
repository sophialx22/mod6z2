package com.example.nobelprize
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.nobelprize.data.api.NobelApiService
import com.example.nobelprize.data.repository.NobelRepositoryImpl
import com.example.nobelprize.domain.usecase.GetNobelPrizesUseCase
import com.example.nobelprize.presentation.NobelListScreen
import com.example.nobelprize.presentation.NobelListViewModel
import com.example.nobelprize.presentation.NobelDetailScreen
import com.example.nobelprize.presentation.NobelDetailViewModel
import com.example.nobelprize.ui.theme.NobelPrizeTheme
import io.ktor.client.*
import io.ktor.client.engine.android.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.client.plugins.logging.*
import io.ktor.client.plugins.*
import io.ktor.client.request.*
import io.ktor.serialization.kotlinx.json.*
import kotlinx.serialization.json.Json

class MainActivity : ComponentActivity() {
    private val httpClient = HttpClient(Android) {
        install(ContentNegotiation) {
            json(Json {
                ignoreUnknownKeys = true
                isLenient = true
            })
        }
        install(Logging) {
            level = LogLevel.ALL
        }
        install(HttpTimeout) {
            requestTimeoutMillis = 30000
            connectTimeoutMillis = 15000
            socketTimeoutMillis = 15000
        }
        defaultRequest {
            header("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36")
            header("Accept", "application/json")
            header("Host", "api.nobelprize.org")
        }
    }
    private val apiService = NobelApiService(httpClient)
    private val repository = NobelRepositoryImpl(apiService)
    private val getNobelPrizesUseCase = GetNobelPrizesUseCase(repository)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            NobelPrizeTheme {
                Surface(modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background) {
                    val navController = rememberNavController()
                    val listViewModel: NobelListViewModel = viewModel {
                        NobelListViewModel(getNobelPrizesUseCase)
                    }
                    NavHost(navController = navController, startDestination = "list") {
                        composable("list") {
                            NobelListScreen(navController = navController, viewModel = listViewModel)
                        }
                        composable("detail/{laureateId}/{prizeYear}/{category}") { backStackEntry ->
                            val laureateId = backStackEntry.arguments?.getString("laureateId") ?: ""
                            val prizeYear = backStackEntry.arguments?.getString("prizeYear") ?: ""
                            val category = backStackEntry.arguments?.getString("category") ?: ""
                            val detailViewModel: NobelDetailViewModel = viewModel {
                                NobelDetailViewModel(getNobelPrizesUseCase, laureateId, prizeYear, category)
                            }
                            NobelDetailScreen(navController = navController, viewModel = detailViewModel)
                        }
                    }
                }
            }
        }
    }
}