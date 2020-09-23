package com.latihangoding.githubuserapp.di

import android.app.Application
import androidx.room.Room
import com.latihangoding.githubuserapp.api.ApiService
import com.latihangoding.githubuserapp.api.AuthInterceptor
import com.latihangoding.githubuserapp.databases.FavoriteDao
import com.latihangoding.githubuserapp.databases.FavoriteDatabase
import com.latihangoding.githubuserapp.helpers.Values
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module(includes = [CoreDataModule::class, ViewModelModule::class])
class AppModule {

    @Singleton
    @Provides
    fun provideApiService(
        @ServiceApi okHttpClient: OkHttpClient,
        converterFactory: GsonConverterFactory
    ) = provideService(okHttpClient, converterFactory, ApiService::class.java)

    @ServiceApi
    @Provides
    fun providePrivateOkHttpClient(upstreamClient: OkHttpClient): OkHttpClient {
        return upstreamClient.newBuilder().addInterceptor(AuthInterceptor(Values.TOKEN)).build()
    }

    @Singleton
    @Provides
    fun provideDb(app: Application): FavoriteDatabase {
        return Room.databaseBuilder(app, FavoriteDatabase::class.java, "github.db")
            .fallbackToDestructiveMigration().build()
    }

    @Singleton
    @Provides
    fun provideFavoriteDao(db: FavoriteDatabase): FavoriteDao {
        return db.favoriteDao()
    }

    private fun createRetrofit(
        okHttpClient: OkHttpClient,
        converterFactory: GsonConverterFactory
    ): Retrofit {
        return Retrofit.Builder()
            .baseUrl(Values.MAIN_URL)
            .client(okHttpClient)
            .addConverterFactory(converterFactory)
            .build()
    }

    private fun <T> provideService(
        okHttpClient: OkHttpClient,
        converterFactory: GsonConverterFactory,
        clazz: Class<T>
    ): T {
        return createRetrofit(okHttpClient, converterFactory).create(clazz)
    }
}