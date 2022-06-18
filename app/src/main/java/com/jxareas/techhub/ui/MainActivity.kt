package com.jxareas.techhub.ui

import android.os.Bundle
import android.transition.AutoTransition
import android.transition.TransitionManager
import android.view.animation.LinearInterpolator
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.WindowCompat
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import com.jxareas.techhub.R
import com.jxareas.techhub.databinding.ActivityMainBinding
import com.jxareas.techhub.extensions.getLong
import com.jxareas.techhub.extensions.gone
import com.jxareas.techhub.extensions.visible
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var navController: NavController
    private val topLevelDestinations = setOf(
        R.id.coursesFragment,
        R.id.myCoursesFragment,
        R.id.searchCoursesFragment
    )



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        WindowCompat.setDecorFitsSystemWindows(window, false)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setupNavController()
    }

    private fun setupNavController() {
        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.nav_host_fragment_main) as NavHostFragment
        navController = navHostFragment.navController

        // Hide Bottom Navigation View in screens that don't require it
        lifecycleScope.launchWhenResumed {
            navController.addOnDestinationChangedListener { _, destination, _ ->
                when (destination.id) {
                    in topLevelDestinations -> {
                        val transition = AutoTransition().apply {
                            duration = resources.getLong(
                                com.google.android.material.R.integer.material_motion_duration_long_1
                            )
                            interpolator = LinearInterpolator()
                        }
                        TransitionManager.beginDelayedTransition(binding.root, transition)
                        binding.bottomNavigation.visible()
                    }
                    else -> {
                        binding.bottomNavigation.gone()
                    }
                }
            }
        }
    }

    override fun onSupportNavigateUp(): Boolean =
        navController.navigateUp() || super.onSupportNavigateUp()

}