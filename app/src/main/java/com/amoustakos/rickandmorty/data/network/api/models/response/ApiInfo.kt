package com.amoustakos.rickandmorty.data.network.api.models.response

import com.google.gson.annotations.SerializedName

data class ApiInfo(

	@SerializedName("next")
	val next: String? = null,

	@SerializedName("pages")
	val pages: Int? = null,

	@SerializedName("prev")
	val prev: String? = null,

	@SerializedName("count")
	val count: Int? = null
)