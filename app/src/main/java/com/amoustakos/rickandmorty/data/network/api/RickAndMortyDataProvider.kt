package com.amoustakos.rickandmorty.data.network.api

import com.amoustakos.rickandmorty.data.cache.RamCacheProvider
import com.amoustakos.rickandmorty.data.network.api.models.request.ApiFetchCharacterRequest
import com.amoustakos.rickandmorty.data.network.api.models.request.ApiFetchCharactersByIdsRequest
import com.amoustakos.rickandmorty.data.network.api.models.request.ApiFetchCharactersRequest
import com.amoustakos.rickandmorty.data.network.api.models.request.ApiFetchEpisodesRequest
import com.amoustakos.rickandmorty.data.network.api.models.response.ApiCharacter
import com.amoustakos.rickandmorty.data.network.api.models.response.ApiCharactersResponse
import com.amoustakos.rickandmorty.data.network.api.models.response.ApiEpisodesResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query
import javax.inject.Inject


interface RickAndMortyApi {

    @GET(value = "episode/")
    suspend fun fetchEpisodes(@Query(value = "page") page: Int): Response<ApiEpisodesResponse>

    @GET(value = "character/{$KEY_CHARACTER_ID}")
    suspend fun fetchCharacterById(@Path(value = KEY_CHARACTER_ID) id: Int): Response<ApiCharacter>

    @GET(value = "character/{KEY_CHARACTER_IDS}")
    suspend fun fetchCharactersById(@Path(value = KEY_CHARACTER_IDS) ids: String): Response<List<ApiCharacter>>

    @GET(value = "character/")
    suspend fun fetchCharacters(): Response<ApiCharactersResponse>

    companion object {
        private const val KEY_CHARACTER_ID = "KEY_CHARACTER_ID"
        private const val KEY_CHARACTER_IDS = "KEY_CHARACTER_IDS"
    }
}

interface RickAndMortyDataProvider {
    suspend fun fetchEpisodes(req: ApiFetchEpisodesRequest): Response<ApiEpisodesResponse>
    suspend fun fetchCharacterById(req: ApiFetchCharacterRequest): Response<ApiCharacter>
    suspend fun fetchCharacters(req: ApiFetchCharactersRequest): Response<ApiCharactersResponse>
    suspend fun fetchCharacters(req: ApiFetchCharactersByIdsRequest): Response<ApiCharactersResponse>
}

class RickAndMortyDataProviderImpl @Inject constructor(
    private val api: RickAndMortyApi,
    private val cache: RamCacheProvider,
) : RickAndMortyDataProvider {

    override suspend fun fetchEpisodes(req: ApiFetchEpisodesRequest): Response<ApiEpisodesResponse> {
        val cachedItem: Response<ApiEpisodesResponse>? = cache.get(req)

        return if (cachedItem != null) {
            cachedItem
        } else {
            val response = api.fetchEpisodes(req.page)
            cache.store(req, response)
            response
        }
    }

    override suspend fun fetchCharacterById(req: ApiFetchCharacterRequest): Response<ApiCharacter> {
        val cachedItem: Response<ApiCharacter>? = cache.get(req)

        return if (cachedItem != null) {
            cachedItem
        } else {
            val response = api.fetchCharacterById(req.id)
            cache.store(req, response)
            response
        }
    }

    override suspend fun fetchCharacters(req: ApiFetchCharactersRequest): Response<ApiCharactersResponse> {
        val cachedItem: Response<ApiCharactersResponse>? = cache.get(req)

        return if (cachedItem != null) {
            cachedItem
        } else {
            val response = api.fetchCharacters()
            cache.store(req, response)
            response
        }
    }

    override suspend fun fetchCharacters(req: ApiFetchCharactersByIdsRequest): Response<ApiCharactersResponse> {
        val cachedItem: Response<ApiCharactersResponse>? = cache.get(req)

        return if (cachedItem != null) {
            cachedItem
        } else {
            val response = api.fetchCharactersById(req.ids)
            val tempResp = Response.success(ApiCharactersResponse(results = response.body()))
            cache.store(req, tempResp)
            tempResp
        }
    }


}