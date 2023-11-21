package com.example.marsphotos.data

import com.example.marsphotos.model.Nota
import com.example.marsphotos.model.NotaDT

import com.example.marsphotos.network.MarsApiService



interface MarsPhotosRepository {
    suspend fun getNotasTarea(): List<Nota>
    suspend fun addNotasTareas(nota: NotaDT): Nota
    suspend fun editNotasTareas(id: Int, nota: Nota): Nota
    suspend fun deleteNotasTareas(id: Int)

}

class NetworkMarsPhotosRepository(private val marsApiService: MarsApiService) : MarsPhotosRepository{

    override suspend fun getNotasTarea(): List<Nota> = marsApiService.getNotasTareas()

    override suspend fun addNotasTareas(nota: NotaDT): Nota {
        return marsApiService.addNotasTareas(nota)
    }

    override suspend fun editNotasTareas(id: Int, nota: Nota): Nota {
        return marsApiService.editNotasTareas(id, nota)
    }


    override suspend fun deleteNotasTareas(id: Int) {
        marsApiService.deleteNotasTareas(id)
    }
}



