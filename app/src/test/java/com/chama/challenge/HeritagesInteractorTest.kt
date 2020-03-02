package com.chama.challenge

import com.chama.challenge.heritages.domain.HeritagesInteractor
import com.chama.challenge.heritages.model.Heritage
import com.chama.challenge.heritages.repository.HeritagesLocalRepository
import com.chama.challenge.heritages.repository.HeritagesRepository
import io.mockk.Runs
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.coVerifyOrder
import io.mockk.every
import io.mockk.just
import io.mockk.mockk
import io.mockk.verify
import io.mockk.verifyOrder
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test

class HeritagesInteractorTest {

    private lateinit var repository: HeritagesRepository
    private lateinit var localRepository: HeritagesLocalRepository
    private lateinit var heritagesInteractor: HeritagesInteractor

    @Before
    fun setUp() {
        repository = mockk()
        localRepository = mockk()

        heritagesInteractor = HeritagesInteractor(repository, localRepository)
    }

    @Test
    fun `load heritages - success`() = runBlocking {
        coEvery { repository.getHeritages(any()) } returns getData()
        coEvery { localRepository.insertOrUpdateHeritages(getData()) } just Runs

        heritagesInteractor.loadHeritages(1)

        coVerifyOrder {
            repository.getHeritages(any())
            localRepository.insertOrUpdateHeritages(getData())
        }
    }

    @Test
    fun `load heritages - error`() = runBlocking {
        coEvery { repository.getHeritages(any()) } throws IllegalStateException("Error")

        try {
            heritagesInteractor.loadHeritages(1)
        } catch (e: Throwable) {
            assert(e is IllegalStateException)
        }

        coVerify {
            repository.getHeritages()
        }

        coVerify(exactly = 0) {
            localRepository.insertOrUpdateHeritages(any())
        }
    }

    @Test
    fun `reload heritages - success`() = runBlocking {
        coEvery { repository.getHeritages(any()) } returns getData()
        every { localRepository.clear() } just Runs
        coEvery { localRepository.insertOrUpdateHeritages(getData()) } just Runs

        heritagesInteractor.reloadHeritages()

        coVerifyOrder {
            repository.getHeritages(any())
            localRepository.clear()
            localRepository.insertOrUpdateHeritages(getData())
        }
    }

    private fun getData() = listOf(
        Heritage(
            "id",
            "name",
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