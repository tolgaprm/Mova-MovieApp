package com.prmto.mova_movieapp.feature_settings.domain.use_case

import com.prmto.core_domain.use_case.languageIsoCode.GetLanguageIsoCodeUseCase
import com.prmto.core_domain.use_case.languageIsoCode.UpdateLanguageIsoCodeUseCase
import com.prmto.core_domain.use_case.uIMode.GetUIModeUseCase
import com.prmto.core_domain.use_case.uIMode.UpdateUIModeUseCase

data class SettingUseCase(
    val getUIModeUseCase: GetUIModeUseCase,
    val updateUIModeUseCase: UpdateUIModeUseCase,
    val updateLanguageIsoCodeUseCase: UpdateLanguageIsoCodeUseCase,
    val getLanguageIsoCodeUseCase: GetLanguageIsoCodeUseCase
)
