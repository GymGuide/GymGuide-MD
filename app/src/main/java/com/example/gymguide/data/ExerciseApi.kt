package com.example.gymguide.data

import retrofit2.Response
import retrofit2.http.GET

interface ExerciseApi {
    @GET("/exercises")
    suspend fun getExercise(): Response<Exercise?>

    @GET("/")
    suspend fun getExercises(): Response<ResponseList>
}
