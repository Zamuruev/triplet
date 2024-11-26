package com.triplet

import com.triplet.models.CharacterModel

class CharacterRepository(private val api: ApiService) {
    suspend fun fetchCharacters(page: Int): List<CharacterModel> {
        return try {
            api.getCharacters(page)
        } catch (e: Exception) {
            emptyList()
        }
    }
}
