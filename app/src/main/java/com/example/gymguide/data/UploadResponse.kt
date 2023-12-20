package com.example.gymguide.data

import com.google.gson.annotations.SerializedName

data class UploadResponse(

    @field:SerializedName("data")
    val data: Data,

    @field:SerializedName("status")
    val status: Status
)

data class Data(

    @field:SerializedName("equipment_prediction")
    val equipmentPrediction: String,

    @field:SerializedName("confidence")
    val confidence: Any
)

data class Status(

    @field:SerializedName("code")
    val code: Int,

    @field:SerializedName("message")
    val message: String
)
