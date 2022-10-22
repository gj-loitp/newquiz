package com.infinitepower.newquiz.data.repository.wordle

import android.content.Context
import android.util.Log
import com.google.firebase.ktx.Firebase
import com.google.firebase.remoteconfig.ktx.remoteConfig
import com.infinitepower.newquiz.core.common.FlowResource
import com.infinitepower.newquiz.core.common.Resource
import com.infinitepower.newquiz.core.common.dataStore.SettingsCommon
import com.infinitepower.newquiz.core.common.dataStore.infiniteWordleSupportedLang
import com.infinitepower.newquiz.core.dataStore.manager.DataStoreManager
import com.infinitepower.newquiz.core.di.SettingsDataStoreManager
import com.infinitepower.newquiz.domain.repository.wordle.WordleRepository
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.withContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class WordleRepositoryImpl @Inject constructor(
    @ApplicationContext private val context: Context,
    @SettingsDataStoreManager private val settingsDataStoreManager: DataStoreManager
) : WordleRepository {
    override suspend fun getAllWords(): Set<String> = withContext(Dispatchers.IO) {
        val quizLanguage = settingsDataStoreManager.getPreference(SettingsCommon.InfiniteWordleQuizLanguage)

        val listRawId = infiniteWordleSupportedLang.find { lang ->
            lang.key == quizLanguage
        }?.rawListId ?: throw NullPointerException("Wordle language not found")

        val wordleListInputStream = context.resources.openRawResource(listRawId)

        try {
            wordleListInputStream
                .readBytes()
                .decodeToString()
                .split("\r\n", "\n")
                .filter { it.length == 5 }
                .map { it.uppercase().trim() }
                .shuffled()
                .toSet()
        } catch (e: Exception) {
            throw e
        } finally {
            wordleListInputStream.close()
        }
    }

    override  fun generateRandomWord(): FlowResource<String> = flow {
        try {
            emit(Resource.Loading())

            val allWords = getAllWords()
            val randomWord = allWords.random()

            emit(Resource.Success(randomWord))
        } catch (e: Exception) {
            e.printStackTrace()
            emit(Resource.Error(e.localizedMessage ?: "A error occurred while getting word."))
        }
    }

    override fun isColorBlindEnabled(): FlowResource<Boolean> = flow {
        try {
            emit(Resource.Loading())

            val isColorBlindEnabled = settingsDataStoreManager.getPreference(SettingsCommon.WordleColorBlindMode)
            emit(Resource.Success(isColorBlindEnabled))
        } catch (e: Exception) {
            e.printStackTrace()
            emit(Resource.Error(e.localizedMessage ?: "A error occurred while checking if color blind is enabled."))
        }
    }

    override fun isLetterHintEnabled(): FlowResource<Boolean> = flow {
        try {
            emit(Resource.Loading())

            val isLetterHintEnabled = settingsDataStoreManager.getPreference(SettingsCommon.WordleLetterHints)
            emit(Resource.Success(isLetterHintEnabled))
        } catch (e: Exception) {
            e.printStackTrace()
            emit(Resource.Error(e.localizedMessage ?: "A error occurred while checking if letter hint is enabled."))
        }
    }

    override fun isHardModeEnabled(): FlowResource<Boolean> = flow {
        try {
            emit(Resource.Loading())

            val isHardModeEnabled = settingsDataStoreManager.getPreference(SettingsCommon.WordleHardMode)
            emit(Resource.Success(isHardModeEnabled))
        } catch (e: Exception) {
            e.printStackTrace()
            emit(Resource.Error(e.localizedMessage ?: "A error occurred while checking if hard mode is enabled."))
        }
    }

    override suspend fun getWordleMaxRows(defaultMaxRow: Int?): Int {
        if (defaultMaxRow == null) {
            // If is row limited return row limit value else return int max value
            val isRowLimited = settingsDataStoreManager.getPreference(SettingsCommon.WordleInfiniteRowsLimited)
            if (isRowLimited) return settingsDataStoreManager.getPreference(SettingsCommon.WordleInfiniteRowsLimit)

            return Int.MAX_VALUE
        }

        return defaultMaxRow
    }

    override fun getAdRewardRowsToAdd(): Int {
        val remoteConfig = Firebase.remoteConfig

        return remoteConfig.getLong("wordle_ad_row_reward_amount").toInt()
    }
}