package com.prmto.mova_movieapp.domain.use_case

import com.prmto.mova_movieapp.domain.use_case.get_language_iso_code.GetLanguageIsoCodeUseCase
import com.prmto.mova_movieapp.domain.use_case.get_ui_mode.GetUIModeUseCase
import com.prmto.mova_movieapp.domain.use_case.update_current_language_iso_code.UpdateLanguageIsoCodeUseCase
import com.prmto.mova_movieapp.domain.use_case.update_ui_mode.UpdateUIModeUseCase

data class SettingUseCase(
    val getUIModeUseCase: GetUIModeUseCase,
    val updateUIModeUseCase: UpdateUIModeUseCase,
    val updateLanguageIsoCodeUseCase: UpdateLanguageIsoCodeUseCase,
    val getLanguageIsoCodeUseCase: GetLanguageIsoCodeUseCase
)