package com.prmto.core_ui.base.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import androidx.navigation.fragment.findNavController
import androidx.viewbinding.ViewBinding
import com.google.android.material.snackbar.Snackbar
import com.prmto.core_domain.util.UiText
import com.prmto.core_domain.util.asString
import com.prmto.navigation.NavigateFlow
import com.prmto.navigation.ToFlowNavigatable

abstract class BaseFragment<VB : ViewBinding, VM : ViewModel>(
    private val inflater: (LayoutInflater, ViewGroup?, Boolean) -> VB
) :
    Fragment() {

    private var _binding: VB? = null
    protected val binding get() = _binding!!

    protected abstract val viewModel: VM

    abstract fun onInitialize()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = inflater(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        onInitialize()
    }

    protected fun showSnackbar(uiText: UiText) {
        Snackbar.make(
            requireView(),
            uiText.asString(requireContext()),
            Snackbar.LENGTH_SHORT
        ).show()
    }

    protected fun addOnBackPressedCallback(
        addCustomBehavior: (() -> Unit?)? = null
    ) {
        val callback = object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                if (addCustomBehavior != null) {
                    addCustomBehavior()
                } else {
                    findNavController().popBackStack()
                }
            }
        }
        requireActivity().onBackPressedDispatcher.addCallback(callback)
    }

    fun navigateToFlow(navigationFlow: NavigateFlow) {
        (requireActivity() as? ToFlowNavigatable)?.navigateToFlow(navigationFlow)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}