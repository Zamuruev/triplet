package com.triplet

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.triplet.databinding.ItemCharacterBinding
import com.triplet.models.CharacterModel

class CharacterAdapter(private val characters: List<CharacterModel>) :
    RecyclerView.Adapter<CharacterAdapter.CharacterViewHolder>() {

    class CharacterViewHolder(private val binding: ItemCharacterBinding) :
        RecyclerView.ViewHolder(binding.root) {
        @SuppressLint("SetTextI18n")
        fun bind(character: CharacterModel) {
            binding.name.text = character.name.ifEmpty { "Unknown" }
            binding.culture.text = "Culture: ${character.culture.ifEmpty { "Unknown" }}"
            binding.born.text = "Born: ${character.born.ifEmpty { "Unknown" }}"
            binding.titles.text = "Titles: ${character.titles.joinToString()}"
            binding.aliases.text = "Aliases: ${character.aliases.joinToString()}"
            binding.playedBy.text = "Played by: ${character.playedBy.joinToString()}"
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CharacterViewHolder {
        val binding =
            ItemCharacterBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CharacterViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CharacterViewHolder, position: Int) {
        holder.bind(characters[position])
    }

    override fun getItemCount() = characters.size
}
