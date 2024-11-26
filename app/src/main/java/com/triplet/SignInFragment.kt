package com.triplet

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.triplet.databinding.SignInFragmentBinding
import com.triplet.models.User

class SignInFragment : Fragment() {

    private var _binding: SignInFragmentBinding? = null
    private val binding get() = _binding!!

    private val TAG = "SignInFragment"

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = SignInFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val newUser = arguments?.getSerializable("user") as? User

        newUser?.let { user ->
            binding.loginEmailForm.setText(user.email)
            binding.loginPasswordForm.setText(user.password)
        }

        binding.loginButtonForm.setOnClickListener {
            val email = binding.loginEmailForm.text.toString()
            val password = binding.loginPasswordForm.text.toString()
            if (validateForm(email, password)) {
                findNavController().navigate(SignInFragmentDirections.actionSignInFragmentToHomeFragment())
            } else {
                Toast.makeText(requireContext(), "Invalid email or password", Toast.LENGTH_SHORT).show()
            }
        }

        binding.backBtn.setOnClickListener {
            findNavController().navigate(SignInFragmentDirections.actionSignInFragmentToOnboardFragment())
        }

        binding.signUpBtn.setOnClickListener {
            findNavController().navigate(SignInFragmentDirections.actionSignInFragmentToSignUpFragment())
        }

        Log.d(TAG, "onViewCreated called")
    }

    private fun validateForm(email: String, password: String): Boolean {
        val emailPattern = "[a-zA-Z0-9._-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}"
        return email.isNotEmpty() && password.isNotEmpty() && email.matches(emailPattern.toRegex())
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
        Log.d(TAG, "onDestroyView called")
    }
}
