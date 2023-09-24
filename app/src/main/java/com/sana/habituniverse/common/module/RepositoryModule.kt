package com.sana.habituniverse.common.module

import com.sana.habituniverse.data.repository.HabitRepository
import com.sana.habituniverse.data.repository.HabitRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    @Singleton
    abstract fun provideHabitRepository(habitRepositoryImpl: HabitRepositoryImpl): HabitRepository

}