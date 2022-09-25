package com.chaliez.alma.test.data.network.retrofit

import com.chaliez.alma.test.data.network.model.ApiMarvelCharacter
import com.chaliez.alma.test.data.network.model.ApiMarvelCharactersResponse
import retrofit2.http.GET
import retrofit2.http.Path

interface MarvelService {

    @GET("/v1/public/characters")
    suspend fun getCharacters(): ApiMarvelCharactersResponse<ApiMarvelCharacter>

    @GET("/v1/public/characters/{id}")
    suspend fun getCharacter(@Path("id") id: Int): ApiMarvelCharactersResponse<ApiMarvelCharacter>
}

