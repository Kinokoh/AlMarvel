package com.chaliez.alma.test.data.network.model

import com.chaliez.alma.test.data.local.database.MarvelHeroRow
import com.chaliez.alma.test.data.model.MarvelHero
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

/**
 * ApiMarvelCharactersContainer holds a list of ApiCharacter.
 *
 * This is to parse firsts levels of our network result which looks like
 *
 * {
 *   "data": [
 *      ...
 *      "results": wantedResults (generic T)
 *      ]
 * }
 */
@JsonClass(generateAdapter = true)
data class ApiMarvelCharactersResponse<T>(
    @field:Json(name = "data")
    val data: ApiMarvelCharactersContainer<T>
)

@JsonClass(generateAdapter = true)
data class ApiMarvelCharactersContainer<T>(
    @field:Json(name = "results")
    val results: List<T>
)

/**
 * adapters for character from https://developer.marvel.com/docs
 * The API contains more data, useless for this application for now
 */
@JsonClass(generateAdapter = true)
data class ApiMarvelCharacter(
    val id: Int,
    val name: String,
    val description: String,
    val thumbnail: Image
)

@JsonClass(generateAdapter = true)
data class Image(
    val path: String,
    val extension: String
)

/**
 * Convert API results to domain model objects
 */
fun ApiMarvelCharacter.asDomainModel(): MarvelHero =
    MarvelHero(
        id = this.id,
        name = this.name,
        description = this.description,
        thumbnail = this.getThumbnail(),
        isPartOfSquad = false
    )

/**
 * Convert API results to database objects
 */
fun ApiMarvelCharacter.asDatabaseModel(): MarvelHeroRow =
    MarvelHeroRow(
        uid = this.id,
        name = this.name,
        description = this.description,
        thumbnail = this.getThumbnail(),
        isPartOfSquad = false
    )

private fun ApiMarvelCharacter.getThumbnail(): String {
    return "${this.thumbnail.path.replace("http", "https")}.${this.thumbnail.extension}" //use SSL
}
