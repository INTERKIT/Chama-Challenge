package com.chama.challenge.common.di

import org.koin.core.module.Module

interface InjectionModule {
    fun create(): Module
}