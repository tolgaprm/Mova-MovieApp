package com.prmto.mova_movieapp

import android.os.Bundle
import android.view.LayoutInflater
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.prmto.mova_movieapp.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(LayoutInflater.from(this))
        setContentView(binding.root)
        val navHost =
            supportFragmentManager.findFragmentById(R.id.fragmentContainerView) as NavHostFragment

        val navController = navHost.navController

        binding.bottomBar.setupWithNavController(navController)
        navController.addOnDestinationChangedListener { _, destination, _ ->

            val isVisibleBottomBar = when (destination.id) {
                R.id.homeFragment -> true
                R.id.exploreFragment -> true
                R.id.myListFragment -> true
                R.id.settingsFragment -> true
                else -> false
            }

            binding.bottomBar.isVisible = isVisibleBottomBar
        }
    }
}











