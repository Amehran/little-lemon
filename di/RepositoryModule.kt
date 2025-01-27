package com.arminmehran.little_lemmon_app_capstone.di

import com.arminmehran.little_lemmon_app_capstone.business.repository.MainRepository
import com.arminmehran.little_lemmon_app_capstone.business.repository.MainRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
abstract class RepositoryModule {
    @Binds
    abstract fun bindMainRepository(
        mainRepositoryImpl: MainRepositoryImpl
    ): MainRepository
}