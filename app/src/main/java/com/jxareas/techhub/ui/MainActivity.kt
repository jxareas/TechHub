package com.jxareas.techhub.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.jxareas.techhub.R
import com.jxareas.techhub.databinding.ActivityMainBinding
import com.jxareas.techhub.utils.extensions.getLong
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var navController: NavController
    private val topLevelDestinations = setOf(
        R.id.coursesFragment,
        R.id.favoriteCoursesFragment,
        R.id.searchCoursesFragment
    )


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setupNavController()
    }

    private fun setupNavController() {
        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.nav_host_fragment_main) as NavHostFragment
        navController = navHostFragment.navController
        binding.bottomNavigation.setupWithNavController(navController)

        // Hide Bottom Navigation View in screens that don't require it
        lifecycleScope.launchWhenResumed {
            navController.addOnDestinationChangedListener { _, destination, _ ->
                when (destination.id) {
                    in topLevelDestinations -> binding.bottomNavigation
                        .animate()
                        .alpha(1.0f)
                        .duration = resources.getLong(R.integer.material_motion_duration_long_3)
                    else -> binding.bottomNavigation
                        .animate()
                        .alpha(0.0f)
                        .duration = resources.getLong(R.integer.material_motion_duration_long_3)
                }
            }
        }
    }

    override fun onSupportNavigateUp(): Boolean =
        navController.navigateUp() || super.onSupportNavigateUp()

}
