package com.prmto.mova_movieapp

import android.Manifest
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.view.View
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
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
        checkNotificationPermissions(binding.root)

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

    private fun checkNotificationPermissions(view: View) {

        if (Build.VERSION.SDK_INT >= 33) {
            if (checkSelfPermission(Manifest.permission.POST_NOTIFICATIONS) != PackageManager.PERMISSION_GRANTED) {
                handlePermissionRequest(
                    permission = Manifest.permission.POST_NOTIFICATIONS
                )
            }
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            if (checkSelfPermission(Manifest.permission.SCHEDULE_EXACT_ALARM) != PackageManager.PERMISSION_GRANTED) {
                handlePermissionRequest(
                    permission = Manifest.permission.SCHEDULE_EXACT_ALARM
                )
            }
        }
    }

    private fun handlePermissionRequest(
        permission: String,
        permissionExplanation: String = getString(R.string.it_is_necc_for_notification)
    ) {
        if (shouldShowRequestPermissionRationale(permission)) {
            showPermissionSnackbar(
                permission = permission,
                permissionExplanation = permissionExplanation
            )
        }
        permissionLauncher.launch(permission)
    }

    private fun showPermissionSnackbar(
        permission: String,
        permissionExplanation: String
    ) {
        val permissionAction = getString(R.string.allow)
        Snackbar.make(binding.root, permissionExplanation, Snackbar.LENGTH_INDEFINITE)
            .setAction(permissionAction) { permissionLauncher.launch(permission) }
            .show()
    }
}