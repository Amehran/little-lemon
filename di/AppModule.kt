package com.arminmehran.little_lemmon_app_capstone.di

import android.content.Context
import androidx.room.Room
import com.arminmehran.little_lemmon_app_capstone.business.data.AppDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import io.ktor.client.HttpClient
import io.ktor.client.engine.android.Android
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.http.ContentType
import io.ktor.serialization.kotlinx.json.json


@Module
@InstallIn(SingletonComponent::class)
  class AppModule {

    @Provides
    fun provideHttpClients()  = HttpClient(Android){
        install(ContentNegotiation){
            json(contentType = ContentType("text", "plain"))
        }
    }

    @Provides
    fun provideDatabase(@ApplicationContext appContext: Context) = Room.databaseBuilder(
        appContext,
        AppDatabase::class.java,
        "database"
    ).build()

    @Provides
    fun provideDao( database: AppDatabase) = database.menuItemDao()
}