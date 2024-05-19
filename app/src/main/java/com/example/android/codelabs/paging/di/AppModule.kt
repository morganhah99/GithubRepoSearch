/*
 * Copyright (C) 2018 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.example.android.codelabs.paging.di

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import androidx.savedstate.SavedStateRegistryOwner
import com.example.android.codelabs.paging.api.GithubService
import com.example.android.codelabs.paging.data.GithubRepository
import com.example.android.codelabs.paging.db.RepoDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import okhttp3.logging.HttpLoggingInterceptor.Level
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


/**
 * Class that handles object creation.
 * Like this, objects can be passed as parameters in the constructors and then replaced for
 * testing, where needed.
 */

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    fun provideGithubService(): GithubService {
        val logger = HttpLoggingInterceptor()
        logger.level = Level.BASIC

        val client = OkHttpClient.Builder()
            .addInterceptor(logger)
            .build()

        return Retrofit.Builder()
            .baseUrl("https://api.github.com/")
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(GithubService::class.java)
    }


    @Provides
    fun provideRepoDatabase(@ApplicationContext context: Context): RepoDatabase {
        return RepoDatabase.getInstance(context)
    }

    @Provides
    fun provideGithubRepository(
        service: GithubService,
        database: RepoDatabase
    ): GithubRepository {
        return GithubRepository(service, database)
    }


}


@Module
@InstallIn(ActivityComponent::class)
class SavedStateModule {
    @Provides
    fun provideSavedStateRegistryOwner(activity: AppCompatActivity): SavedStateRegistryOwner {
        return activity
    }
}




