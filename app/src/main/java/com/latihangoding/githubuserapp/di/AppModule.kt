package com.latihangoding.githubuserapp.di

import android.app.Application
import androidx.room.Room
import com.latihangoding.githubuserapp.databases.FavoriteDao
import com.latihangoding.githubuserapp.databases.FavoriteDatabase
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module(includes = [ViewModelModule::class])
class AppModule {
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
}