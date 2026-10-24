package com.cops.tutorvirtual.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.cops.tutorvirtual.data.HitoEntity
import com.cops.tutorvirtual.viewmodel.HitoViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ListaHitosScreen(
    viewModel: HitoViewModel,
    onNavegarAFormulario: () -> Unit,
    onNavegarAFaq: () -> Unit
) {
    val hitosList by viewModel.hitos.collectAsState(initial = emptyList())

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Seguimiento de Avances") },
                actions = {
                    TextButton(onClick = onNavegarAFaq) {
                        Text("Tutor / FAQ")
                    }
                }
            )
        },
        floatingActionButton = {
            FloatingActionButton(onClick = onNavegarAFormulario) {
                Icon(Icons.Default.Add, contentDescription = "Nuevo Hito")
            }
        }
    ) { innerPadding ->
        if (hitosList.isEmpty()) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "No hay entregables registrados.\nPresiona + para registrar uno.",
                    style = MaterialTheme.typography.bodyLarge
                )
            }
        } else {
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding)
                    .padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                items(hitosList, key = { it.id }) { hito ->
                    ItemHitoCard(
                        hito = hito,
                        onCambiarEstado = { nuevoEstado ->
                            viewModel.actualizarEstado(hito, nuevoEstado)
                        }
                    )
                }
            }
        }
    }
}

@Composable
fun ItemHitoCard(
    hito: HitoEntity,
    onCambiarEstado: (String) -> Unit
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = hito.titulo,
                    style = MaterialTheme.typography.titleMedium
                )
                AssistChip(
                    onClick = { },
                    label = { Text(hito.estado) }
                )
            }

            Text(
                text = hito.descripcion,
                style = MaterialTheme.typography.bodyMedium
            )

            Text(
                text = "Fecha Límite: ${hito.fechaLimite}",
                style = MaterialTheme.typography.labelMedium,
                color = MaterialTheme.colorScheme.secondary
            )

            Divider(modifier = Modifier.padding(vertical = 4.dp))

            Text("Cambiar Estado:", style = MaterialTheme.typography.labelSmall)

            val estados = listOf("Por iniciar", "En desarrollo", "Enviado", "Aprobado")
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                estados.forEach { est ->
                    FilterChip(
                        selected = hito.estado == est,
                        onClick = { onCambiarEstado(est) },
                        label = { Text(est, style = MaterialTheme.typography.bodySmall) }
                    )
                }
            }
        }
    }
}