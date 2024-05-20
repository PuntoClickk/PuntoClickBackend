package com.puntoclick.features.utils

import io.ktor.server.application.*
import java.util.*



fun ApplicationCall.retrieveLocale(): Locale {
    val tag = request.headers[LAN] ?: LAN_TAG_MX
    return Locale.forLanguageTag(tag)
}

fun Locale.loadLocalizedResources(): ResourceBundle {
    return ResourceBundle.getBundle("app_strings", this)
}
