package com.triplet

import MainActivity
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.triplet.databinding.SignInFragmentBinding
import com.triplet.models.User

class SignInFragment : Fragment(R.layout.sign_in_fragment) {
    companion object {
        private const val TAG = "SignInFragment"
    }

    private var _binding: SignInFragmentBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = SignInFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        activity?.window?.decorView?.systemUiVisibility = View.SYSTEM_UI_FLAG_LAYOUT_STABLE or
                View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
        activity?.window?.statusBarColor = resources.getColor(R.color.fon)

        val args = SignInFragmentArgs.fromBundle(requireArguments())
        val newUser = args.newUser

        newUser?.let {
            binding.loginEmailForm.setText(it.email)
            binding.loginPasswordForm.setText(it.password)
        }

        binding.loginButtonForm.setOnClickListener {
            val email = binding.loginEmailForm.text.toString()
            val password = binding.loginPasswordForm.text.toString()

            if (validateForm(email, password)) {
                (activity as? MainActivity)?.navigateToHome()
            } else {
                Toast.makeText(requireContext(), "Invalid email or password", Toast.LENGTH_SHORT).show()
            }
        }

        binding.backBtn.setOnClickListener {
            (activity as? MainActivity)?.navigateToOnboard()
        }

        binding.signUpBtn.setOnClickListener {
            (activity as? MainActivity)?.navigateToSignUp()
        }

        Log.d(TAG, "onViewCreated called")
    }

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
