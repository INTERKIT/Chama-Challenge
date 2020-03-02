package com.chama.challenge.heritages.di

import com.chama.challenge.common.di.InjectionModule
import com.chama.challenge.heritages.domain.HeritagesInteractor
import com.chama.challenge.heritages.repository.HeritagesInMemoryRepository
import com.chama.challenge.heritages.repository.HeritagesJsonRepository
import com.chama.challenge.heritages.repository.HeritagesLocalRepository
import com.chama.challenge.heritages.repository.HeritagesRepository
import com.chama.challenge.heritages.ui.HeritagesViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.bind
import org.koin.dsl.module

object HeritagesModule : InjectionModule {

    override fun create() = module {
        single { HeritagesInteractor(get(), get()) }
        single { HeritagesInMemoryRepository() } bind HeritagesLocalRepository::class
        single { HeritagesJsonRepository(get()) } bind HeritagesRepository::class

        viewModel { HeritagesViewModel(get()) }
    }
}