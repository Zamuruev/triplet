package com.triplet

import kotlinx.coroutines.flow.Flow

class CharacterRepository(
    private val api: ApiService,
    private val dao: CharacterDao
) {
    fun getCharactersFlow(): Flow<List<CharacterEntity>> = dao.getAllCharacters()

    suspend fun fetchAndSaveCharacters(page: Int) {
        val characters = api.getCharacters(page).map { character ->
            CharacterEntity(
                name = character.name,
                culture = character.culture,
                born = character.born,
                titles = character.titles,
                aliases = character.aliases,
                playedBy = character.playedBy
            )
        }
        dao.insertCharacters(characters)
    }

    suspend fun refreshCharacters(page: Int) {
        dao.deleteAllCharacters()
        fetchAndSaveCharacters(page)
    }
}