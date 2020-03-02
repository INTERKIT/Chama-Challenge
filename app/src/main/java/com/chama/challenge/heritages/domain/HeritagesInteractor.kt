package com.chama.challenge.heritages.domain

import com.chama.challenge.heritages.model.Heritage
import com.chama.challenge.heritages.repository.HeritagesLocalRepository
import com.chama.challenge.heritages.repository.HeritagesRepository
import kotlinx.coroutines.flow.Flow

class HeritagesInteractor(
    private val repository: HeritagesRepository,
    private val localRepository: HeritagesLocalRepository
) {

    fun getAllOperations(): Flow<List<Heritage>> = localRepository.getAllFlow()

    suspend fun loadHeritages(page: Int) {
        val heritages = repository.getHeritages(page)
        localRepository.insertOrUpdateHeritages(heritages)
    }

    suspend fun reloadHeritages() {
        val heritages = repository.getHeritages()
        localRepository.clear()
        localRepository.insertOrUpdateHeritages(heritages)
    }
}