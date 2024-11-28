package com.triplet

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.triplet.databinding.SignUpFragmentBinding
import com.triplet.models.User
import androidx.navigation.fragment.findNavController

class SignUpFragment : Fragment() {

    private var _binding: SignUpFragmentBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = SignUpFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.signInBtn.setOnClickListener {
           findNavController().navigate(R.id.signInFragment)
        }

        binding.submitBtn.setOnClickListener {
            val firstName = binding.firstNameField.text.toString()
            val lastName = binding.lastNameField.text.toString()
            val email = binding.emailField.text.toString()
            val password = binding.passwordField.text.toString()
            val repeatPassword = binding.repeatPasswordField.text.toString()

            if (firstName.isNotEmpty() && lastName.isNotEmpty() &&
                email.isNotEmpty() && password.isNotEmpty() &&
                email.contains("@") && (email.contains(".com") || email.contains(".ru") && email.length != 4) &&
                password == repeatPassword) {

                val newUser = User(firstName, lastName, email, password)
                val action = SignUpFragmentDirections.actionSignUpFragmentToSignInFragment(newUser)

                findNavController().navigate(action)
            } else {
                Toast.makeText(requireContext(), "Please fill in all fields correctly.", Toast.LENGTH_SHORT).show()
            }
        }

    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
