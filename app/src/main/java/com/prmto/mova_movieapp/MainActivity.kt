package com.prmto.mova_movieapp

import android.Manifest
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.view.View
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import androidx.navigation.NavDestination
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.google.android.gms.ads.MobileAds
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.FirebaseApp
import com.google.firebase.appcheck.FirebaseAppCheck
import com.google.firebase.appcheck.playintegrity.PlayIntegrityAppCheckProviderFactory
import com.prmto.mova_movieapp.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    private lateinit var permissionLauncher: ActivityResultLauncher<String>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        FirebaseApp.initializeApp(this)
        val firebaseAppCheck = FirebaseAppCheck.getInstance()
        firebaseAppCheck.installAppCheckProviderFactory(
            PlayIntegrityAppCheckProviderFactory.getInstance()
        )
        MobileAds.initialize(this)
        val navHost =
            supportFragmentManager.findFragmentById(R.id.fragmentContainerView) as NavHostFragment

        registerPermissionLaunch()
        checkPermission(binding.root)

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
            R.id.upComingFragment -> true
            R.id.myListFragment -> true
            R.id.settingsFragment -> true
            else -> false
        }
    }

    private fun registerPermissionLaunch() {
        permissionLauncher = registerForActivityResult(
            ActivityResultContracts.RequestPermission()
        ) {
            if (it) {
                return@registerForActivityResult
            }
        }
    }

    private fun checkPermission(view: View) {
        if (Build.VERSION.SDK_INT >= 33) {
            val permissionRequest = listOf(
                Manifest.permission.POST_NOTIFICATIONS,
            )

            permissionRequest.forEach { permission ->
                if (ContextCompat.checkSelfPermission(
                        this,
                        permission
                    ) != PackageManager.PERMISSION_GRANTED
                ) {
                    if (ActivityCompat.shouldShowRequestPermissionRationale(
                            this,
                            permission
                        )
                    ) {
                        Snackbar.make(
                            view,
                            getString(R.string.it_is_necc_for_notification),
                            Snackbar.LENGTH_INDEFINITE
                        ).setAction(R.string.allow) {
                            permissionLauncher.launch(permission)
                        }.show()
                    }
                    permissionLauncher.launch(permission)
                } else {
                    return
                }
            }
        }
    }
}













