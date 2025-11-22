package com.moviles.pc02moviles_22200127_22200224

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import com.moviles.pc02moviles_22200127_22200224.presentation.listado.ListadoScreen
import com.moviles.pc02moviles_22200127_22200224.presentation.registro.RegistroScreen

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            MaterialTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    var currentScreen by remember { mutableStateOf("registro") }

                    when (currentScreen) {
                        "registro" -> {
                            RegistroScreen(
                                onNavigateToList = { currentScreen = "listado" }
                            )
                        }
                        "listado" -> {
                            ListadoScreen(
                                onNavigateBack = { currentScreen = "registro" }
                            )
                        }
                    }
                }
            }
        }
    }
}