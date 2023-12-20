package com.example.gymguide.data

import okhttp3.MultipartBody
import retrofit2.http.Header
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part

interface ModelApi {
    @Multipart
    @POST("prediction")
    suspend fun uploadImage(
        @Header("Authorization") token: String,  // Add bearer token as a query parameter
        @Part file: MultipartBody.Part,
    ): UploadResponse
}