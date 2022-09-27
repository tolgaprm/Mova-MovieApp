package com.prmto.mova_movieapp.presentation.splash

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.core.view.isVisible
import androidx.fragment.app.testing.FragmentScenario
import androidx.fragment.app.testing.launchFragmentInContainer
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.matcher.ViewMatchers.withId
import com.prmto.mova_movieapp.R
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

@ExperimentalCoroutinesApi
@RunWith(JUnit4::class)
class SplashFragmentTest {

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    lateinit var scenario: FragmentScenario<SplashFragment>

    @Before
    fun setup() {
        scenario = launchFragmentInContainer<SplashFragment>()
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