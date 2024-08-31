package com.practicum.testtask.di

import com.practicum.testtask.data.local.AppDataBase
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

val dataModule = module {
    single {AppDataBase.getInstance(androidContext())}
    single {get<AppDataBase>().itemDao()}
}