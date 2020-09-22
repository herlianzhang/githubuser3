package com.latihangoding.githubuserapp.di

import javax.inject.Qualifier

@Retention(AnnotationRetention.BINARY)
@Qualifier
annotation class ServiceApi

@Retention(AnnotationRetention.BINARY)
@Qualifier
annotation class CoroutineScropeIO