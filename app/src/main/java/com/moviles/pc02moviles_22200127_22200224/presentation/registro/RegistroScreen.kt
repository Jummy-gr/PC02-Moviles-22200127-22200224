package com.moviles.pc02moviles_22200127_22200224.presentation.registro

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.moviles.pc02moviles_22200127_22200224.data.firebase.FirebaseManager
import com.moviles.pc02moviles_22200127_22200224.data.model.TeamModel
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RegistroScreen(onNavigateToList: () -> Unit) {
    var nombreEquipo by remember { mutableStateOf("") }
    var anioFundacion by remember { mutableStateOf("") }
    var nombreEstadio by remember { mutableStateOf("") }
    var urlImagen by remember { mutableStateOf("") }
    var isLoading by remember { mutableStateOf(false) }
    var errorMessage by remember { mutableStateOf("") }

    val scope = rememberCoroutineScope()
    val firebaseManager = remember { FirebaseManager() }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Registro Liga 1") }
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {

            OutlinedTextField(
                value = nombreEquipo,
                onValueChange = { nombreEquipo = it },
                label = { Text("Nombre del equipo") },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true,
                enabled = !isLoading
            )

            Spacer(modifier = Modifier.height(16.dp))

            OutlinedTextField(
                value = anioFundacion,
                onValueChange = { anioFundacion = it },
                label = { Text("Año de fundación") },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true,
                enabled = !isLoading
            )

            Spacer(modifier = Modifier.height(16.dp))

            OutlinedTextField(
                value = nombreEstadio,
                onValueChange = { nombreEstadio = it },
                label = { Text("Nombre del estadio (casa)") },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true,
                enabled = !isLoading
            )

            Spacer(modifier = Modifier.height(16.dp))

            OutlinedTextField(
                value = urlImagen,
                onValueChange = { urlImagen = it },
                label = { Text("URL de la imagen del equipo") },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true,
                enabled = !isLoading
            )

            Spacer(modifier = Modifier.height(24.dp))

            Button(
                onClick = {
                    scope.launch {
                        isLoading = true
                        errorMessage = ""

                        val team = TeamModel(
                            nombreEquipo = nombreEquipo,
                            anioFundacion = anioFundacion,
                            nombreEstadio = nombreEstadio,
                            urlImagenEquipo = urlImagen
                        )

                        firebaseManager.saveTeam(team)
                            .onSuccess {
                                onNavigateToList()
                            }
                            .onFailure { e ->
                                errorMessage = "Error: ${e.message}"
                                isLoading = false
                            }
                    }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp),
                enabled = !isLoading &&
                        nombreEquipo.isNotBlank() &&
                        anioFundacion.isNotBlank() &&
                        nombreEstadio.isNotBlank() &&
                        urlImagen.isNotBlank()
            ) {
                if (isLoading) {
                    CircularProgressIndicator(
                        modifier = Modifier.size(24.dp),
                        color = MaterialTheme.colorScheme.onPrimary
                    )
                } else {
                    Text("Guardar")
                }
            }

            if (errorMessage.isNotEmpty()) {
                Spacer(modifier = Modifier.height(16.dp))
                Text(
                    text = errorMessage,
                    color = MaterialTheme.colorScheme.error,
                    style = MaterialTheme.typography.bodySmall
                )
            }
        }
    }
}