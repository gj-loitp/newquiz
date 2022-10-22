package com.infinitepower.newquiz.settings_presentation

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.infinitepower.newquiz.domain.repository.wordle.daily.DailyWordleRepository
import com.infinitepower.newquiz.settings_presentation.data.SettingsScreenPageData
import com.infinitepower.newquiz.settings_presentation.model.ScreenKey
import com.infinitepower.newquiz.translation_dynamic_feature.TranslatorUtil
import com.infinitepower.newquiz.translation_dynamic_feature.TranslatorUtil.TranslatorModelState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SettingsViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    val dailyWordleRepository: DailyWordleRepository,
    private val translatorUtil: TranslatorUtil
) : ViewModel() {
    private val _uiState = MutableStateFlow(SettingsUiState())
    val uiState = _uiState.asStateFlow()

    init {
        savedStateHandle
            .getStateFlow(
                key = SettingsScreenNavArgs::screenKey.name,
                initialValue = SettingsScreenPageData.MainPage.key.value
            ).onEach { key ->
                _uiState.update { currentState ->
                    currentState.copy(screenKey = ScreenKey(key))
                }
            }.launchIn(viewModelScope)

        viewModelScope.launch(Dispatchers.IO) {
            _uiState.update { currentState ->
                val state = if (translatorUtil.isModelDownloaded()) {
                    TranslatorModelState.Downloaded
                } else TranslatorModelState.None

                currentState.copy(translationModelState = state)
            }

        }
    }

    fun onEvent(event: SettingsScreenUiEvent) {
        when (event) {
            is SettingsScreenUiEvent.DeleteTranslationModel -> viewModelScope.launch(Dispatchers.IO) {
                translatorUtil.deleteModel()
            }
            is SettingsScreenUiEvent.DownloadTranslationModel -> downloadTranslationModel()
        }
    }

    private fun downloadTranslationModel() = viewModelScope.launch(Dispatchers.IO)  {
        translatorUtil
            .downloadModel()
            .collect { res ->
                _uiState.update { currentState ->
                    currentState.copy(translationModelState = res.data ?: TranslatorModelState.None)
                }
            }
    }
}