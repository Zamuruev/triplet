package com.triplet

import com.triplet.models.CharacterModel
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {
    @GET("characters")
    suspend fun getCharacters(
        @Query("page") page: Int,
        @Query("pageSize") pageSize: Int = 50
    ): List<CharacterModel>
}
