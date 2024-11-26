package com.triplet.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class CharacterModel(
    val name: String = "",
    val culture: String = "",
    val born: String = "",
    val titles: List<String> = emptyList(),
    val aliases: List<String> = emptyList(),
    @SerialName("playedBy") val playedBy: List<String> = emptyList()
)
