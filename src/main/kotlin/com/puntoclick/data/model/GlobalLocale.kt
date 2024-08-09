package com.puntoclick.data.model

import com.puntoclick.features.utils.LAN_TAG_MX
import java.util.*

object GlobalLocale {
    var locale: Locale = Locale.forLanguageTag(LAN_TAG_MX)
        private set

    fun updateLocale(newLocale: Locale) {
        locale = newLocale
    }
}