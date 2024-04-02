package com.puntoclick.features.utils

import com.puntoclick.data.model.AppResult
import com.puntoclick.data.model.createError


inline fun <T: Any> tryCatch( block: () -> AppResult<T>): AppResult<T> {
    return try {
        block.invoke()
    } catch (e: Exception) {
        createError(e.message, e.message)
    }
}
