package com.example.marsphotos.network
import com.example.marsphotos.model.Nota
import com.example.marsphotos.model.NotaDT
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
//import retrofit2.converter.scalars.ScalarsConverterFactory
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

interface MarsApiService {
    @GET("api/NotasTareas")
    suspend fun getNotasTareas(): List<Nota>

    @POST("api/NotasTareas")
    suspend fun addNotasTareas(@Body nota: NotaDT): Nota

    @PUT("api/NotasTareas/{id}")
    suspend fun editNotasTareas(@Path("id") id: Int, @Body nota: Nota): Nota

    @DELETE("api/NotasTareas/{id}")
    suspend fun deleteNotasTareas(@Path("id") id: Int)


}

