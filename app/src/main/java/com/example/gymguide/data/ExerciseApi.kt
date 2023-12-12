package com.example.gymguide.data

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface ExerciseApi {
    @GET("/exercises")
    suspend fun getExercise(@Query("equipment") equipment: String): Response<ResponseList>
    @GET("/")
    suspend fun getExercises(): Response<ResponseList>
}
