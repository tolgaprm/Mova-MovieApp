package com.prmto.mova_movieapp.feature_settings.presentation.setting

import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.AdapterView.OnItemSelectedListener
import android.widget.ArrayAdapter
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.os.LocaleListCompat
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.google.android.gms.ads.AdRequest
import com.google.android.material.snackbar.Snackbar
import com.prmto.mova_movieapp.R
import com.prmto.mova_movieapp.core.domain.supportedLanguages
import com.prmto.mova_movieapp.core.presentation.util.AlertDialogUtil
import com.prmto.mova_movieapp.core.presentation.util.BaseUiEvent
import com.prmto.mova_movieapp.core.presentation.util.asString
import com.prmto.mova_movieapp.databinding.FragmentSettingsBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class SettingsFragment : Fragment(R.layout.fragment_settings) {

    private var _binding: FragmentSettingsBinding? = null
    private val binding get() = _binding!!
    private val viewModel: SettingsViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentSettingsBinding.bind(view)

        val adRequest = AdRequest.Builder().build()
        binding.adView.loadAd(adRequest)

        collectDataLifecycleAware()
        setListenerSwitch()
        val adapter = ArrayAdapter(
            requireContext(),
            android.R.layout.simple_spinner_dropdown_item,
            supportedLanguages.map { requireContext().getString(it.textId) }
        )

        binding.spinner.adapter = adapter

        binding.spinner.onItemSelectedListener = object : OnItemSelectedListener {
            override fun onItemSelected(
                adapterView: AdapterView<*>?,
                p1: View?,
                position: Int,
                p3: Long
            ) {

                val isoCode = supportedLanguages[position].iso
                viewModel.updateLanguageIsoCode(isoCode)
                AppCompatDelegate.setApplicationLocales(LocaleListCompat.forLanguageTags(isoCode))
            }

            override fun onNothingSelected(p0: AdapterView<*>?) {
            }

        }

        binding.txtLogOut.setOnClickListener {
            AlertDialogUtil.showAlertDialog(
                context = requireContext(),
                title = R.string.are_you_sure_log_out,
                message = R.string.log_out_message,
                positiveBtnMessage = R.string.log_out,
                negativeBtnMessage = R.string.cancel,
                onClickPositiveButton = {
                    viewModel.logOut()
                }
            )
        }

    }

    private fun setListenerSwitch() {
        binding.switchDarkTheme.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                // Update uiMode to DarkTheme
                updateUIMode(AppCompatDelegate.MODE_NIGHT_YES)
            } else {
                // Update uiMode to LightTheme
                updateUIMode(AppCompatDelegate.MODE_NIGHT_NO)
            }
        }
    }

    private fun updateUIMode(uiMode: Int) {
        viewModel.updateUIMode(uiMode)
        AppCompatDelegate.setDefaultNightMode(uiMode)
    }

    private fun collectDataLifecycleAware() {
        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                launch {
                    viewModel.isLoading.collectLatest { isLoaading ->
                        binding.progressBar.isVisible = isLoaading
                    }
                }
                launch { collectUiEvent() }
                launch { collectUIMode() }
                launch { collectLanguageIsoCode() }
                launch {
                    viewModel.isSignedIn.collectLatest { isUserSignIn ->
                        binding.txtLogOut.isVisible = isUserSignIn
                    }
                }
            }
        }
    }

    private suspend fun collectUiEvent() {
        viewModel.eventFlow.collectLatest { event ->
            when (event) {
                is BaseUiEvent.ShowSnackbar -> {
                    Snackbar.make(
                        requireView(),
                        event.uiText.asString(requireContext()),
                        Snackbar.LENGTH_SHORT
                    ).show()
                }
                else -> return@collectLatest
            }
        }
    }

    private suspend fun collectUIMode() {
        viewModel.getUIMode().collectLatest { uiMode ->
            binding.switchDarkTheme.isChecked =
                uiMode == AppCompatDelegate.MODE_NIGHT_YES
        }
    }

    private suspend fun collectLanguageIsoCode() {
        viewModel.getLanguageIsoCode().collectLatest { langIsoCode ->
            val selectedLangIsoIndex = supportedLanguages.indexOfFirst {
                it.iso == langIsoCode
            }
            binding.spinner.setSelection(selectedLangIsoIndex)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}