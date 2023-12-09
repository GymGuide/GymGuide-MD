package com.example.gymguide.data

import com.google.gson.annotations.SerializedName

data class Response(

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
