package com.sana.habituniverse.common.module

import com.sana.habituniverse.data.repository.HabitRepository
import com.sana.habituniverse.data.repository.HabitRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import io.realm.Realm
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object RealmModule {

    @Singleton
    @Provides
    fun provideRealm(): Realm {
        return Realm.getDefaultInstance()
    }

}
