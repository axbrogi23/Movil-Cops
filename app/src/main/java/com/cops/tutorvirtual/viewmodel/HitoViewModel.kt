package com.cops.tutorvirtual.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.cops.tutorvirtual.data.AppDatabase
import com.cops.tutorvirtual.data.HitoEntity
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class HitoViewModel(application: Application) : AndroidViewModel(application) {

    private val hitoDao = AppDatabase.getDatabase(application).hitoDao()

    // Lista de hitos observada desde la base de datos local
    val hitos = hitoDao.getAllHitos()

    // Estados del formulario
    private val _titulo = MutableStateFlow("")
    val titulo: StateFlow<String> = _titulo.asStateFlow()

    private val _descripcion = MutableStateFlow("")
    val descripcion: StateFlow<String> = _descripcion.asStateFlow()

    private val _fechaLimite = MutableStateFlow("")
    val fechaLimite: StateFlow<String> = _fechaLimite.asStateFlow()

    private val _estado = MutableStateFlow("Por iniciar")
    val estado: StateFlow<String> = _estado.asStateFlow()

    // Estados de errores de validación
    private val _tituloError = MutableStateFlow<String?>(null)
    val tituloError: StateFlow<String?> = _tituloError.asStateFlow()

    private val _descripcionError = MutableStateFlow<String?>(null)
    val descripcionError: StateFlow<String?> = _descripcionError.asStateFlow()

    private val _fechaError = MutableStateFlow<String?>(null)
    val fechaError: StateFlow<String?> = _fechaError.asStateFlow()

    // Handlers para cambios en los campos con validación en tiempo real
    fun onTituloChange(nuevoTitulo: String) {
        _titulo.value = nuevoTitulo
        _tituloError.value = if (nuevoTitulo.isBlank()) "El título del hito es obligatorio" else null
    }

    fun onDescripcionChange(nuevaDescripcion: String) {
        _descripcion.value = nuevaDescripcion
        _descripcionError.value = if (nuevaDescripcion.isBlank()) "La descripción es obligatoria" else null
    }

    fun onFechaChange(nuevaFecha: String) {
        _fechaLimite.value = nuevaFecha
        _fechaError.value = if (nuevaFecha.isBlank()) "Debe indicar una fecha de entrega" else null
    }

    fun onEstadoChange(nuevoEstado: String) {
        _estado.value = nuevoEstado
    }

    // Validación general centralizada (IE 2.2.1)
    fun validarYGuardarHito(onExito: () -> Unit) {
        val esTituloValido = _titulo.value.isNotBlank()
        val esDescValida = _descripcion.value.isNotBlank()
        val esFechaValida = _fechaLimite.value.isNotBlank()

        _tituloError.value = if (esTituloValido) null else "El título del hito es obligatorio"
        _descripcionError.value = if (esDescValida) null else "La descripción es obligatoria"
        _fechaError.value = if (esFechaValida) null else "Debe indicar una fecha de entrega"

        if (esTituloValido && esDescValida && esFechaValida) {
            viewModelScope.launch {
                val nuevoHito = HitoEntity(
                    titulo = _titulo.value,
                    descripcion = _descripcion.value,
                    fechaLimite = _fechaLimite.value,
                    estado = _estado.value
                )
                hitoDao.insertHito(nuevoHito)
                limpiarFormulario()
                onExito()
            }
        }
    }

    fun actualizarEstado(hito: HitoEntity, nuevoEstado: String) {
        viewModelScope.launch {
            hitoDao.updateHito(hito.copy(estado = nuevoEstado))
        }
    }

    private fun limpiarFormulario() {
        _titulo.value = ""
        _descripcion.value = ""
        _fechaLimite.value = ""
        _estado.value = "Por iniciar"
        _tituloError.value = null
        _descripcionError.value = null
        _fechaError.value = null
    }
}