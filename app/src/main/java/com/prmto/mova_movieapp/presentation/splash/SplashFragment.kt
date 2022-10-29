package com.prmto.mova_movieapp.presentation.splash

import android.os.Bundle
import android.view.View
import androidx.core.os.ConfigurationCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import com.prmto.mova_movieapp.R
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class SplashFragment : Fragment(R.layout.fragment_splash) {

    lateinit var viewModel: SplashViewModel


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = ViewModelProvider(this)[SplashViewModel::class.java]

        val locale =
            ConfigurationCompat.getLocales(requireContext().resources.configuration)[0]?.country.toString()

        viewModel.updateLocale(locale = locale)

        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.navigateToHomeFragment()
                viewModel.isNavigateToHomeFragment.collect {
                    if (it) {
                        findNavController().navigate(SplashFragmentDirections.actionToHomeFragment())
                    }
                }
            }
        }


    }
}