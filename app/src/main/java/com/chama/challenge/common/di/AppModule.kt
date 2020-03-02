package com.chama.challenge.common.di

import com.chama.challenge.heritages.repository.DataProvider
import com.google.gson.Gson
import org.koin.dsl.module

object AppModule : InjectionModule {

    override fun create() = module {
        single { DataProvider(get(), get()) }
        single { Gson() }
    }
}