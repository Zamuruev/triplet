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
    // Это вещь можно было сделать без объекта-компаньона
//    companion object {
//        private const val TAG = "SignInFragment"
//    }

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

//        activity?.window?.decorView?.systemUiVisibility = View.SYSTEM_UI_FLAG_LAYOUT_STABLE or
//                View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
//        activity?.window?.statusBarColor = resources.getColor(R.color.fon)

        // Извлекаем объект User из аргументов
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
//                (activity as? MainActivity)?.navigateToHome()
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
    // Вот этот валидатор хуйня, в Котлине есть паттерны)))
    // Patterns.EMAIL_ADDRESS.matcher(input).matches(), где input - это почта пользователя
    private fun validateForm(email: String, password: String): Boolean {
        return email.isNotEmpty() && password.isNotEmpty() && email.contains("@") &&
                (email.contains(".com") || email.contains(".ru") && email.length != 4)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
        Log.d(TAG, "onDestroyView called")
    }
}
