package com.puntoclick.features.utils

import com.puntoclick.data.model.GlobalLocale
import io.ktor.server.application.*
import io.ktor.server.application.ApplicationCallPipeline.ApplicationPhase.Plugins
import java.util.*


fun Application.configureGlobalLocale() {
    intercept(Plugins) {
        val locale = call.retrieveLocale()
        GlobalLocale.updateLocale(locale)
    }
}

fun ApplicationCall.retrieveLocale(): Locale {
    val tag = request.headers[LAN] ?: LAN_TAG_MX
    return Locale.forLanguageTag(tag)
}

fun Locale.getString(key: StringResourcesKey): String =
    ResourceBundle.getBundle("app_strings", this).getString(key.value)