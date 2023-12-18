package com.example.gymguide.data

import com.google.gson.annotations.SerializedName

data class ResponseList(
	@SerializedName("data")
	val data: List<Exercise>
)

data class Exercise(
	@SerializedName("id")
	val id: Int,

	@SerializedName("name")
	val name: String,

	@SerializedName("type")
	val type: String,

	@SerializedName("muscle")
	val muscle: String,

	@SerializedName("equipment")
	val equipment: String,

	@SerializedName("difficulty")
	val difficulty: String,

	@SerializedName("instructions")
	val instructions: String,

	@SerializedName("link")
	val link: String,

	@SerializedName("picture")
	val picture: String,

	@SerializedName("animation")
	val animation: String
)

// Status class remains the same
//data class Status(
//	@SerializedName("code")
//	val code: Int,
//
//	@SerializedName("message")
//	val message: String
//)
