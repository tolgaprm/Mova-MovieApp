package com.prmto.mova_movieapp.presentation.splash

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.prmto.mova_movieapp.R

import kotlinx.coroutines.*

class SplashFragment : Fragment(R.layout.fragment_splash) {

    private var coroutine: CoroutineScope? = null

    private var job: Job? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        coroutine = CoroutineScope(context = Dispatchers.Main)

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