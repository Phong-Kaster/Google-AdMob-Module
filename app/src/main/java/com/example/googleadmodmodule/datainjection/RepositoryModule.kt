package com.example.googleadmodmodule.datainjection

import com.example.googleadmodmodule.database.dao.NationalityDao
import com.example.googleadmodmodule.database.dao.UserDao
import com.example.googleadmodmodule.database.repository.NationalityRepository
import com.example.googleadmodmodule.database.repository.UserRepository
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

    @Provides
    @Singleton
    fun provideUserRepository(userDao: UserDao): UserRepository {
        return UserRepository(userDao)
    }

    @Provides
    @Singleton
    fun provideNationalityRepository(nationalityDao: NationalityDao): NationalityRepository {
        return NationalityRepository(nationalityDao)
    }
}