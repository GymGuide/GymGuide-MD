package com.example.gymguide.data

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitInstance {

    val api: ExerciseApi by lazy {
        Retrofit.Builder()
            .baseUrl("https://exercise-crng4qpv6q-et.a.run.app")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ExerciseApi::class.java)
    }
}