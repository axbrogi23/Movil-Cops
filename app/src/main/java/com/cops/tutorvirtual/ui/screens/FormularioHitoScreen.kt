package com.cops.tutorvirtual.ui.screens

import android.Manifest
import android.net.Uri
import android.os.Build
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.cops.tutorvirtual.data.NotificationHelper
import com.cops.tutorvirtual.viewmodel.HitoViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FormularioHitoScreen(
    viewModel: HitoViewModel,
    onVolver: () -> Unit
) {
    val context = LocalContext.current
    val notificationHelper = remember { NotificationHelper(context) }

    val titulo by viewModel.titulo.collectAsState()
    val descripcion by viewModel.descripcion.collectAsState()
    val fechaLimite by viewModel.fechaLimite.collectAsState()
    val estado by viewModel.estado.collectAsState()

    val tituloError by viewModel.tituloError.collectAsState()
    val descripcionError by viewModel.descripcionError.collectAsState()
    val fechaError by viewModel.fechaError.collectAsState()

    var exitoGuardado by remember { mutableStateOf(false) }
    var archivoSeleccionadoUri by remember { mutableStateOf<Uri?>(null) }

    // Selector Nativo de Archivos (Recurso Nativo 2)
    val filePickerLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri: Uri? ->
        archivoSeleccionadoUri = uri
    }

    // Solicitud de permiso para notificaciones en Android 13+
    val permissionLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission()
    ) { }

    LaunchedEffect(Unit) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            permissionLauncher.launch(Manifest.permission.POST_NOTIFICATIONS)
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Registrar Hito de Tesis") },
                navigationIcon = {
                    IconButton(onClick = onVolver) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Volver")
                    }
                }
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(16.dp)
                .verticalScroll(rememberScrollState()),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Text(
                text = "Ingrese los datos del entregable requerido según la pauta institucional:",
                style = MaterialTheme.typography.bodyMedium
            )

            OutlinedTextField(
                value = titulo,
                onValueChange = { viewModel.onTituloChange(it) },
                label = { Text("Título del Avance (ej: Marco Teórico)") },
                isError = tituloError != null,
                trailingIcon = {
                    if (tituloError != null) {
                        Icon(Icons.Default.Warning, contentDescription = "Error", tint = MaterialTheme.colorScheme.error)
                    }
                },
                modifier = Modifier.fillMaxWidth()
            )
            AnimatedVisibility(visible = tituloError != null) {
                tituloError?.let { Text(text = it, color = MaterialTheme.colorScheme.error, style = MaterialTheme.typography.bodySmall) }
            }

            OutlinedTextField(
                value = descripcion,
                onValueChange = { viewModel.onDescripcionChange(it) },
                label = { Text("Descripción / Formato Requerido") },
                isError = descripcionError != null,
                trailingIcon = {
                    if (descripcionError != null) {
                        Icon(Icons.Default.Warning, contentDescription = "Error", tint = MaterialTheme.colorScheme.error)
                    }
                },
                modifier = Modifier.fillMaxWidth(),
                minLines = 3
            )
            AnimatedVisibility(visible = descripcionError != null) {
                descripcionError?.let { Text(text = it, color = MaterialTheme.colorScheme.error, style = MaterialTheme.typography.bodySmall) }
            }

            OutlinedTextField(
                value = fechaLimite,
                onValueChange = { viewModel.onFechaChange(it) },
                label = { Text("Fecha Límite (ej: 15-11-2026)") },
                isError = fechaError != null,
                trailingIcon = {
                    if (fechaError != null) {
                        Icon(Icons.Default.Warning, contentDescription = "Error", tint = MaterialTheme.colorScheme.error)
                    }
                },
                modifier = Modifier.fillMaxWidth()
            )
            AnimatedVisibility(visible = fechaError != null) {
                fechaError?.let { Text(text = it, color = MaterialTheme.colorScheme.error, style = MaterialTheme.typography.bodySmall) }
            }

            // Recurso Nativo 2: Adjuntar borrador o documento local
            OutlinedButton(
                onClick = { filePickerLauncher.launch("application/pdf") },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    if (archivoSeleccionadoUri == null) "Adjuntar Documento/Borrador (PDF)"
                    else "Archivo adjunto: ${archivoSeleccionadoUri?.lastPathSegment}"
                )
            }

            // Botón de Guardar con Disparo de Notificación (Recurso Nativo 1)
            Button(
                onClick = {
                    viewModel.validarYGuardarHito {
                        exitoGuardado = true
                        notificationHelper.enviarNotificacionHitoGuardado(titulo, fechaLimite)
                    }
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Guardar Entregable")
            }

            AnimatedVisibility(visible = exitoGuardado) {
                Card(
                    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.primaryContainer),
                    modifier = Modifier.fillMaxWidth().padding(top = 8.dp)
                ) {
                    Row(
                        modifier = Modifier.padding(16.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(Icons.Default.CheckCircle, contentDescription = null, tint = MaterialTheme.colorScheme.primary)
                        Spacer(modifier = Modifier.width(8.dp))
                        Text("Hito guardado y notificación enviada.")
                    }
                }
            }
        }
    }
}