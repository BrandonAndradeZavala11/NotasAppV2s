
package com.example.marsphotos.ui.screens

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.marsphotos.MarsPhotosApplication
import com.example.marsphotos.data.MarsPhotosRepository
import com.example.marsphotos.model.Nota
import com.example.marsphotos.model.NotaDT
import kotlinx.coroutines.launch
import java.io.IOException

sealed interface MarsUiState {
    data class Success(val photos: List< Nota>) : MarsUiState
    object Error : MarsUiState
    object Loading : MarsUiState
}

class MarsViewModel(private val marsPhotosRepository: MarsPhotosRepository) : ViewModel() {

    var marsUiState: MarsUiState by mutableStateOf(MarsUiState.Loading)
        private set
    val errorState = mutableStateOf<String?>(null)

    init {
        try {
            getNotaTarea()
        } catch (e: Exception) {
            errorState.value = "Conéctate a internet para obtener los datos"
        }
    }

    fun getNotaTarea() {
        viewModelScope.launch {
            marsUiState =  try {
                val listResult = marsPhotosRepository.getNotasTarea()
                MarsUiState.Success(
                    listResult
                )
            } catch (e: IOException) {
                MarsUiState.Error
            }

        }

    }

    fun addNotaTarea(nota: NotaDT) {
        viewModelScope.launch {
            try {
                // Agregar la nueva nota
                val addedNota = marsPhotosRepository.addNotasTareas(nota)

                // Actualizar la lista de notas después de agregar la nueva nota
                val listResult = marsPhotosRepository.getNotasTarea()
                marsUiState = MarsUiState.Success(listResult)
            } catch (e: IOException) {
                marsUiState = MarsUiState.Error
            }
        }
    }

    fun editNotaTarea(id: Int, nota: Nota) {
        viewModelScope.launch {
            try {
                val editedNota = marsPhotosRepository.editNotasTareas(id, nota)
                val listResult = marsPhotosRepository.getNotasTarea()
                marsUiState = MarsUiState.Success(listResult)
            } catch (e: IOException) {
                marsUiState = MarsUiState.Error
            }
        }
    }


    fun deleteNotaTarea(id: Int) {
        viewModelScope.launch {
            try {
                val borrar = marsPhotosRepository.deleteNotasTareas(id)
                val listResult = marsPhotosRepository.getNotasTarea()
                marsUiState = MarsUiState.Success(listResult)
            } catch (e: Exception) {
                marsUiState = MarsUiState.Error
            }
        }
    }

    companion object {
        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val application = (this[APPLICATION_KEY] as MarsPhotosApplication)
                val marsPhotosRepository = application.container.marsPhotosRepository
                MarsViewModel(marsPhotosRepository = marsPhotosRepository)
            }
        }
    }

}
