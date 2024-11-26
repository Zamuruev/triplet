package com.triplet
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.triplet.databinding.HomeFragmentBinding
import kotlinx.coroutines.launch

class HomeFragment : Fragment() {
    private lateinit var binding: HomeFragmentBinding
    private val repository = CharacterRepository(RetrofitInstance.api)

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = HomeFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.recyclerView.layoutManager = LinearLayoutManager(requireContext())

        lifecycleScope.launch {
            val characters = repository.fetchCharacters(page = 9)
            binding.recyclerView.adapter = CharacterAdapter(characters)
        }
    }
}
