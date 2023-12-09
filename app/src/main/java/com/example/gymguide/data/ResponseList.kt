package com.example.gymguide.data

import com.google.gson.annotations.SerializedName

data class ResponseList(

	@field:SerializedName("data")
	val data: List<DataItem?>? = null,

	@field:SerializedName("status")
	val status: Status? = null
)

data class DataItem(

	@field:SerializedName("difficulty")
	val difficulty: String? = null,

	@field:SerializedName("instructions")
	val instructions: String? = null,

	@field:SerializedName("muscle")
	val muscle: String? = null,

	@field:SerializedName("name")
	val name: String? = null,

	@field:SerializedName("link")
	val link: String? = null,

	@field:SerializedName("equipment")
	val equipment: String? = null,

	@field:SerializedName("type")
	val type: String? = null,

	@field:SerializedName("picture")
	val picture: String? = null
)

data class Status(

	@field:SerializedName("code")
	val code: Int? = null,

	@field:SerializedName("message")
	val message: String? = null
)
