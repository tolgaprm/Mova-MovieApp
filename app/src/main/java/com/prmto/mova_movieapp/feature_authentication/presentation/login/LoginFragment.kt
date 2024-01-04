package com.prmto.mova_movieapp.feature_authentication.presentation.login

import android.app.Activity
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.view.isVisible
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.prmto.authentication_ui.databinding.FragmentLoginBinding
import com.prmto.authentication_ui.login.LoginEvent
import com.prmto.authentication_ui.util.updateFieldEmptyError
import com.prmto.core_ui.base.fragment.BaseFragmentWithUiEvent
import com.prmto.core_ui.util.collectFlow
import com.prmto.mova_movieapp.R
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LoginFragment :
    BaseFragmentWithUiEvent<FragmentLoginBinding, LoginViewModel>(
        inflater = FragmentLoginBinding::inflate
    ) {

    private lateinit var googleSignInClient: GoogleSignInClient
    override val viewModel: LoginViewModel by viewModels()

    private val launcher =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == Activity.RESULT_OK) {
                val task = GoogleSignIn.getSignedInAccountFromIntent(result.data)
                viewModel.onEvent(LoginEvent.SignInWithGoogle(task))
            }
        }

    override fun onInitialize() {
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.default_web_client_id))
            .requestEmail()
            .build()

        googleSignInClient = GoogleSignIn.getClient(requireContext(), gso)
        collectData()
        addOnBackPressedCallback()
        setClickListeners()
    }

    private fun signInWithGoogle() {
        val signInIntent = googleSignInClient.signInIntent
        launcher.launch(signInIntent)
    }

    private fun collectData() {
        collectLoginUiEvent()
        collectUiState()
    }

    private fun collectLoginUiEvent() {
        collectFlow(viewModel.consumableViewEvents) { listOfUiEvents ->
            handleUiEvent(
                listOfUiEvent = listOfUiEvents,
                onEventConsumed = viewModel::onEventConsumed
            )
        }
    }

    private fun collectUiState() {
        collectFlow(viewModel.uiState) { uiState ->
            binding.layoutEmail.updateFieldEmptyError(
                authError = uiState.emailState.error
            )
            binding.layoutPassword.updateFieldEmptyError(
                authError = uiState.passwordState.error
            )

            binding.edtEmail.isEnabled = !uiState.isLoading
            binding.edtPassword.isEnabled = !uiState.isLoading
            binding.btnSignIn.isEnabled = !uiState.isLoading
            binding.btnSignInGoogle.isEnabled = !uiState.isLoading
            binding.progressBar.isVisible = uiState.isLoading
        }
    }

    private fun setClickListeners() {
        binding.edtEmail.addTextChangedListener {
            it?.let {
                viewModel.onEvent(LoginEvent.EnteredEmail(it.toString()))
            }
        }

        binding.edtPassword.addTextChangedListener {
            it?.let {
                viewModel.onEvent(LoginEvent.EnteredPassword(it.toString()))
            }
        }

        binding.txtForgotPassword.setOnClickListener {
            viewModel.onEvent(LoginEvent.ClickedForgetPassword)
        }

        binding.btnSignIn.setOnClickListener {
            viewModel.onEvent(LoginEvent.SignIn)
        }

        binding.txtSignUp.setOnClickListener {
            findNavController().navigate(
                LoginFragmentDirections.actionLoginFragmentToSignUpFragment()
            )
        }

        binding.btnSignInGoogle.setOnClickListener {
            signInWithGoogle()
        }
    }
}