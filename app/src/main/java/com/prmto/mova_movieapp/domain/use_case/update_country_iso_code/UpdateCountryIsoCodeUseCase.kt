package com.prmto.mova_movieapp.domain.use_case.update_country_iso_code

import com.prmto.mova_movieapp.domain.repository.DataStoreOperations
import javax.inject.Inject

class UpdateCountryIsoCodeUseCase @Inject constructor(
    private val dataStoreOperations: DataStoreOperations
) {
    suspend operator fun invoke(countryIsoCode: String) {
        dataStoreOperations.updateUserCountryIsoCode(countryIsoCode)
    }
}