package com.amoustakos.rickandmorty.data.domain.models.episodes


data class EpisodesResponse(
    val page: Page,
    val episodes: List<Episode>
)

data class Page(
    val currentPage: Int,
    val availablePages: Int
)

data class Episode(
    val airDate: String,
    val characters: List<String>,
    val created: String,
    val name: String,
    val episode: String,
    val id: Int,
    val url: String,
    var image: String? = null
)