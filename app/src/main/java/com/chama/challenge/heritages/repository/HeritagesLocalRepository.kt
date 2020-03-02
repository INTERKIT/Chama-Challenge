package com.chama.challenge.heritages.repository

import com.chama.challenge.heritages.model.Heritage
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.asCoroutineDispatcher
import kotlinx.coroutines.channels.BroadcastChannel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.asFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.withContext
import java.util.concurrent.Executors

interface HeritagesLocalRepository {
    fun getAllFlow(): Flow<List<Heritage>>
    suspend fun insertOrUpdateHeritages(items: List<Heritage>)
    fun clear()
}

@FlowPreview
@UseExperimental(ExperimentalCoroutinesApi::class)
class HeritagesInMemoryRepository : HeritagesLocalRepository {

    private val dispatcher = Executors.newSingleThreadExecutor().asCoroutineDispatcher()

    private val heritages = BroadcastChannel<List<Heritage>>(Channel.CONFLATED)
        .apply { offer(emptyList()) }

    override fun getAllFlow(): Flow<List<Heritage>> = heritages.asFlow()

    override suspend fun insertOrUpdateHeritages(items: List<Heritage>) {
        updateHeritages { current -> (items + current).distinctBy { it.id } }
    }

    override fun clear() {
        heritages.offer(emptyList())
    }

    private suspend fun updateHeritages(block: (List<Heritage>) -> List<Heritage>) = withContext(dispatcher) {
        val currentList = heritages.asFlow().first()
        val updatedList = block(currentList)
        heritages.offer(updatedList)
    }
}