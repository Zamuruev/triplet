package com.triplet

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class CharacterViewModel(private val repository: CharacterRepository) : ViewModel() {

    val characters: StateFlow<List<CharacterEntity>> = repository.getCharactersFlow()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(), emptyList())

    fun fetchCharactersIfEmpty(page: Int) {
        viewModelScope.launch {
            if (characters.value.isEmpty()) {
                repository.fetchAndSaveCharacters(page)
            }
        }
    }

    fun refreshCharacters(page: Int) {
        viewModelScope.launch {
            repository.refreshCharacters(page)
        }
    }
}
