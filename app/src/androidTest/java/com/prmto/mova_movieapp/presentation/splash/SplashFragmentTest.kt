package com.prmto.mova_movieapp.presentation.splash

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.core.view.isVisible
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.matcher.ViewMatchers.withId
import com.prmto.mova_movieapp.R
import com.prmto.mova_movieapp.launchFragmentInHiltContainer
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mockito

@ExperimentalCoroutinesApi
@HiltAndroidTest
class SplashFragmentTest {

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    val hiltRule = HiltAndroidRule(this)

    lateinit var navController: NavController

    @Before
    fun setup() {
        hiltRule.inject()
        navController = Mockito.mock(NavController::class.java)
        launchFragmentInHiltContainer<SplashFragment> {
            Navigation.setViewNavController(requireView(), navController)
        }
    }

    @Test
    fun splashScreen_text_displayed() {
        onView(withId(R.id.splash_text)).check { view, _ ->
            view.isVisible
        }
    }


    @Test
    fun splashScreen_image_displayed() {
        onView(withId(R.id.splash_image)).check { view, _ ->
            view.isVisible
        }
    }

}