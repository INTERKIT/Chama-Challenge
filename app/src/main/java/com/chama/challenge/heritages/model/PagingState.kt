package com.chama.challenge.heritages.model

sealed class PagingState {
    object Idle : PagingState()
    object Loading : PagingState()
    class Error(val e: Throwable) : PagingState()
}