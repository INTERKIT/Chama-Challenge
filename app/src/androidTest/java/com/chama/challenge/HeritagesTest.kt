package com.chama.challenge

import android.content.Intent
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.intent.rule.IntentsTestRule
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withText
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.chama.challenge.heritages.domain.HeritagesInteractor
import com.chama.challenge.heritages.model.Heritage
import com.chama.challenge.heritages.ui.HeritagesViewModel
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.flow.flow
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.context.loadKoinModules
import org.koin.dsl.module

@RunWith(AndroidJUnit4::class)
class HeritagesTest {

    @get:Rule
    internal val intentsTestRule = IntentsTestRule(MainActivity::class.java, false, false)

    private lateinit var interactor: HeritagesInteractor

    @Before
    fun setUp() {
        interactor = mockk()

        loadKoinModules(
            module {
                single(override = true) { interactor }
                viewModel(override = true) { HeritagesViewModel(interactor) }
            }
        )
    }

    @After
    fun tearDown() {
    }

    @Test
    fun showHeritagesList() {
        every { interactor.getAllOperations() } returns flow { emit(getData()) }

        startActivity()

        onView(withText(name)).check(matches(isDisplayed()))
    }

    private fun startActivity() {
        val intent = Intent()
        intentsTestRule.launchActivity(intent)
    }

    private val name: String = "Somename"

    private fun getData() = listOf(
        Heritage(
            "id",
            name,
            2000,
            "target",
            "r",
            "id",
            "id",
            1.2,
            1.0,
            "page",
            "image",
            "short",
            "longinfo"
        )
    )
}