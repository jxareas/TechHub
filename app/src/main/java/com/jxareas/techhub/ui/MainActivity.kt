package com.jxareas.techhub.ui

import android.os.Bundle
import android.view.animation.AccelerateDecelerateInterpolator
import androidx.appcompat.app.AppCompatActivity
import androidx.interpolator.view.animation.LinearOutSlowInInterpolator
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.jxareas.techhub.R
import com.jxareas.techhub.databinding.ActivityMainBinding
import com.jxareas.techhub.utils.extensions.getLong
import com.jxareas.techhub.utils.extensions.gone
import com.jxareas.techhub.utils.extensions.visible
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    lateinit var binding: ActivityMainBinding
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
        setupBottomNavigation()
    }

    private fun setupNavController() {
        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.nav_host_fragment_main) as NavHostFragment
        navController = navHostFragment.navController
    }

    private fun setupBottomNavigation() = binding.bottomNavigation.run bottomNav@{
        setupWithNavController(navController)
        // Hide Bottom Navigation View in screens that don't require it
        lifecycleScope.launchWhenResumed {
            navController.addOnDestinationChangedListener { _, destination, _ ->
                when (destination.id) {
                    in topLevelDestinations -> this@bottomNav
                        .animate()
                        .setInterpolator(LinearOutSlowInInterpolator())
                        .alpha(1.0f).withStartAction { visible() }
                        .duration = resources.getLong(R.integer.bottom_navigation_fade_in)
                    else -> this@bottomNav
                        .animate()
                        .setInterpolator(AccelerateDecelerateInterpolator())
                        .alpha(0.0f)
                        .withEndAction { gone() }
                        .duration = resources.getLong(R.integer.bottom_navigation_fade_out)
                }
            }
        }
    }

    override fun onSupportNavigateUp(): Boolean =
        navController.navigateUp() || super.onSupportNavigateUp()

}
