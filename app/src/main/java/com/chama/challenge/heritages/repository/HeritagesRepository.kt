package com.chama.challenge.heritages.repository

import com.chama.challenge.heritages.model.Heritage

interface HeritagesRepository {
    suspend fun getHeritages(page: Int = 1): List<Heritage>
}

class HeritagesJsonRepository(
    private val provider: DataProvider
) : HeritagesRepository {

    override suspend fun getHeritages(page: Int): List<Heritage> =
        provider.getHeritages(page)
}