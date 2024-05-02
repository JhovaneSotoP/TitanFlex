package com.jade.titanflex.api

import com.jade.titanflex.api.data.wgerEjercicioBaseResponse
import com.jade.titanflex.api.data.wgerImageResponse
import com.jade.titanflex.api.data.wgerResponse
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query
import retrofit2.http.Url

interface wgerAPI {
    @GET("exercise/")
    suspend fun extraerEjercicios(
        @Query("Token") apiKey:String="e1bfb9bd630a80b24edf2d12587002f4e8884591",
        @Query("language") idLenguaje:Int=4,
        @Query("offset") inicioConteo:Int=0,
        @Query("limit") limite:Int=20): wgerResponse

    @GET("exercise/")
    suspend fun extraerEjerciciosConBase(
        @Query("Token") apiKey:String="e1bfb9bd630a80b24edf2d12587002f4e8884591",
        @Query("exercise_base") idEjercicioBase:Int,
        @Query("language") idLenguaje:Int=4,
        @Query("offset") inicioConteo:Int=0,
        @Query("limit") limite:Int=20): wgerResponse

    @GET("exercise-base/")
    suspend fun extraerEjerciciosBase(
        @Query("Token") apiKey:String="e1bfb9bd630a80b24edf2d12587002f4e8884591",
        @Query("offset") inicioConteo:Int=0,
        @Query("limit") limite:Int=20): wgerEjercicioBaseResponse

    @GET("exerciseimage/")
    suspend fun extraerImagenes(
        @Query("Token") apiKey:String="e1bfb9bd630a80b24edf2d12587002f4e8884591",
        @Query("exercise_base") idEjercicioBase:Int): wgerImageResponse
}

object RetrofitFactory{
    fun getRetrofit(): wgerAPI {
        return Retrofit.Builder()
            .baseUrl("https://wger.de/api/v2/")
            .addConverterFactory(GsonConverterFactory.create())
            .build().create(wgerAPI::class.java)
    }

}