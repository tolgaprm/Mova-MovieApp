package com.prmto.core_ui.countryCode

import android.content.Context
import com.prmto.core_domain.countryCode.CountryCodeProvider
import com.prmto.core_ui.util.getCountryIsoCode
import javax.inject.Inject


class CountryCodeProviderImpl @Inject constructor(
    private val context: Context
) : CountryCodeProvider {

    override fun getCountryIsoCode(): String {
        return context.getCountryIsoCode()
    }
}