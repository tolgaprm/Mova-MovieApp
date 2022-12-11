package com.prmto.mova_movieapp.presentation.splash

import androidx.core.view.isVisible
import androidx.navigation.Navigation
import androidx.navigation.testing.TestNavHostController
import androidx.test.core.app.ApplicationProvider
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

@ExperimentalCoroutinesApi
@HiltAndroidTest
class SplashFragmentTest {


    @get:Rule
    val hiltRule = HiltAndroidRule(this)

    private lateinit var navController: TestNavHostController

    @Before
    fun setup() {
        hiltRule.inject()
        navController = TestNavHostController(
            ApplicationProvider.getApplicationContext()
        )

        launchFragmentInHiltContainer<SplashFragment> {
            Navigation.setViewNavController(requireView(), navController)
            navController.setGraph(R.navigation.nav_graph)
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