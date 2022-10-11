package com.prmto.mova_movieapp.presentation.splash

import android.os.Bundle
import android.view.View
import androidx.core.os.ConfigurationCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.prmto.mova_movieapp.R
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.*
import timber.log.Timber

@AndroidEntryPoint
class SplashFragment : Fragment(R.layout.fragment_splash) {

    private val viewModel: SplashViewModel by viewModels()

    private var coroutine: CoroutineScope? = null

    private var job: Job? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        coroutine = CoroutineScope(context = Dispatchers.Main)

        val locale =
            ConfigurationCompat.getLocales(requireContext().resources.configuration)[0]?.country.toString()
        Timber.d(locale)


        viewModel.updateLocale(locale = locale)

        job = coroutine?.launch {
            delay(2000)
            findNavController().navigate(SplashFragmentDirections.actionToHomeFragment())
        }


    }

    override fun onDestroyView() {
        super.onDestroyView()
        job?.cancel()
        coroutine = null
    }


}