package com.triplet

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.triplet.databinding.HomeFragmentBinding
import kotlinx.coroutines.launch

import androidx.navigation.fragment.findNavController
import com.triplet.models.CharacterModel
import kotlinx.coroutines.flow.collectLatest

class HomeFragment : Fragment() {

    private lateinit var binding: HomeFragmentBinding
    private val viewModel: CharacterViewModel by viewModels {
        CharacterViewModelFactory(
            CharacterRepository(
                RetrofitInstance.api,
                (requireContext().applicationContext as MyApplication).database.characterDao()
            )
        )
    }

    private var currentPage = 1

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = HomeFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val adapter = CharacterAdapter(emptyList())
        binding.recyclerView.layoutManager = LinearLayoutManager(requireContext())
        binding.recyclerView.adapter = adapter

        lifecycleScope.launch {
            viewModel.characters.collectLatest { characters ->
                adapter.updateCharacters(characters.map {
                    CharacterModel(
                        name = it.name,
                        culture = it.culture,
                        born = it.born,
                        titles = it.titles,
                        aliases = it.aliases,
                        playedBy = it.playedBy
                    )
                })
            }
        }

        viewModel.fetchCharactersIfEmpty(page = currentPage)

        binding.previousPageButton.setOnClickListener {
            if (currentPage > 1) {
                currentPage--
                viewModel.refreshCharacters(page = currentPage)
            }
        }

        binding.nextPageButton.setOnClickListener {
            currentPage++
            viewModel.refreshCharacters(page = currentPage)
        }
        binding.settingsButton.setOnClickListener {
            findNavController().navigate(R.id.action_homeFragment_to_settingsFragment)
        }
    }
}