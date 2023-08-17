package com.infinitepower.newquiz.translation.di

import com.infinitepower.newquiz.translation.GoogleTranslatorUtil
import com.infinitepower.newquiz.translation.TranslatorUtil
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class GoogleTranslatorModule {
    @Binds
    abstract fun bindTranslatorUtil(impl: GoogleTranslatorUtil): TranslatorUtil
}