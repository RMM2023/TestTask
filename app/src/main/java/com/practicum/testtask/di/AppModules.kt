package com.practicum.testtask.di

import com.practicum.testtask.data.local.AppDataBase
import com.practicum.testtask.data.repository.ItemRepositoryImpl
import com.practicum.testtask.domain.repository.ItemRepository
import com.practicum.testtask.presentation.viewmodel.MainViewModel
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val appModule = module {
    single {AppDataBase.getInstance(androidContext())}
    single {get<AppDataBase>().itemDao()}
    single<ItemRepository> {ItemRepositoryImpl(get())}
    viewModel { MainViewModel(get()) }
}