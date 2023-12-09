package com.example.gymguide.data

import com.example.gymguide.ui.Exercise
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {
    @GET("exercises")
    suspend fun getExercise(@Query("equipment") name: String?): Response<Exercise?>

    @GET("/")
    suspend fun getExercises(): Response<ResponseList>
}
