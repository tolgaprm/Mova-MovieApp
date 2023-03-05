package com.prmto.mova_movieapp

import android.os.Bundle
import android.view.LayoutInflater
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.navigation.NavDestination
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.google.android.gms.ads.MobileAds
import com.google.firebase.FirebaseApp
import com.google.firebase.appcheck.FirebaseAppCheck
import com.google.firebase.appcheck.playintegrity.PlayIntegrityAppCheckProviderFactory
import com.prmto.mova_movieapp.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(LayoutInflater.from(this))
        setContentView(binding.root)

        FirebaseApp.initializeApp(this)
        val firebaseAppCheck = FirebaseAppCheck.getInstance()
        firebaseAppCheck.installAppCheckProviderFactory(
            PlayIntegrityAppCheckProviderFactory.getInstance()
        )
        MobileAds.initialize(this)
        val navHost =
            supportFragmentManager.findFragmentById(R.id.fragmentContainerView) as NavHostFragment

        val navController = navHost.navController

        binding.bottomBar?.setupWithNavController(navController)

        binding.navigationRail?.setupWithNavController(navController)

        navController.addOnDestinationChangedListener { _, destination, _ ->

            val isPreviousBackStackBottomBarDestinations =
                isVisibleBottomBarOrNavRail(navController.previousBackStackEntry?.destination)
            val isCurrentBackStackDetailBottomSheet = destination.id == R.id.detailBottomSheet

            val isVisibleBottomBarOrNavRailWhenOpenBottomDetail =
                isPreviousBackStackBottomBarDestinations && isCurrentBackStackDetailBottomSheet

            val isVisibleBottomBarOrNavigationRail = isVisibleBottomBarOrNavRail(destination)

            binding.navigationRail?.isVisible =
                isVisibleBottomBarOrNavigationRail || isVisibleBottomBarOrNavRailWhenOpenBottomDetail
            binding.bottomBar?.isVisible =
                isVisibleBottomBarOrNavigationRail || isVisibleBottomBarOrNavRailWhenOpenBottomDetail
        }
    }

    private fun isVisibleBottomBarOrNavRail(destination: NavDestination?): Boolean {
        if (destination == null) return false
        return when (destination.id) {
            R.id.homeFragment -> true
            R.id.exploreFragment -> true
            R.id.myListFragment -> true
            R.id.settingsFragment -> true
            else -> false
        }
    }

}













