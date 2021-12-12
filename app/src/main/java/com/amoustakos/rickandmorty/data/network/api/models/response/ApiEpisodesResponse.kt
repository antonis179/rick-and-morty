package com.amoustakos.rickandmorty.data.network.api.models.response

import com.google.gson.annotations.SerializedName

data class ApiEpisodesResponse(

    @SerializedName("results")
	val results: List<ApiEpisode?>? = null,

    @SerializedName("info")
	val info: ApiInfo? = null
)

data class ApiEpisode(

    @SerializedName("air_date")
    val airDate: String? = null,

    @SerializedName("characters")
    val characters: List<String?>? = null,

    @SerializedName("created")
    val created: String? = null,

    @SerializedName("name")
    val name: String? = null,

    @SerializedName("episode")
    val episode: String? = null,

    @SerializedName("id")
    val id: Int? = null,

    @SerializedName("url")
    val url: String? = null
)