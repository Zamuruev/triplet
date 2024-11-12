import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import com.triplet.R
import com.triplet.databinding.ActivityMainBinding
import com.triplet.models.User

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val navHostFragment = supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        navController = navHostFragment.navController
    }

    fun navigateToSignIn(user: User? = null) {
        val action = OnboardFragmentDirections.actionOnboardFragmentToSignInFragment(user)
        findNavController().navigate(action)

    }

    fun navigateToHome() {
        navController.navigate(R.id.action_signInFragment_to_homeFragment)
    }

    fun navigateToOnboard() {
        navController.navigate(R.id.action_signInFragment_to_onboardFragment)
    }

    fun navigateToSignUp() {
        navController.navigate(R.id.action_onboardFragment_to_signUpFragment)
    }
}
