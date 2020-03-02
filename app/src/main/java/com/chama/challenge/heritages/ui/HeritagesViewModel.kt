package com.chama.challenge.heritages.ui

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.chama.challenge.heritages.domain.HeritagesInteractor
import com.chama.challenge.heritages.model.Heritage
import com.chama.challenge.heritages.model.PagingState
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.cancel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import timber.log.Timber
import kotlin.coroutines.CoroutineContext
import kotlin.properties.Delegates

private const val DELAY_IN_MILLIS = 1000L

class HeritagesViewModel(
    private val heritagesInteractor: HeritagesInteractor
) : ViewModel(), CoroutineScope {

    override val coroutineContext: CoroutineContext = SupervisorJob() + Dispatchers.Main.immediate

    val heritagesLiveData = MutableLiveData<List<Heritage>>()
    val stateLiveData = MutableLiveData<PagingState>()

    private var pagingState: PagingState = PagingState.Idle

    private var heritageList: List<Heritage> by Delegates.observable(emptyList()) { _, _, newValue ->
        heritagesLiveData.postValue(newValue)
        stateLiveData.postValue(pagingState)
    }

    init {
        launch {
            heritagesInteractor.getAllOperations().collect { heritages ->
                heritageList = heritages
            }
        }
    }

    fun loadHeritages(page: Int = 1) {
        launch {
            try {
                delay(DELAY_IN_MILLIS)
                setState(PagingState.Loading)
                heritagesInteractor.loadHeritages(page)
                setState(PagingState.Idle)
            } catch (e: CancellationException) {
                Timber.d("Heritage loading cancelled")
            } catch (e: Throwable) {
                Timber.e(e, "Error loading heritages")
                setState(PagingState.Error(e))
                stateLiveData.postValue(PagingState.Error(e))
            }
        }
    }

    fun reloadHeritages() {
        launch {
            try {
                setState(PagingState.Loading)
                heritagesInteractor.reloadHeritages()
                setState(PagingState.Idle)
            } catch (e: Throwable) {
                Timber.e(e, "Error reloading heritages")
                setState(PagingState.Error(e))
                stateLiveData.postValue(PagingState.Error(e))
            }
        }
    }

    private fun setState(state: PagingState) {
        pagingState = state
    }

    override fun onCleared() {
        super.onCleared()
        cancel()
    }
}