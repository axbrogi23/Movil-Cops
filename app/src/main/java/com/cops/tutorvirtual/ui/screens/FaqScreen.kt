package com.cops.tutorvirtual.ui.screens

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

data class FaqItem(
    val id: Int,
    val pregunta: String,
    val respuesta: String,
    val categoria: String
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FaqScreen(onVolver: () -> Unit) {
    var searchQuery by remember { mutableStateOf("") }

    val faqList = remember {
        listOf(
            FaqItem(
                1,
                "¿Cómo estructurar el Marco Teórico?",
                "El Marco Teórico debe incluir los antecedentes de la investigación, bases teóricas y definición de conceptos clave relacionados con la gestión en seguridad pública.",
                "Metodología"
            ),
            FaqItem(
                2,
                "¿Cómo citar según Norma APA 7ma edición?",
                "Para citas textuales de menos de 40 palabras, incorpórelas en el texto entre comillas e indique autor, año y página: (Pérez, 2024, p. 15).",
                "Citación"
            ),
            FaqItem(
                3,
                "¿Cuál es el formato de presentación para los informes?",
                "Tipo de fuente Arial 11 o Times New Roman 12, interlineado 1.5, márgenes estándar de 2.5 cm y alineación justificada.",
                "Formato"
            ),
            FaqItem(
                4,
                "¿Cómo definir el Planteamiento del Problema?",
                "Debe describir la situación actual, los síntomas, causas y la justificación institucional dentro del contexto de ESUCAR.",
                "Metodología"
            )
        )
    }

    val filteredFaqs = faqList.filter {
        it.pregunta.contains(searchQuery, ignoreCase = true) ||
                it.respuesta.contains(searchQuery, ignoreCase = true) ||
                it.categoria.contains(searchQuery, ignoreCase = true)
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Tutor Virtual - FAQ") },
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
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            OutlinedTextField(
                value = searchQuery,
                onValueChange = { searchQuery = it },
                label = { Text("Buscar duda metodológica o formato...") },
                leadingIcon = { Icon(Icons.Default.Search, contentDescription = "Buscar") },
                modifier = Modifier.fillMaxWidth()
            )

            LazyColumn(
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items(filteredFaqs, key = { it.id }) { faq ->
                    FaqCardItem(faq = faq)
                }
            }
        }
    }
}

@Composable
fun FaqCardItem(faq: FaqItem) {
    var expanded by remember { mutableStateOf(false) }

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .animateContentSize()
            .clickable { expanded = !expanded },
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = faq.pregunta,
                    style = MaterialTheme.typography.titleMedium,
                    modifier = Modifier.weight(1f)
                )
                Icon(
                    imageVector = if (expanded) Icons.Default.KeyboardArrowUp else Icons.Default.KeyboardArrowDown,
                    contentDescription = "Expandir"
                )
            }

            AssistChip(
                onClick = { },
                label = { Text(faq.categoria, style = MaterialTheme.typography.labelSmall) },
                modifier = Modifier.padding(top = 4.dp)
            )

            AnimatedVisibility(visible = expanded) {
                Column(modifier = Modifier.padding(top = 12.dp)) {
                    Divider()
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = faq.respuesta,
                        style = MaterialTheme.typography.bodyMedium
                    )
                }
            }
        }
    }
}