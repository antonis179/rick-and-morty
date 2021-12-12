package com.amoustakos.rickandmorty.data.domain.models.characters


data class DomainCharacter(
    val id: Int,
    val type: String,
    val url: String,
    val status: CharacterStatus,
    val image: String,
    val gender: String,
    val species: String,
    val created: String,
    val origin: Location,
    val name: String,
    val location: Location,
    val episode: List<String>
)

data class Location(
    val name: String,
    val url: String
)

enum class CharacterStatus {
    ALIVE,
    DEAD,
    UNKNOWN;

    companion object {
        fun fromApi(value: String) = when (value) {
            "Alive" -> ALIVE
            "Dead" -> DEAD
            else -> UNKNOWN
        }
    }
}