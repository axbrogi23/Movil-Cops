package com.cops.tutorvirtual

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.cops.tutorvirtual.ui.screens.FaqScreen
import com.cops.tutorvirtual.ui.screens.FormularioHitoScreen
import com.cops.tutorvirtual.ui.screens.ListaHitosScreen
import com.cops.tutorvirtual.ui.theme.TutorVirtualTheme
import com.cops.tutorvirtual.viewmodel.HitoViewModel

class MainActivity : ComponentActivity() {

    private val hitoViewModel: HitoViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            TutorVirtualTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    val navController = rememberNavController()

                    NavHost(
                        navController = navController,
                        startDestination = "lista_hitos"
                    ) {
                        composable("lista_hitos") {
                            ListaHitosScreen(
                                viewModel = hitoViewModel,
                                onNavegarAFormulario = { navController.navigate("formulario_hito") },
                                onNavegarAFaq = { navController.navigate("faq") }
                            )
                        }
                        composable("formulario_hito") {
                            FormularioHitoScreen(
                                viewModel = hitoViewModel,
                                onVolver = { navController.popBackStack() }
                            )
                        }
                        composable("faq") {
                            FaqScreen(
                                onVolver = { navController.popBackStack() }
                            )
                        }
                    }
                }
            }
        }
    }
}