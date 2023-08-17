package com.infinitepower.newquiz.translation.di

import com.infinitepower.newquiz.translation.NoTranslatorUtil
import com.infinitepower.newquiz.translation.TranslatorUtil
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class NoTranslatorModule {
    @Binds
    abstract fun bindTranslatorUtil(impl: NoTranslatorUtil): TranslatorUtil
}