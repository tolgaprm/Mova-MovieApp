package com.prmto.mova_movieapp.feature_settings.presentation.setting

import android.view.View
import android.widget.AdapterView
import android.widget.AdapterView.OnItemSelectedListener
import android.widget.ArrayAdapter
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.os.LocaleListCompat
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import com.prmto.core_domain.models.supportedLanguages
import com.prmto.mova_movieapp.R
import com.prmto.mova_movieapp.core.presentation.base.fragment.BaseFragmentWithUiEvent
import com.prmto.mova_movieapp.core.presentation.util.AlertDialogUtil
import com.prmto.mova_movieapp.core.presentation.util.collectFlow
import com.prmto.mova_movieapp.core.presentation.util.loadAd
import com.prmto.mova_movieapp.databinding.FragmentSettingsBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SettingsFragment : BaseFragmentWithUiEvent<FragmentSettingsBinding, SettingsViewModel>(
    inflater = FragmentSettingsBinding::inflate
) {

    override val viewModel: SettingsViewModel by viewModels()

    override fun onInitialize() {
        binding.adView.loadAd()
        collectUiState()
        collectUiEvent()
        collectUIMode()
        collectLanguageIsoCode()
        setListenerSwitch()
        setSpinnerAdapter()
        addSpinnerOnItemSelectedListener()
        addTxtLogoutClickListener()
    }

    private fun setSpinnerAdapter() {
        val adapter = ArrayAdapter(
            requireContext(),
            android.R.layout.simple_spinner_dropdown_item,
            supportedLanguages.map { requireContext().getString(it.textId) }
        )

        binding.spinner.adapter = adapter
    }

    private fun collectUiState() {
        collectFlow(viewModel.state) { uiState ->
            binding.progressBar.isVisible = uiState.isLoading
            binding.txtLogOut.isVisible = uiState.isSignedIn
        }
    }

    private fun collectUiEvent() {
        collectFlow(viewModel.consumableViewEvents) { event ->
            handleUiEvent(
                listOfUiEvent = event,
                onEventConsumed = viewModel::onEventConsumed
            )
        }
    }

    private fun collectUIMode() {
        collectFlow(viewModel.getUIMode()) { uiMode ->
            binding.switchDarkTheme.isChecked =
                uiMode == AppCompatDelegate.MODE_NIGHT_YES
        }
    }

    private fun collectLanguageIsoCode() {
        collectFlow(viewModel.getLanguageIsoCode()) { langIsoCode ->
            val selectedLangIsoIndex = supportedLanguages.indexOfFirst {
                it.iso == langIsoCode
            }
            binding.spinner.setSelection(selectedLangIsoIndex)
        }
    }

    private fun addSpinnerOnItemSelectedListener() {
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
    }

    private fun addTxtLogoutClickListener() {
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
}