package com.jade.titanflex.api

import com.jade.titanflex.api.data.wgerResponse
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query
import retrofit2.http.Url

interface wgerAPI {
    @GET
    suspend fun extraerDatos(
        @Url url:String): wgerResponse
}

object RetrofitFactory{
    fun getRetrofit(): wgerAPI {
        return Retrofit.Builder()
            .baseUrl("https://botw-compendium.herokuapp.com/api/v3/compendium/")
            .addConverterFactory(GsonConverterFactory.create())
            .build().create(wgerAPI::class.java)
    }

}